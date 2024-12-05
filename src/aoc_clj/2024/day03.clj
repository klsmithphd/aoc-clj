(ns aoc-clj.2024.day03
  "Solution to https://adventofcode.com/2024/day/3")

;; Constants
(def mul-inst-pattern
  (re-pattern #"mul\(\d{1,3},\d{1,3}\)"))

(def part2-pattern
  (re-pattern #"mul\(\d{1,3},\d{1,3}\)|do\(\)|don't\(\)"))

;; Input parsing
(defn parse
  [input]
  (apply str input))

;; Puzzle logic
(defn real-mul-insts
  "Finds the *real* mul instructions in a given string"
  [s]
  (re-seq mul-inst-pattern s))

(defn all-insts
  "Finds all the real `mul`, `do()`, and `don't()` instructions"
  [s]
  (re-seq part2-pattern s))

(defn enablement-groups
  "Partitions a list of instructions including `do()` and `don't()
   into groups of mul(X,Y) instructions following the do/don'ts"
  [insts]
  (let [[fst & rem] (partition-by #(= "d" (subs % 0 1)) insts)
        groups (->> (partition 2 rem)
                    (map #(apply concat %)))]
    (into [fst] groups)))

(defn enabled-insts
  "Returns a seq of only the enabled real mul instructions"
  [s]
  (->> s
       all-insts
       enablement-groups
       (remove #(= "don't()" (first %)))
       (mapcat #(if (= "do()" (first %))
                  (rest %)
                  %))))

(defn mul-args
  "Extracts the multiplicands from a mul inst string"
  [s]
  (map read-string (re-seq #"\d+" s)))

(defn sum-of-products
  "Given a set of enabled real mul instructions, computes the sum of
   the products"
  [insts]
  (->> insts
       (map mul-args)
       (map #(apply * %))
       (reduce +)))

;; Puzzle solutions
(defn part1
  "What do you get if you add up all of the results of the multiplications?"
  [input]
  (sum-of-products (real-mul-insts input)))

(defn part2
  "What do you get if you add up all of the results of just the 
   enabled multiplications?"
  [input]
  (sum-of-products (enabled-insts input)))