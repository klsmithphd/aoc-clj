(ns aoc-clj.2017.day13
  "Solution to https://adventofcode.com/2017/day/13")

;; Input parsing
(defn parse-line
  [line]
  (mapv read-string (re-seq #"\d+" line)))

(defn parse
  [input]
  (into {} (map parse-line input)))

;; Puzzle logic
(defn caught?
  "Determines whether you'll be caught by the given scanner, presuming
   you move to the right one layer at a time starting at t=0"
  [[layer size]]
  (let [freq (* 2 (dec size))]
    (zero? (mod layer freq))))

(defn severity
  "Severity is the sum of the products of the layer depth and the range size
   of the scanners that catch you when you start at t=0"
  [scanners]
  (->> (filter caught? scanners)
       (map #(apply * %))
       (reduce +)))

;; Puzzle solutions
(defn part1
  "What's the severity of your whol trip?"
  [input]
  (severity input))