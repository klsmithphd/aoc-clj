(ns aoc-clj.2015.day17
  "Solution to https://adventofcode.com/2015/day/17"
  (:require [clojure.math.combinatorics :as combo]))

;; Input parsing
(defn parse
  [input]
  (map read-string input))

;; Puzzle logic
(defn total-options
  [total containers]
  (if (zero? total)
    ;; If we get down to zero for the target total, we've succeeded, so this
    ;; counts as one option
    1
    (if (or (neg? total) (> total (reduce + containers)))
      ;; If the remaining containers aren't enough to meet the target, 
      ;; or if we've overshot the target and gone negative, don't look any 
      ;; further and return zero indicating a dead branch
      0
      ;; Otherwise, we explore the sub-problem of picking each container
      ;; (in descending size order), removing its size from the total, and
      ;; computing the total options for meeting the new smaller target total
      ;; from the remaining containers.
      ;;
      ;; First, filter to only containers that are less than or equal to 
      ;; our target
      (let [ctrs (filter #(<= % total) containers)]
        (->> (range (count ctrs))
             (map #(total-options (- total (nth ctrs %)) (drop (inc %) ctrs)))
             (reduce +))))))



(defn sums-to?
  [sum coll]
  (= sum (reduce + (map second coll))))

(defn combinations
  [sum containers]
  (->> (map-indexed vector containers)
       (combo/subsets)
       (filter (partial sums-to? sum))
       (map (partial map  second))))

(defn minimal-combinations
  [sum containers]
  (let [combos (combinations sum containers)
        min-containers (apply min (map count combos))]
    (filter #(= min-containers (count %)) combos)))

;; Puzzle solutions
(defn part1
  [input]
  (total-options 150 input)
  ;; (count (combinations 150 input))
  )

(defn part2
  [input]
  (count (minimal-combinations 150 input)))