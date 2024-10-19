(ns aoc-clj.2017.day18
  "Solution to https://adventofcode.com/2017/day/18"
  (:require [clojure.string :as str]))

;; Input parsing
(defn parse-possible-num
  [s]
  (if (number? (read-string s))
    (read-string s)
    s))

(defn parse-line
  [line]
  (let [[cmd & args] (str/split line #" ")]
    [cmd (mapv parse-possible-num args)]))

(defn parse
  [input]
  (mapv parse-line input))

;; Puzzle logic
(defn arg-val
  "Parse the argument. If it's a literal (number), return the number,
   else return the value stored in the register or zero if the register
   is unset"
  [state x]
  (if (number? x)
    x
    (get state x 0)))

(defn add-cmd
  "Returns the new state for an `add` instruction, which updates
   the first argument's register value by adding the second argument's value."
  [state [x y]]
  (-> state
      (assoc x (+ (arg-val state x) (arg-val state y)))
      (update :pos inc)))

(defn jgz-cmd
  "Returns the new state for a `jgz` instruction, which jumps
   in instruction position by the offset of the second argument only
   if the value in the first argument's register is greater than zero."
  [state [x y]]
  (if (> (arg-val state x) 0)
    (update state :pos + (arg-val state y))
    (update state :pos inc)))

(defn mod-cmd
  "Returns the new state for a `mod` instruction, which updates
   the first argument's register value by modding it with the second
   argument's value"
  [state [x y]]
  (-> state
      (assoc x (mod (arg-val state x) (arg-val state y)))
      (update :pos inc)))

(defn mul-cmd
  "Returns the new state for a `mul` instruction, which updates
   the first argument's register value by multiplying it by the second
   argument's value"
  [state [x y]]
  (-> state
      (assoc x (* (arg-val state x) (arg-val state y)))
      (update :pos inc)))

(defn set-cmd
  "Returns the new state for a `set` instruction, which sets the
   first argument's register value to the second argument's value"
  [state [x y]]
  (-> state
      (assoc x (arg-val state y))
      (update :pos inc)))

(defn snd-cmd-p1
  "Returns the new state for a `snd` instruction using the interpretation
   in part 1. It sets the `:snd` key to the value of the argument's register,
   indicating that frequency has been played as a sound"
  [state [x]]
  (-> state
      (assoc :snd (arg-val state x))
      (update :pos inc)))

(defn rcv-cmd-p1
  "Returns the new state for a `rcv` instruction using the interpretation
   in part 1. If the value of the argument's register is zero, it does nothing,
   but if non-zero, it sets the instruction position out of bounds to
   stop the program from running."
  [state [x]]
  (if (zero? (arg-val state x))
    (update state :pos inc)
    (assoc state :pos -1)))

(defn snd-cmd-p2
  "Returns the new state for a `snd` instruction using the interpretation
   in part 2. It adds the value of the argument's register to the queue
   of the other program."
  [p-id state [x]]
  (let [other-p-id (bit-xor 1 p-id)
        snd-val    (arg-val (get-in state [:progs p-id]) x)]
    (-> state
        (assoc-in [:progs other-p-id :waiting] false)
        (update-in [:progs other-p-id :queue] conj snd-val)
        (update-in [:progs p-id :snd-cnt] inc)
        (update-in [:progs p-id :pos] inc))))

(defn rcv-cmd-p2
  "Returns the state change for a `rcv` instruction using the interpretation
   in part 2. It sets the value of the argument's register to the current
   value in the program's queue."
  [{:keys [queue] :as state} [x]]
  (if (empty? queue)
    (assoc state :waiting true)
    (-> state
        (update :queue #(subvec % 1))
        (assoc x (first queue))
        (update :pos inc))))

(def shared-cmds-map
  {"add" add-cmd
   "jgz" jgz-cmd
   "mod" mod-cmd
   "mul" mul-cmd
   "set" set-cmd})

(defn init-state
  "Returns an initial state based on which part of the puzzle we're solving
   and the set of assembly instructions"
  [part insts]
  (case part
    :p1 {:insts insts :progs {0 {:pos 0}}}
    :p2 {:insts insts
         :progs {0 {:pos 0 :waiting false :queue [] :snd-cnt 0 "p" 0}
                 1 {:pos 0 :waiting false :queue [] :snd-cnt 0 "p" 1}}}))

(defn step
  "Evolves the state forward one step and returns the new state.
   `part` is either `:p1` or `:p2`"
  [part {:keys [insts progs] :as state}]
  (let [cmd-map    (merge shared-cmds-map
                          (case part
                            :p1 {"rcv" rcv-cmd-p1 "snd" snd-cmd-p1}
                            :p2 {"rcv" rcv-cmd-p2 "snd" snd-cmd-p2}))
        prog       (if (get-in progs [0 :waiting]) 1 0)
        prog-state (progs prog)
        [cmd args] (insts (:pos prog-state))
        cmd-fn     (cmd-map cmd)]
    (if (and (= :p2 part) (= "snd" cmd))
      (cmd-fn prog state args)
      (update-in state [:progs prog] merge (cmd-fn prog-state args)))))

(def step-p1 (partial step :p1))
(def step-p2 (partial step :p2))

(defn in-bounds?
  "Returns true if the current instruction position is within the bounds
   of the program"
  [inst-count {:keys [pos]}]
  (< -1 pos inst-count))

(defn running?
  "Returns true if any programs are within bounds and at least one
   is not waiting"
  [{:keys [insts progs]}]
  (and
   (every? #(in-bounds? (count insts) %) (vals progs))
   (not-every? :waiting (vals progs))))

(defn execute
  "Run the program until it's complete and return the final execution state"
  [part insts]
  (let [stepper (partial step part)]
    (->> (init-state part insts)
         (iterate stepper)
         (drop-while running?)
         first)))

(def execute-p1 (partial execute :p1))
(def execute-p2 (partial execute :p2))

(defn recovered-frequency
  "What's the frequency returned by the first `rcv` instruction"
  [insts]
  (-> (execute-p1 insts)
      (get-in [:progs 0 :snd])))

(defn prog1-snd-cnt
  "What's the number of times program 1 issued a `snd` instruction?"
  [insts]
  (-> (execute-p2 insts)
      (get-in [:progs 1 :snd-cnt])))

;; Puzzle solutions
(defn part1
  "What is the value of the recovered frequency the first time a `rcv` 
   instruction is executed with a non-zero value?"
  [input]
  (recovered-frequency input))

(defn part2
  "how many times did program 1 send a value?"
  [input]
  (prog1-snd-cnt input))