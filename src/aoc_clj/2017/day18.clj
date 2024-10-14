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
  [{:keys [regs]} x]
  (if (number? x)
    x
    (get regs x 0)))

(defn add-cmd
  [state [x y]]
  (-> state
      (assoc-in [:regs x] (+ (arg-val state x) (arg-val state y)))
      (update :pos inc)))

(defn jgz-cmd
  [state [x y]]
  (if (> (arg-val state x) 0)
    (update state :pos + y)
    (update state :pos inc)))

(defn mod-cmd
  [state [x y]]
  (-> state
      (assoc-in [:regs x] (mod (arg-val state x) (arg-val state y)))
      (update :pos inc)))

(defn mul-cmd
  [state [x y]]
  (-> state
      (assoc-in [:regs x] (* (arg-val state x) (arg-val state y)))
      (update :pos inc)))

(defn rcv-cmd
  [state [x]]
  (if (zero? (arg-val state x))
    (update state :pos inc)
    (assoc state :pos -1)))

(defn set-cmd
  [state [x y]]
  (-> state
      (assoc-in [:regs x] (arg-val state y))
      (update :pos inc)))

(defn snd-cmd
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
  [{:keys [insts pos snd] :as state}]
  (if (< -1 pos (count insts))
    (let [[cmd args] (insts pos)]
      ((cmd-map cmd) state args))
    snd))

(defn execute
  [insts]
  (->> {:insts insts :regs {} :pos 0}
       (iterate step)
       (drop-while map?)
       first))

;; Puzzle solutions
(defn part1
  [input]
  (execute input))