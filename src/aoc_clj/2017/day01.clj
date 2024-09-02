(ns aoc-clj.2017.day01
  "Solution to https://adventofcode.com/2017/day/1")

;; Input parsing
(defn parse
  [input]
  (map (comp read-string str) (first input)))

;; Puzzle logic
(defn match-value
  "If the two values are equal, return the value else zero"
  [[a b]]
  (if (= a b)
    a
    0))

(defn wrap-around
  "Tack on the first element to the end of the collection to
   simulate wrapping all the way around"
  [digits]
  (conj digits (first digits)))

(defn sum-of-matching-digits
  "Compute the sum of all the values who match the very next value
   in the sequence, wrapping around to the beginning for the last
   value"
  [digits]
  (->> (wrap-around digits)
       (partition 2 1)
       (map match-value)
       (reduce +)))

(defn sum-of-halfway-around-matching-digits
  "Compute the sum of all the values who match a value halfway
   around the collection."
  [digits]
  (let [half (/ (count digits) 2)]
    (->> (map vector (take half digits) (drop half digits))
         (map match-value)
         (reduce +)
         (* 2))))

;; Puzzle solutions
(defn part1
  "Sum of all the digits that match the next digit in the list"
  [input]
  (sum-of-matching-digits input))

(defn part2
  "Sum of all the digits that match the digit halfway around the list"
  [input]
  (sum-of-halfway-around-matching-digits input))