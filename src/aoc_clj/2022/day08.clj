(ns aoc-clj.2022.day08
  "Solution to https://adventofcode.com/2022/day/8"
  (:require [aoc-clj.utils.core :as u]))

;;;; Input parsing 
(defn parse-row
  [row]
  (mapv (comp read-string str) row))

(defn parse
  [input]
  (mapv parse-row input))

;;;; Puzzle logic

(defn visible?
  "Returns a vec of 0s or 1s, where 0 indicates that the tree is not visible
   **from the left edge** and 1 indicates that the tree is visible.
   
   A tree is visible if all of the other trees between it and an edge of 
   the grid are shorter than it."
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
  "Returns a vec of 0s or 1s, where 0 indicates that the tree is not visible
   **from the left or right edge** and 1 indicates that the tree is visible."
  [row]
  (mapv bit-or (visible? row) (reverse (visible? (reverse row)))))


(defn grid-visibility
  "Returns a vec of vecs of 0s or 1s, where 0 indicates that the tree
   is not visible **from the left or right edges** and 1 indicates that 
   the tree is visible.
   
   The first row and the last row (being at the outer edge) are always
   visible (i.e. are all 1s)"
  [grid]
  (let [width (count (first grid))]
    (vec
     (concat [(vec (repeat width 1))]
             (mapv row-visibility (butlast (drop 1 grid)))
             [(vec (repeat width 1))]))))

(defn transpose-fn
  "Applies a function on the transpose of the grid and then returns the
   transpose of that result, i.e. (f A^T)^T"
  [f grid]
  (u/transpose (f (u/transpose grid))))

(defn visible-trees
  "Returns a count of all of the trees that are visible from the outside
   of the grid"
  [input]
  (reduce + (map bit-or
                 (flatten (grid-visibility input))
                 (flatten (transpose-fn grid-visibility input)))))

(defn view-distance
  "For a given `row`, consider the element at index `i`. Compute the product
   of the distance to the left before hitting a tree of equal or greater
   height and the equivalent distance to the right"
  [row i]
  (let [[l [x & r]] (split-at i row)]
    [x l r]
    (* (min (count l) (inc (count (take-while #(> x %) (reverse l)))))
       (min (count r) (inc (count (take-while #(> x %) r)))))))

(defn row-view-distance
  "Computes the horizontal view distance products for an entire row"
  [row]
  (map #(view-distance row %) (range (count row))))

(defn grid-view-distance
  "Computes the horizontal view distance product for an entire grid"
  [grid]
  (map row-view-distance grid))

(defn scenic-score
  "To measure the viewing distance from a given tree, look up, down, left, and 
   right from that tree; stop if you reach an edge or at the first tree that is 
   the same height or taller than the tree under consideration. (If a tree is 
   right on the edge, at least one of its viewing distances will be zero.)
   
   A tree's scenic score is found by multiplying together its viewing distance 
   in each of the four directions."
  [grid]
  (map *
       (flatten (grid-view-distance grid))
       (flatten (transpose-fn grid-view-distance grid))))

(defn max-scenic-score
  "Find the maximum scenic score of any tree in the grid"
  [input]
  (apply max (scenic-score input)))

;;;; Puzzle solutions

(defn part1
  "Consider your map; how many trees are visible from outside the grid?"
  [input]
  (visible-trees input))

(defn part2
  "Consider each tree on your map. What is the highest scenic score 
   possible for any tree?"
  [input]
  (max-scenic-score input))