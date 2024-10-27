(ns aoc-clj.2017.day22
  "Solution to https://adventofcode.com/2017/day/22"
  (:require [aoc-clj.utils.grid :as grid]))

;; Constants
(def burst-count-p1 10000)
(def burst-count-p2 10000000)

(def cell-status-map
  {0 :clean
   1 :weakened
   2 :infected
   3 :flagged})

(def pre-infected-status
  "Mapping for part1/part2 for the status of a node that is going
   to become infected"
  {:part1 :clean :part2 :weakened})

(def status-change-val
  "Mapping for how much to increment a cell's value in part1/part2.
   
   In part 1, cells toggle between 0 and 2 (clean and infected)
   In part 2, cells evolve from 0->1->2->3->0"
  {:part1 2 :part2 1})

;; Input parsing
(defn parse
  [input]
  (let [size     (count input)
        dim      (quot size 2)
        coords   (for [y (reverse (range (- dim) (inc dim)))
                       x (range (- dim) (inc dim))]
                   [x y])
        infected (->> (zipmap coords (apply concat input))
                      (filter #(= \# (val %)))
                      keys)]
    {:cells (zipmap infected (repeat 2))
     :infect-cnt 0 :pos [0 0] :heading :n}))

;; Puzzle logic
(defn status
  "Returns the status of the grid node currently occupied"
  [{:keys [cells pos]}]
  (cell-status-map (get cells pos 0)))

(defn turn
  "Returns the updated state with the virus turned according to the
   status of the grid node it's currently at."
  [state]
  (case (status state)
    :clean    (grid/turn state :left)
    :weakened state
    :infected (grid/turn state :right)
    :flagged  (grid/turn state :backward)))

(defn inc-infected-cnt
  "Returns the updated state, incrementing the infected count if
   a node became newly infected this step."
  [state part]
  (if (= (pre-infected-status part) (status state))
    (update state :infect-cnt inc)
    state))

(defn cell-update
  "Returns the updated state, with the currently occupied node's status evolved"
  [{:keys [pos] :as state} part]
  (let [delta (status-change-val part)]
    (-> state
        (inc-infected-cnt part)
        (update-in [:cells pos] #(if (nil? %) delta (mod (+ % delta) 4))))))

(defn step
  "Returns the new state after the virus takes one move."
  [part state]
  (-> state
      turn
      (cell-update part)
      (grid/forward 1)))

(def step-p1 (partial step :part1))
(def step-p2 (partial step :part2))
(def step-fn {:part1 step-p1 :part2 step-p2})

(defn infections-caused-at-n
  "Returns the number of times an infection was caused after n bursts"
  [part init-state n]
  (let [stepper (step-fn part)]
    (->> init-state
         (iterate stepper)
         (drop n)
         first
         :infect-cnt)))

;; Puzzle solutions
(defn part1
  "After 10000 bursts of activity, how many bursts cause a node to
   become infected?"
  [input]
  (infections-caused-at-n :part1 input burst-count-p1))

(defn part2
  "After 10000000 bursts of activity, how many bursts cause a node to 
   become infected?"
  [input]
  (infections-caused-at-n :part2 input burst-count-p2))