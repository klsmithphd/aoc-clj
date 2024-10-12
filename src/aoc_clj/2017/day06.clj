(ns aoc-clj.2017.day06
  "Solution to https://adventofcode.com/2017/day/6"
  (:require [aoc-clj.utils.core :as u]))

;; Input parsing
(defn parse
  [input]
  (->> (first input)
       (re-seq #"\d+")
       (mapv read-string)))

;; Puzzle logic
(defn largest-value
  "Given a collection, returns a vec of the index in the collection and
   the value of the largest value. When there are ties, the earliest value
   is returned"
  [nums]
  (let [max-val (apply max nums)
        idx     (u/index-of (u/equals? max-val) nums)]
    [idx max-val]))

(defn reallocate
  "Reallocate the memory blocks from the largest blank one at a time 
   sequentially to the next blocks (looping around if necessary).
   Returns a new vec of the number of blocks in each memory bank"
  [banks]
  (let [len (count banks)
        [idx mx] (largest-value banks)
        distro (->> (concat (repeat (inc idx) 0)
                            (repeat mx 1))
                    (partition len len (repeat 0)))]
    (apply mapv + (concat [(assoc banks idx 0)] distro))))

(defn cycles-to-repeat
  "Number of cycles until a repeat value is seen"
  [nums]
  (second (u/first-duplicates (iterate reallocate nums))))

(defn loop-size
  "Size of the loop of repeating values"
  [nums]
  (let [[frst scnd] (u/first-duplicates (iterate reallocate nums))]
    (- scnd frst)))

;; Puzzle solutions
(defn part1
  "How many redistribution cycles must be completed before a configuration
   that has been seen before is reproduced?"
  [input]
  (cycles-to-repeat input))

(defn part2
  "How many cycles are in the infinite loop?"
  [input]
  (loop-size input))