(ns aoc-clj.2015.day13
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :as combo]
            [aoc-clj.utils.core :as u]))

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

(def day13-input (parse (u/puzzle-input "inputs/2015/day13-input.txt")))

(defn adjacents
  [order]
  (map vector (u/rotate 1 order) (u/rotate -1 order)))

(defn happiness
  [table guest neighbors]
  (+  (get-in table [guest (first  neighbors)])
      (get-in table [guest (second neighbors)])))

(defn happiness-total
  [table order]
  (let [neighbors (adjacents order)]
    (reduce + (map (partial happiness table) order neighbors))))

(defn max-happiness
  [table]
  (let [orders (combo/permutations (keys table))]
    (apply max (map (partial  happiness-total table) orders))))

(defn add-me
  [table]
  (let [new-table (u/fmap #(assoc % "Me" 0) table)]
    (assoc new-table "Me" (zipmap (keys new-table) (repeat 0)))))

(defn day13-part1-soln
  []
  (max-happiness day13-input))

(defn day13-part2-soln
  []
  (max-happiness (add-me day13-input)))