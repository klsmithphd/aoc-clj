(ns aoc-clj.2018.day17
  "Solution to https://adventofcode.com/2018/day/17"
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.vectors :as vec]))

;; Constants
(def spring [500 0])

;; Input parsing
(defn parse-line
  [line]
  (let [nums (map read-string (re-seq #"\d+" line))
        dir  (case (subs line 0 1)
               "x" :v
               "y" :h)]
    (conj nums dir)))

(defn parse
  [input]
  (map parse-line input))

;; Puzzle logic
(defn clay-line
  "Expands the definition of a line of clay into its full coordinates"
  [[dir fixed start end]]
  (for [changing (range start (inc end))]
    (case dir
      :v [fixed changing]
      :h [changing fixed])))

(defn clay-map
  "Expands the clay vein definitions into a map with the vertical coordinate
   as the key and the value is the horizontal coordinates of clay"
  [veins]
  (->> veins
       (mapcat clay-line)
       set
       (group-by second)
       (u/fmap #(sort (map first %)))))

(defn all-clay
  "Returns a set of all the coordinates of the clay cells from the vertical
   clay map"
  [clay-map]
  (set (mapcat (fn [[y xs]] (map #(vector % y) xs)) clay-map)))

(def lowest
  "Returns the lowest (maximum y) value of all the clay"
  (memoize
   (fn [clay]
     (apply max (keys clay)))))

;; The defining characteristic of whether water *can* settle at a given
;; position is that there's a clay cell to the left and a clay cell to
;; the right on the current line, with every value on the next line
;; down already occupied.

(defn water-can-settle
  [])

(defn add-water
  [occupied]
  (let [y (->> spring
               (iterate #(vec/vec-add [0 1] %))
               (drop-while (complement occupied))
               first)]
    y))