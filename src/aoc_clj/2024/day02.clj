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
(defn step-size
  "Computes the deltas between consecutive elements in nums"
  [nums]
  (->> (partition 2 1 nums)
       (map (fn [[a b]] (- b a)))))

(defn all-safe-pos-steps?
  "Returns true if all the steps are positive between 1 and 3 inclusive"
  [steps]
  (every? #(<= 1 % 3) steps))

(defn all-safe-neg-steps?
  "Returns true if all the steps are negative between -3 and -1 inclusive"
  [steps]
  (every? #(<= -3 % -1) steps))

(defn safe?
  "Returns true if all the steps are either positve or negative in the
   and are between 1 and 3 in absolute size"
  [nums]
  (let [steps (step-size nums)]
    (or (all-safe-neg-steps? steps)
        (all-safe-pos-steps? steps))))

(defn safe-count
  "Returns the count of all safe combinations"
  [all-nums]
  (count (filter safe? all-nums)))

(defn without-index
  "Returns a new coll with the item at index `n` removed"
  [n coll]
  (let [[l r] (split-at n coll)]
    (concat l (rest r))))

(defn safe-without-a-level
  "Returns true if the collection would be deemed safe if
   just a single element were removed"
  [nums]
  (->> (range (count nums))
       (map #(without-index % nums))
       (some safe?)
       boolean))

(defn weaker-safe-count
  "Counts all the number combinations that pass the weaker safety check"
  [all-nums]
  (count (filter safe-without-a-level all-nums)))

;; Puzzle solutions
(defn part1
  "How many reports are safe?"
  [input]
  (safe-count input))

(defn part2
  "How many reports are now safe?"
  [input]
  (weaker-safe-count input))