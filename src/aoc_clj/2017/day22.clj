(ns aoc-clj.2017.day22
  "Solution to https://adventofcode.com/2017/day/22"
  (:require [aoc-clj.utils.grid :as grid]))

;; Constants
(def part1-burst-count 10000)
(def part2-burst-count 10000000)

;; Input parsing
(defn parse
  [input]
  (let [size (count input)
        dim  (quot size 2)]
    (->>
     (zipmap (for [y (reverse (range (- dim) (inc dim)))
                   x (range (- dim) (inc dim))]
               [x y])
             (apply concat input))
     (filter #(= \# (val %)))
     keys
     set)))

;; Puzzle logic
(defn turn
  "Return a new state with the virus turned based the infected state of
   the cell it's currently on"
  [{:keys [infected pos] :as state}]
  (if (infected pos)
    (grid/turn state :right)
    (grid/turn state :left)))

(defn cell-update
  "Return a new state with the cell toggled from infected or cleaned"
  [{:keys [infected pos] :as state}]
  (if (infected pos)
    (update state :infected disj pos)
    (-> state
        (update :infected conj pos)
        (update :infect-cnt inc))))

(defn step
  "Return a new state for a single interation"
  [state]
  (-> state
      turn
      cell-update
      (grid/forward 1)))

(defn init-state
  "Create an initial state map based on what cells are initially infected"
  [infected]
  {:infected infected :infect-cnt 0 :pos [0 0] :heading :n})

(defn infections-caused-at-n
  "Returns the number of times an infection was caused after n bursts"
  [infections n]
  (->> (init-state infections)
       (iterate step)
       (drop n)
       first
       :infect-cnt))

;; Puzzle solutions
(defn part1
  "After 10000 bursts of activity, how many bursts cause a node to
   become infected?"
  [input]
  (infections-caused-at-n input part1-burst-count))