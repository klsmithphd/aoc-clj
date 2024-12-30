(ns aoc-clj.2024.day17
  "Solution to https://adventofcode.com/2024/day/17"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

;; Input parsing
(defn get-nums
  [s]
  (map read-string (re-seq #"\d+" s)))

(defn parse
  [input]
  (let [[reg-str prog-str] (u/split-at-blankline input)]
    {:regs (zipmap [:a :b :c] (mapcat get-nums reg-str))
     :prog (vec (get-nums (first prog-str)))}))

;; Puzzle logic
(def combo-map
  {4 :a
   5 :b
   6 :c})

(defn combo-operand
  "Returns the combo operand value.
   Combo operands 0-3 represent the literal values 0 through 3
   Combo operands 4, 5, and 6 map to register A, B, and C, respectively."
  [regs operand]
  (if (<= operand 3)
    operand
    (regs (combo-map operand))))

(defn inc-ip
  "Increases the instruction pointer by 2"
  [state]
  (update state :ip + 2))

(defn division
  "Computes a truncated division operation, with register A's value in the
   numerator and saving the result in the register given by `store`.
   
   The divisor is 2 to the power of the combo operand"
  [store {:keys [regs] :as state} operand]
  (let [two-pow (bit-shift-left 1 (combo-operand regs operand))]
    (-> state
        (assoc-in [:regs store] (quot (regs :a) two-pow))
        inc-ip)))

(def adv (partial division :a))
(def bdv (partial division :b))
(def cdv (partial division :c))

(defn bxl
  "Bitwise XOR of register B and literal operand, stored in B"
  [{:keys [regs] :as state} operand]
  (-> state
      (assoc-in [:regs :b] (bit-xor (regs :b) operand))
      inc-ip))

(defn bst
  "Combo operand mod 8, stored in B"
  [{:keys [regs] :as state} operand]
  (-> state
      (assoc-in [:regs :b] (mod (combo-operand regs operand) 8))
      inc-ip))

(defn jnz
  "Do nothing if register A is zero, else set instruction pointer to
   value of literal operand"
  [{:keys [regs] :as state} operand]
  (if (zero? (regs :a))
    (inc-ip state)
    (assoc state :ip operand)))

(defn bxc
  "Bitwise XOR of register B and register C, stored in B"
  [{:keys [regs] :as state} _]
  (-> state
      (assoc-in [:regs :b] (bit-xor (regs :b) (regs :c)))
      inc-ip))

(defn out
  "Combo operand mod 8, appended to output"
  [{:keys [regs] :as state} operand]
  (-> state
      (update :out conj (mod (combo-operand regs operand) 8))
      inc-ip))

(def opcode-map
  {0 adv
   1 bxl
   2 bst
   3 jnz
   4 bxc
   5 out
   6 bdv
   7 cdv})

(defn step
  "Increments the program state by one step, based on the current
   instruction pointer `ip`"
  [{:keys [ip prog] :as state}]
  (let [[opcode operand] (take 2 (drop ip prog))]
    ((opcode-map opcode) state operand)))

(defn running?
  "Returns true while the instruction pointer is in bounds of the program"
  [{:keys [ip prog]}]
  (< -1 ip (count prog)))

(defn init-state
  "Returns the initial state of the program"
  [input]
  (into input {:ip 0 :out []}))

(defn execute
  "Executes the instructions in the program until the instruction pointer
   is out of bounds of the instructions and returns the final state"
  [input]
  (->> (init-state input)
       (iterate step)
       (drop-while running?)
       first))

(defn execute-with-a-reg
  "Returns the output of executing the program when the A register
   is overridden by `a-reg`"
  [input a-reg]
  (:out (execute (assoc-in input [:regs :a] a-reg))))

(defn viable-candidates
  "Returns only those candidate a-register values that are capable of
   matching `n` values in the program output with the program itself"
  [prog input n candidates]
  (filter #(= (take n prog)
              (take n (execute-with-a-reg input %))) candidates))

(defn a-value-that-copies
  "Returns the value that, when set to the A register, causes the program
   to return a copy of itself."
  [{:keys [prog] :as input}]
  ;; The opcodes work on data in 3-bit chunks, but may bit-shift in
  ;; data from outside of those 3 bits. In my puzzle input, the maximum
  ;; possible bit-shift was 7. Because of that, we start by looking at
  ;; 10-bit candidates that are capable of outputting the first number
  ;; that matches the program itself. 10-bits = numbers in the range 0
  ;; to 1023, hence the `init` value below.
  ;; `init` consists of a collection of numbers that all end up 
  ;; correctly outputting the first value of the program.
  (let [size (count prog)
        init (->> (range 1024)
                  (viable-candidates prog input 1))]
    ;; Now, we move "left" 3 bits at a time, considering tacking on 
    ;; to the front of each of our candidates 3 new bits, i.e.,
    ;; numbers in the range 0..7, shifted accordingly. For each new
    ;; 3-bit chunk we tack on to the front, we require that the output
    ;; of the program match the program itself to one more place.
    (loop [n 2 shift 10 candidates init]
      (if (= n size)
        (first candidates)
        (recur (inc n)
               (+ shift 3)
               (->> (range 8)
                    (map #(bit-shift-left % shift))
                    (mapcat #(map (fn [x] (+ x %)) candidates))
                    (viable-candidates prog input n)))))))

;; Puzzle solutions
(defn part1
  "Once it halts, what do you get if you use commas to join the values it
   output into a single string?"
  [input]
  (str/join "," (:out (execute input))))

(defn part2
  "What is the lowest positive initial value for register A that causes
   the program to output a copy of itself?"
  [input]
  (a-value-that-copies input))

;; Working notes

;; When I look at my personal puzzle input, the program is:
;; 2,4 1,7 7,5 1,7 4,6 0,3 5,5 3,0
;; That corresponds to:
;; bst 4   b = a mod 8 
;; bxl 7   b = b ^ 7
;; cdv 5   c = a // b  
;; bxl 7   b = b ^ 7
;; bxc     b = b ^ c
;; adv 3   a = a // 2^3
;; out 5   output b
;; jnz 0   jump to 0 if a != 0
;;
;; The net impact of this program is to look at 3-bit chunks of a, and
;; do the following:
;; Take the lowest three bits of a and assign it to b. b = 0..7
;; Set c = a right-shifted by (7-b) bits
;; Set b = b ^ c
;; Output b
;; Right-shift a by 3 bits.
;;
;; Deterministically finding the lowest integer a that results in outputing
;; the program code can be thought of as finding the sequence of 3-bit ints
;; that are compatible with emitting the program ints.
;;
;; For instance, to end up with 2 as the first digit,
;; [o n m | l k j | i h g | f e d]
;; [o n m | l k j | i h g | 0 1 0]
;; Result needs to equal 2
;; The options are:
;; b = 0, 1, 2, 3, 4, 5, 6, 7
;; c = 2, 3, 0, 1, 6, 7, 4, 5
;; c has to be 7-b bits shifted down
;; So, shifting 7, 6, 5, 4, 3, 2, 1, or 0 bits shifted.
;; 0 bits shifted is just [f e d], i.e. [0 1 0] = 2, not 5, so that doesn't work
;; 1 bits shifted is [g e d] = [g 0 1], which can't equal 4. Out.
;; 2 bits shifted is [h g e] = [h g 0], which can't equal 7. Out.
;; 3 bits shifted is [i h g], which works if it equals [1 1 0].
;; 4 bits shifted is [j i h], which works if [j i h] equals [1 1 0], and g can be 0 or 1.
;; 5 bits shifted is [k j i], [k j i] = [1 1 0], and g, h = 0 or 1
;; 6 bits shifted is [l k j], [l k j] = [1 1 0], and g, h, i = 0 or 1
;; 7 bits shifted is [m l k], [m l k] = [1 1 0], and g, h, i, j = 0 or 1.
;; So, the possible options are:
;;
;; [* * * | * * * | 1 1 0 | 1 0 0] = [* | * | 6 | 4]
;; [* * * | * * 0 | 0 1 * | 0 1 1] = [* | 0,2,4,6 | 2,3 | 3]
;; [* * * | * 0 0 | 0 * * | 0 1 0] = [* | 0,4 | 0,1,2,3 | 2]
;; [* * * | 0 1 1 | * * * | 0 0 1] = [* | 3 | * | 1]
;; [* * 0 | 1 0 * | * * * | 0 0 0] = [0,2,4,6 | 4,5 | * | 0]
;;
;; The program is 16 ints long, so we need at minimum 3*19 bits (3*3 for padding)

;; More generally, we need to look 10 bits at a time, which are 1024 values.
;; Those 10 bits determine which possible numbers will output our desired
;; sequence. 
;; We'll come up with a set of options that all work for the lowest output number.

;; Then we shift up 3 bits and consider all the variations that
;; will now work for outputting this number.
