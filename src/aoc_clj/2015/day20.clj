(ns aoc-clj.2015.day20
  "Solution to https://adventofcode.com/2015/day/20")

(def parse (comp read-string first))

(defn house-presents
  [house]
  (let [factors (->> (range 1 (inc house))
                     (filter #(zero? (rem house %))))]
    (* 10 (reduce + factors))))

(defn first-house-with-n-presents
  [present-logic start input]
  (ffirst (filter #(>= (second %) input) (map #(vector % (present-logic %)) (range start input)))))

(defn house-presents-part2
  [house]
  (let [start   (int (Math/ceil (/ house 50)))
        factors (->> (range (if (zero? start) 1 start) (inc house))
                     (filter #(zero? (rem house %))))]
    (* 11 (reduce + factors))))

(defn day20-part1-soln
  [input]
  (first-house-with-n-presents house-presents 776159 input))

(defn day20-part2-soln
  [input]
  (first-house-with-n-presents house-presents-part2 786239 input))