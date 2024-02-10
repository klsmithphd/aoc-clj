(ns aoc-clj.2015.day20
  "Solution to https://adventofcode.com/2015/day/20")

;; Input parsing
(def parse (comp read-string first))

;; Puzzle logic
(defn pos-neg-range
  []
  (interleave (drop 1 (range)) (map #(* -1 %) (drop 1 (range)))))

(defn gen-pentagonal-nums
  "[Generalized pentagonal numbers](https://en.wikipedia.org/wiki/Pentagonal_number)"
  []
  (for [i (pos-neg-range)]
    (/ (- (* 3 i i) i) 2)))

(def sum-of-divisors
  "The [sum-of-divisors function](https://en.wikipedia.org/wiki/Divisor_function)
   for z=1, aka sigma_1(n)
   
   This implementation uses Euler's recurrence relationship from:
   https://en.wikipedia.org/wiki/Divisor_function#Other_properties_and_identities"
  (memoize
   (fn [n]
     (let [recurrences (take-while #(<= % n) (gen-pentagonal-nums))]
       (->> (map #(if (zero? (- n %)) n (sum-of-divisors (- n %))) recurrences)
            (partition 2 2 [])
            (map #(reduce + %))
            (map #(* %1 %2) (cycle [1 -1]))
            (reduce +))))))

(defn house-presents
  "Computes the number of presents delivered to the house numbered `house`"
  [house]
  (* 10 (sum-of-divisors house)))

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
  (first-house-with-n-presents house-presents 0 input))

(defn part2
  "The lowest house number that gets at least as many presents as input
   using the part2 present distribution logic"
  [input]
  (first-house-with-n-presents house-presents-part2 786239 input))