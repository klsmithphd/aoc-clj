(ns aoc-clj.2019.day01
  (:require [aoc-clj.utils.core :as u]))

(def day01-input
  (map read-string (u/puzzle-input "inputs/2019/day01-input.txt")))

(defn fuel
  "[T]o find the fuel required for a module, take its mass, 
   divide by three, round down, and subtract 2."
  [mass]
  (let [f (-> mass
              (/ 3)
              Math/floor
              (- 2)
              int)]
    (if (neg? f) 0 f)))

(defn total-fuel
  "So, for each module mass, calculate its fuel and add it to the total. 
   Then, treat the fuel amount you just calculated as the input mass 
   and repeat the process, continuing until a fuel requirement is zero 
   or negative."
  [mass]
  (->> mass
       (iterate fuel)
       (take-while pos?)
       rest ;; drop the original mass itself
       (reduce +)))

(defn day01-part1-soln
  []
  (reduce + (map fuel day01-input)))

(defn day01-part2-soln
  []
  (reduce + (map total-fuel day01-input)))