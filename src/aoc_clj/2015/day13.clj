(ns aoc-clj.2015.day13
  "Solution to https://adventofcode.com/2015/day/13"
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :as combo]
            [aoc-clj.utils.core :as u]))

;; Input parsing
(defn parse-line
  [line]
  (let [bits (str/split line #" ")
        person1   (nth bits 0)
        gain-loss (if (= "gain" (nth bits 2)) 1 -1)
        amount    (read-string  (nth bits 3))
        person2   (nth bits 10)]
    [person1 [(subs person2 0 (dec (count person2)))
              (* gain-loss amount)]]))

(defn parse
  [input]
  (->> (map parse-line input)
       (group-by first)
       (u/fmap #(into {} (map second %)))))

;; Puzzle logic
(defn happiness
  "Compute a happiness score for a given guest based on who their
   neighbors are and their known preferences"
  [prefs [n1 guest n2]]
  (+  (get-in prefs [guest n1])
      (get-in prefs [guest n2])))

(defn table-circle
  "Takes a collection of n elements and makes it into a collection 2 items
   longer, wrapping around the first and second elements to the end, i.e.,
   it take [1, 2, 3, 4] and turns it into [1, 2, 3, 4, 1, 2]. This can
   then be used to take 3 elements at a time and compute the happiness
   of the center person"
  [coll]
  (concat coll (take 2 coll)))

(defn happiness-total
  "Compute the total happiness score for the given ordering of guests"
  [prefs ordering]
  (->> (table-circle ordering)
       (partition 3 1)
       (map (partial happiness prefs))
       (reduce +)))

(defn arrangements
  "Returns the (n-1)! possible arrangements of the guests around the table.
   
   Technically there should be only half of that number to account because
   the clockwise/counter-clockwise orderings around the table are equivalent"
  [prefs]
  (let [guests (keys prefs)]
    (map #(concat [(first guests)] %) (combo/permutations (rest guests)))))

(defn max-happiness
  "Find the maximum happiness of any possible configuration of guests"
  [prefs]
  (->> (arrangements prefs)
       (map (partial happiness-total prefs))
       (apply max)))

(defn add-me
  "Insert a new guest (me) into the mix, whose happiness scores are all zero"
  [prefs]
  (let [new-prefs (u/fmap #(assoc % "Me" 0) prefs)]
    (assoc new-prefs "Me" (zipmap (keys prefs) (repeat 0)))))

;; Puzzle solutions
(defn part1
  "Find the maximum happiness score possible for all guests"
  [input]
  (max-happiness input))

(defn part2
  "Find the maximum happiness score including me in the seating plan"
  [input]
  (max-happiness (add-me input)))