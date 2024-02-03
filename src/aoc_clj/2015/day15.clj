(ns aoc-clj.2015.day15
  "Solution to https://adventofcode.com/2015/day/15")

;; Constants
(def max-teaspoons 100)

;; Input parsing
(defn parse-line
  [line]
  (mapv read-string (re-seq #"-?\d+" line)))

(defn parse
  [input]
  (mapv parse-line input))

;; Puzzle logic
(defn score-vec
  "Computes the score component vector, where each element is the sum of 
   the products of each ingredient property and the quantity of that
   ingredient"
  [ingredients quantities]
  (->> (map #(map (partial * %2) %1) ingredients quantities)
       (apply (partial map +))
       (map #(if (neg? %) 0 %))))

(defn score
  "The score is the product of the first four properties"
  [score-vec]
  (->> score-vec (take 4) (reduce *)))

(defn five-hundred-cal?
  "Returns true if this combination results in exactly 500 calories"
  [[_ _ _ _ cals]]
  (= 500 cals))

(defn all-options
  "Returns a sequence of all possible combinations of `n` items that sum
   to `total`"
  [total n]
  ;; sub-options is a function that "pins" the first value as total minus x
  ;; and computes all combinations for the remaining values that sum to x
  (letfn [(sub-options [x]
            (mapv #(concat [(- total x)] %) (all-options x (dec n))))]
    (if (= 2 n)
      (map #(vector (- total %) %) (range (inc total)))
      (mapcat sub-options (range (inc total))))))

(defn max-score
  "Computes the maximum possible score for the given ingredients. If
   `cal-constraint` is `true`, only combinations that have exactly 500
   calories will be considered"
  [ingredients cal-constraint]
  (->>
   ;; Generate all valid options for the quantities of each ingredient
   (all-options max-teaspoons (count ingredients))
   ;; Compute the score component vector for each option
   (map #(score-vec ingredients %))
   ;; If `cal-constraint` is true, restrict to options that have 500 calories
   (filter (if cal-constraint five-hundred-cal? identity))
   ;; Compute the final score
   (map score)
   ;; Select the maximum
   (apply max)))

;; Puzzle solutions
(defn part1
  "Computes the maximum cookie score for the given ingredients"
  [input]
  (max-score input false))

(defn part2
  "Computes the maximum cookie score for the given ingredients with the 
   restriction that the cookies must have exactly 500 calories"
  [input]
  (max-score input true))