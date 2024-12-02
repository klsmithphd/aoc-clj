(ns aoc-clj.2024.day02
  "Solution to https://adventofcode.com/2024/day/2")

;; Input parsing
(defn parse-line
  [line]
  (map read-string (re-seq #"\d+" line)))

(defn parse
  [input]
  (map parse-line input))

;; Puzzle logic
(defn all-increasing?
  [nums]
  (apply < nums))

(defn all-decreasing?
  [nums]
  (apply > nums))

(defn safe-diff-size?
  [nums]
  (->> (partition 2 1 nums)
       (map #(apply (comp abs -) %))
       (every? #(<= 1 % 3))))

(defn safe?
  [nums]
  (and (or (all-decreasing? nums)
           (all-increasing? nums))
       (safe-diff-size? nums)))

(defn safe-count
  [all-nums]
  (count (filter safe? all-nums)))

;; Puzzle solutions
(defn part1
  [input]
  (safe-count input))