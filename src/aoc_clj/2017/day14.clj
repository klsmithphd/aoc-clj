(ns aoc-clj.2017.day14
  "Solution to https://adventofcode.com/2017/day/14"
  (:require [aoc-clj.2017.day10 :as d10]
            [aoc-clj.utils.binary :as b]
            [aoc-clj.utils.grid :as grid :refer [pos-seq value neighbors-4]]
            [aoc-clj.utils.grid.vecgrid :as vg]))

;; Input parsing
(def parse first)

;; Puzzle logic
(defn hexstr->bitstr
  "Convert a string of hex characters into a string of bits, with each
   hex character representing 4 bits."
  [hex-str]
  (->> (map b/hexstr->int hex-str)
       (mapcat #(b/int->bitstr 4 %))
       (apply str)))

(defn grid-squares
  "Create a representation of the 128x128 grid's occupied state, 
   represented as 128 bit strings"
  [key-str]
  (->> (map #(str key-str "-" %) (range 128))
       (map d10/knot-hash)
       (map hexstr->bitstr)))

(defn used-squares
  "How many of the grid's cells are used (i.e. have a value of one)"
  [key-str]
  (->> (grid-squares key-str)
       (apply str)
       (filter #{\1})
       count))

(defn ones
  "Returns a seq of all the cell positions that are used"
  [grid]
  (->> (pos-seq grid)
       (filter #(= 1 (value grid %)))))

(defn adjacent-ones
  "For a given position, return the neighbors that are also used"
  [grid pos]
  (->> (neighbors-4 grid pos)
       (filter #(= 1 (val %)))
       (map key)))

(defn region
  "Given the grid and a used position within it, return all of the
   connected values (that together form a region)"
  [grid pos]
  (loop [group #{pos} front (adjacent-ones grid pos)]
    (if-not (seq front)
      group
      (recur (into group front)
             (remove group (mapcat #(adjacent-ones grid %) front))))))

(defn region-count
  "Returns the count of all connected regions"
  [key-str]
  (let [grid (vg/ascii->VecGrid2D {\0 0 \1 1} (grid-squares key-str))]
    (loop [regions #{} yet-ungrouped (ones grid)]
      (if-not (seq yet-ungrouped)
        (count regions)
        (let [new-region (region grid (first yet-ungrouped))]
          (recur (conj regions new-region)
                 (remove new-region yet-ungrouped)))))))

;; Puzzle solutions
(defn part1
  "How many squares are used?"
  [input]
  (used-squares input))

(defn part2
  "How many regions are present?"
  [input]
  (region-count input))