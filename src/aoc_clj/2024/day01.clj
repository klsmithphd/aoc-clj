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
  [[l1 l2]]
  (map (comp abs -) (sort l1) (sort l2)))

(defn total-distance
  [lists]
  (reduce + (list-diffs lists)))

(defn similarity-score
  [[l1 l2]]
  (let [freqs (frequencies l2)]
    (->> l1
         (map #(* % (get freqs % 0)))
         (reduce +))))

;; Puzzle solutions
(defn part1
  [input]
  (total-distance input))

(defn part2
  [input]
  (similarity-score input))