(ns aoc-clj.2015.day20
  "Solution to https://adventofcode.com/2015/day/20"
  (:require [aoc-clj.utils.core :as u]))

;; Constants
(def house-limit 800000)
(def elf-limit 50)

;; Input parsing
(def parse (comp read-string first))

;; Puzzle logic
(defn presents
  "Finds the number of presents delivered to each house, from 1 up to 
   `house-limit`. If `elf-limit` is a non-zero value, elves will stop
   after delivering `elf-limit` presents."
  [house-limit elf-limit]
  (->> (for [elf   (range 1 house-limit)
             house (if (zero? elf-limit)
                     (range elf house-limit elf)
                     (take elf-limit (range elf house-limit elf)))]
         [house elf])
       (group-by first)
       (u/fmap #(reduce + (map second %)))))

(defn house-with-n-presents
  "Finds the first (lowest numbered) house that received at least 
   n presents delivered to it."
  ([n house-limit]
   (house-with-n-presents n house-limit 0))
  ([n house-limit elf-limit]
   (->> (presents house-limit elf-limit)
        (filter #(>= (val %) n))
        keys
        (apply min))))

;; Puzzle solutions
(defn part1
  "The lowest house number that gets at least as many presents as input"
  [input]
  (house-with-n-presents (quot input 10) house-limit))

(defn part2
  "The lowest house number that gets at least as many presents as input
   using the part2 present distribution logic"
  [input]
  (house-with-n-presents (quot input 11) house-limit elf-limit))