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
  [nums]
  (let [len (count nums)
        [idx mx] (largest-value nums)
        distro (->> (concat (repeat (inc idx) 0)
                            (repeat mx 1))
                    (partition len len (repeat 0)))]
    (apply mapv + (concat [(assoc nums idx 0)] distro))))

(defn cycles-to-repeat
  [nums]
  (let [infinite (iterate reallocate nums)
        first-dupe (u/first-duplicate infinite)
        first-time (u/index-of (u/equals? first-dupe) infinite)
        second-time (u/index-of (u/equals? first-dupe) (drop (inc first-time) infinite))]
    [(+ 1 first-time second-time) (+ 1 second-time)]))

;; Puzzle solutions
(defn part1
  [input]
  (first (cycles-to-repeat input)))

(defn part2
  [input]
  (second (cycles-to-repeat input)))