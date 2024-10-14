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
  [{:keys [regs]} x]
  (if (number? x)
    x
    (get regs x 0)))

(defn add-cmd
  "`add X Y` **increases** register X by the value of Y"
  [state [x y]]
  (-> state
      (assoc-in [:regs x] (+ (arg-val state x) (arg-val state y)))
      (update :pos inc)))

(defn jgz-cmd
  "`jgz X Y` **jumps** with an offset of the value of Y, but only if 
   the value of X is greater than zero."
  [state [x y]]
  (if (> (arg-val state x) 0)
    (update state :pos + y)
    (update state :pos inc)))

(defn mod-cmd
  "`mod X Y` sets register X to the **remainder** of dividing the value 
   contained in register X by the value of Y"
  [state [x y]]
  (-> state
      (assoc-in [:regs x] (mod (arg-val state x) (arg-val state y)))
      (update :pos inc)))

(defn mul-cmd
  "`mul X Y` sets register X to the result of **multiplying** the value 
   contained in register X by the value of Y."
  [state [x y]]
  (-> state
      (assoc-in [:regs x] (* (arg-val state x) (arg-val state y)))
      (update :pos inc)))

(defn rcv-cmd
  "`rcv X` **recovers** the frequency of the last sound played, but only when 
   the value of X is not zero. (If it is zero, the command does nothing.)"
  [state [x]]
  (if (zero? (arg-val state x))
    (update state :pos inc)
    (assoc state :pos -1)))

(defn set-cmd
  "`set X Y` **sets** register X to the value of Y."
  [state [x y]]
  (-> state
      (assoc-in [:regs x] (arg-val state y))
      (update :pos inc)))

(defn snd-cmd
  "`snd X` **plays a sound** with a frequency equal to the value of X."
  [state [x]]
  (-> state
      (assoc :snd (arg-val state x))
      (update :pos inc)))

(def cmd-map
  {"add" add-cmd
   "jgz" jgz-cmd
   "mod" mod-cmd
   "mul" mul-cmd
   "rcv" rcv-cmd
   "set" set-cmd
   "snd" snd-cmd})

(defn step
  "Evolve the state by one step based on the instruction indicated by `pos`"
  [{:keys [insts pos snd] :as state}]
  (if (< -1 pos (count insts))
    (let [[cmd args] (insts pos)]
      ((cmd-map cmd) state args))
    snd))

(defn execute
  "Execute the assembly programs instructions until it returns a recover value"
  [insts]
  (->> {:insts insts :regs {} :pos 0}
       (iterate step)
       (drop-while map?)
       first))

;; Puzzle solutions
(defn part1
  "What is the value of the recovered frequency the first time a `rcv` 
   instruction is executed with a non-zero value?"
  [input]
  (execute input))