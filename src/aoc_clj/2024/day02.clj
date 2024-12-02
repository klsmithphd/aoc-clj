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

(defn step-size
  [nums]
  (->> (partition 2 1 nums)
       (map (fn [[a b]] (- b a)))))

(defn safe-pos-step-count
  [steps]
  (count (filter #(<= 1 % 3) steps)))

(defn safe-neg-step-count
  [steps]
  (count (filter #(<= -3 % -1) steps)))

(defn safe-check-count
  [nums]
  (let [steps (step-size nums)]
    (max (safe-pos-step-count steps)
         (safe-neg-step-count steps))))

(defn safe-count
  [all-nums]
  (count (filter safe? all-nums)))

(defn without-index
  [n coll]
  (let [[l r] (split-at n coll)]
    (concat l (rest r))))

(defn safe-without-a-level
  [nums]
  (->> (range (count nums))
       (map #(without-index % nums))
       (some safe?)
       boolean))

(defn weaker-safe-count
  [all-nums]
  (count (filter safe-without-a-level all-nums)))

;; Puzzle solutions
(defn part1
  [input]
  (safe-count input))

(defn part2
  [input]
  (weaker-safe-count input))