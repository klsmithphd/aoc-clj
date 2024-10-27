(ns aoc-clj.2017.day23
  "Solution to https://adventofcode.com/2017/day/23"
  (:require [aoc-clj.2017.day18 :as d18]))

;; Input parsing
(def parse d18/parse)

;; Puzzle logic
(def arg-val d18/arg-val)

(defn init-state
  [insts]
  {:insts insts :pos 0 :mul-count 0})

(defn set-cmd
  "Returns the new state for a `set` instruction, which sets the
   first argument's register value to the second argument's value"
  [state [x y]]
  (-> state
      (assoc x (arg-val state y))
      (update :pos inc)))

(defn sub-cmd
  "Returns the new state for an `sub` instruction, which updates
   the first argument's register value by subtracting the second argument's
   value."
  [state [x y]]
  (-> state
      (assoc x (- (arg-val state x) (arg-val state y)))
      (update :pos inc)))

(defn mul-cmd
  "Returns the new state for an `mul` instruction, which updates
   the first argument's register value by multiplying the second argument's
   value."
  [state [x y]]
  (-> state
      (assoc x (* (arg-val state x) (arg-val state y)))
      (update :mul-count inc)
      (update :pos inc)))

(defn jnz-cmd
  "Returns the new state for a `jgz` instruction, which jumps
   in instruction position by the offset of the second argument only
   if the value in the first argument's register is greater than zero."
  [state [x y]]
  (if (zero? (arg-val state x))
    (update state :pos inc)
    (update state :pos + (arg-val state y))))

(def cmd-map
  {"set" set-cmd
   "sub" sub-cmd
   "mul" mul-cmd
   "jnz" jnz-cmd})

(defn step
  "Evolves the state forward one step and returns the new state."
  [{:keys [insts pos] :as state}]
  (let [[cmd args] (insts pos)
        cmd-fn     (cmd-map cmd)]
    (cmd-fn state args)))

(defn running?
  "Returns true if the current instruction position is within the bounds
   of the program"
  [{:keys [insts pos]}]
  (< -1 pos (count insts)))

(defn execute
  "Run the program until it's complete and return the final execution state"
  [insts]
  (->> (init-state insts)
       (iterate step)
       (drop-while running?)
       first))

(defn mul-count
  [insts]
  (->> (execute insts)
       :mul-count))

;; Puzzle solutions
(defn part1
  [input]
  (mul-count input))