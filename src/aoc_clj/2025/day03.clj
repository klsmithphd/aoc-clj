(ns aoc-clj.2025.day03
  "Solution to https://adventofcode.com/2025/day/3"
  (:require [aoc-clj.utils.core :as u]))

;; Input parsing
(defn parse-line
  [line]
  (map #(Character/digit % 10) line))

(defn parse
  [input]
  (map parse-line input))

;; Puzzle logic
(defn two-max
  "Returns the two largest numbers from a collection"
  [nums]
  (->> nums
       (sort >)
       (take 2)))

(defn digit-positions
  [nums]
  (->> (map-indexed vector nums)
       (group-by second)
       (u/fmap #(mapv first %))))

(defn next-digit
  [digit-pos a a-pos])

;; (defn largest-two-digits
;;   [nums]
;;   (let [digit-pos (digit-positions nums)
;;         digits    (sort > (keys digit-pos))
;;         start     (first digits)
;;         start-pos (first (digit-pos start))]
;;     (loop [a start a-pos start-pos]
;;       (if ()))))
;; 
(keys (digit-positions [8 1 8 1 8 1 9 1 1 1 1 2 1 1 1]))

;; Thoughts
;; Create a map of digit to position in order, e.g.
;; {8 [0 2 4] 1 [1 3 5 7 8 9 10 12 13 14] 9 [6] 2 [11]} 
;; select the keys by decending order, pick the largest key,
;; see if any lower keys have an index greater than the smallest
;; index of the current key. Keep iterating through such
;; combinations are found
;; So far (at least for part 1), it's sufficient to just check
;; the min and max indices for a match, i.e. the min index of
;; the first key and the max index for the second key.

;; Puzzle solutions
(defn part1
  [_]
  :not-implemented)

(defn part2
  [_]
  :not-implemented)