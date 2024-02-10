(ns aoc-clj.2015.day20
  "Solution to https://adventofcode.com/2015/day/20")

;; Input parsing
(def parse (comp read-string first))

;; Puzzle logic
(defn house-presents
  "Computes the number of presents delivered to the house numbered `house`"
  [house]
  (let [factors (->> (range 1 (inc house))
                     (filter #(zero? (rem house %))))]
    (* 10 (reduce + factors))))

(defn house-presents-part2
  "Computes the number of presents delivered to teh house numbered `house`
   using the rules of part2"
  [house]
  (let [start   (int (Math/ceil (/ house 50)))
        factors (->> (range (if (zero? start) 1 start) (inc house))
                     (filter #(zero? (rem house %))))]
    (* 11 (reduce + factors))))

(defn first-house-with-n-presents
  "The first house number that has at least `n` presents delivered to it,
   using the present-distribution logic fn `present-logic`."
  [present-logic start n]
  (->> (range start n)
       (map #(vector % (present-logic %)))
       (filter #(>= (second %) n))
       ffirst))

;; Puzzle solutions
(defn part1
  "The lowest house number that gets at least as many presents as input"
  [input]
  (first-house-with-n-presents house-presents 776159 input))

(defn part2
  "The lowest house number that gets at least as many presents as input
   using the part2 present distribution logic"
  [input]
  (first-house-with-n-presents house-presents-part2 786239 input))