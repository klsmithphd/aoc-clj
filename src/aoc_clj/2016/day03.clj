(ns aoc-clj.2016.day03
  "Solution to https://adventofcode.com/2016/day/3")

(defn parse-line
  [line]
  (read-string (str "[" line "]")))

(defn parse
  [input]
  (map parse-line input))

(defn valid-triangle?
  [[a b c]]
  (and (> (+ a b) c)
       (> (+ b c) a)
       (> (+ c a) b)))

(defn count-of-valid-triangles
  [input]
  (->> input (filter valid-triangle?) count))

(defn group-by-columns
  [input]
  (->> (concat (map #(nth % 0) input)
               (map #(nth % 1) input)
               (map #(nth % 2) input))
       (partition 3)))

(defn day03-part1-soln
  [input]
  (count-of-valid-triangles input))

(defn day03-part2-soln
  [input]
  (-> input group-by-columns count-of-valid-triangles))