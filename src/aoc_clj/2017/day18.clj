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
    (update state :pos + y)
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

(def common-cmds
  {"add" add-cmd
   "jgz" jgz-cmd
   "mod" mod-cmd
   "mul" mul-cmd
   "set" set-cmd})

(def cmd-map-p1
  (merge common-cmds
         {"rcv" rcv-cmd-p1
          "snd" snd-cmd-p1}))

(def cmd-map-p2
  (merge common-cmds
         {"rcv" rcv-cmd-p2
          "snd" snd-cmd-p2}))

(defn in-bounds?
  [{:keys [insts pos]}]
  (< -1 pos (count insts)))

(def running-p1? in-bounds?)

(defn step-p1
  "Evolve the state by one step based on the instruction indicated by `pos`"
  [{:keys [insts pos] :as state}]
  (let [[cmd args] (insts pos)
        cmd-fn (cmd-map-p1 cmd)]
    (cmd-fn state args)))

(defn step-p2
  [{:keys [insts progs] :as state}]
  ;; Because the speed of the programs doesn't matter, we always attempt
  ;; to execute program 0 if we can (if it's not waiting for a value to 
  ;; receive).
  (let [prog (if (get-in progs [0 :waiting]) 1 0)
        [cmd args] (insts (get-in state [:progs prog :pos]))
        cmd-fn (cmd-map-p2 cmd)]
    (if (= "snd" cmd)
      (cmd-fn prog state args)
      (update-in state [:progs prog] merge (cmd-fn (get-in state [:progs prog]) args)))))

(defn execute-p1
  "Execute the assembly programs instructions until it returns a recover value"
  [insts]
  (->> {:insts insts :pos 0}
       (iterate step-p1)
       (drop-while running-p1?)
       first
       :snd))

(defn execute-p2
  [insts]
  (->> {:insts insts}))

;; Puzzle solutions
(defn part1
  "What is the value of the recovered frequency the first time a `rcv` 
   instruction is executed with a non-zero value?"
  [input]
  (execute-p1 input))


;; Let's think about Part 2 now
;; We have one common set of instructions, but now we've got two
;; programs executing them independently and sending each other
;; messages over a queue.
;; The register values and positions are unique to each program now.
;;
;; Here's a possible structure for the state
(def p2-init-progs
  {0 {:pos 0
      :snd-cnt 0
      :queue []
      :waiting false
      "p" 0}
   1 {:pos 0
      :snd-cnt 0
      :queue []
      :waiting false
      "p" 1}})

(comment
  {:insts []
   :progs {0 {:pos 0
              :queue []
              :snd-count 0
              :waiting false
              "p" 0}
           1 {:pos 0
              :queue []
              :snd-count 0
              :waiting false
              "p" 1}}})
