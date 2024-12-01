(ns aoc-clj.2024.day01
  "Solution to https://adventofcode.com/2024/day/1")

;; Input parsing
(defn parse-line
  [line]
  (map read-string (re-seq #"\d+" line)))

(defn parse
  [input]
  (let [pairs (map parse-line input)]
    [(map first pairs)
     (map second pairs)]))

;; Puzzle logic
(defn list-diffs
  "Returns the individual distances (absolute difference)
   between two lists when sorted in ascending order"
  [[l1 l2]]
  (map (comp abs -) (sort l1) (sort l2)))

(defn total-distance
  "Computes the total distance between two lists"
  [lists]
  (reduce + (list-diffs lists)))

(defn similarity-score
  "Returns the similarity score between two lists of numbers,
   defined as the sum of the product of each number in the
   first list and the number of times it appears in the second
   list"
  [[l1 l2]]
  (let [freqs (frequencies l2)]
    (->> l1
         (map #(* % (get freqs % 0)))
         (reduce +))))

;; Puzzle solutions
(defn part1
  "What is the total distance between your lists?"
  [input]
  (total-distance input))

(defn part2
  "What is their similarity score?"
  [input]
  (similarity-score input))