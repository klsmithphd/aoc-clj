(ns aoc-clj.2024.day11
  "Solution to https://adventofcode.com/2024/day/11"
  (:require [clojure.string :as str]
            [clojure.math :as math]))

;; Constants
(def part1-blinks 25)
(def part2-blinks 75)

;; Input parsing
(defn parse
  [input]
  (map read-string (str/split (first input) #" ")))

;; Puzzle logic
(defn even-divisor
  "If `num` has an even number of digits, returns the divisor to split the
   number into two equal-sized numbers. Otherwise, returns 0"
  [num]
  (let [digits (count (str num))
        div    (long (math/pow 10 (/ digits 2)))]
    (if (even? digits)
      div
      0)))

;; The number of stones will grow exponentially, so it becomes infeasible
;; to keep track of a collection of the stones directly. Fortunately, we only
;; need to know the number of stones available at any given time.
;; We update a map of the stones to their counts, which might grow large,
;; but never prohibitively so.
(defn stone-count-updater
  "For a given `stone`, and the `cnt` of times seen so far, updates
   the `new-counts` map after another blink."
  [new-counts [stone cnt]]
  (if (zero? stone)
    (update new-counts 1 (fnil + 0) cnt)
    (let [div (even-divisor stone)]
      (if (pos? div)
        (-> new-counts
            (update (quot stone div) (fnil + 0) cnt)
            (update (mod stone div)  (fnil + 0) cnt))
        (update new-counts (* 2024 stone) (fnil + 0) cnt)))))

(defn blink
  "Updates the map of the counts of each stone after one blink"
  [stone-counts]
  (reduce stone-count-updater {} stone-counts))

(defn stones-after-n-blinks
  "Returns the count of stones present after n iterations"
  [n stones]
  (->> (frequencies stones)
       (iterate blink)
       (drop n)
       first
       vals
       (reduce +)))

;; Puzzle solutions
(defn part1
  "How many stones will you have after blinking 25 times?"
  [input]
  (stones-after-n-blinks part1-blinks input))

(defn part2
  "How many stones would you have after blinking a total of 75 times?"
  [input]
  (stones-after-n-blinks part2-blinks input))