(ns aoc-clj.2022.day08
  "Solution to https://adventofcode.com/2022/day/8"
  (:require [aoc-clj.utils.core :as u]))

(defn parse-row
  [row]
  (mapv (comp read-string str) row))

(defn parse
  [input]
  (mapv parse-row input))

(def day08-input (parse (u/puzzle-input "2022/day08-input.txt")))

(defn visible?
  [s]
  (loop [nxt (rest s) mx (first s) acc [1]]
    (if (empty? nxt)
      acc
      (let [tree    (first nxt)
            taller? (> tree mx)]
        (recur (rest nxt)
               (if taller? tree mx)
               (conj acc (if taller? 1 0)))))))

(defn row-visibility
  [row]
  (mapv bit-or (visible? row) (reverse (visible? (reverse row)))))


(defn grid-visibility
  [grid]
  (let [width (count (first grid))]
    (vec
     (concat [(vec (repeat width 1))]
             (mapv row-visibility (butlast (drop 1 grid)))
             [(vec (repeat width 1))]))))

(defn visible-trees
  [input]
  (reduce + (map bit-or
                 (flatten (grid-visibility input))
                 (flatten (u/transpose (grid-visibility (u/transpose input)))))))

(defn view-distance
  [row i]
  (let [[l [x & r]] (split-at i row)]
    [x l r]
    (* (min (count l) (inc (count (take-while #(> x %) (reverse l)))))
       (min (count r) (inc (count (take-while #(> x %) r)))))))

(defn row-view-distance
  [row]
  (map #(view-distance row %) (range (count row))))

(defn grid-view-distance
  [grid]
  (map row-view-distance grid))

(defn scenic-score
  "A tree's scenic score is found by multiplying together its viewing distance 
   in each of the four directions."
  [grid]
  (map *
       (flatten (grid-view-distance grid))
       (flatten (u/transpose (grid-view-distance (u/transpose grid))))))

(defn max-scenic-score
  [input]
  (apply max (scenic-score input)))

(defn day08-part1-soln
  "Consider your map; how many trees are visible from outside the grid?"
  []
  (visible-trees day08-input))

(defn day08-part2-soln
  "Consider each tree on your map. What is the highest scenic score 
   possible for any tree?"
  []
  (max-scenic-score day08-input))