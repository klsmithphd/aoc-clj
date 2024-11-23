(ns aoc-clj.2018.day18
  "Solution to https://adventofcode.com/2018/day/18"
  (:require [aoc-clj.utils.grid :as grid]
            [aoc-clj.utils.grid.vecgrid :as vg]
            [aoc-clj.utils.core :as u]))

;; Constants
(def part1-time 10)
(def part2-time 1000000000)

;; Input parsing
(def charmap
  {\. :o ;; open
   \# :l ;; lumberyard
   \| :t ;; tree
   })

(defn parse
  [input]
  (vg/ascii->VecGrid2D charmap input :down true))

;; Puzzle logic
(defn cell-update
  "Given the current scan state, and a position in the grid `pos`,
   return the new value for that position using the logic provideds"
  [scan pos]
  (let [neighbor-cnts (->> (grid/neighbor-data scan pos :diagonals true)
                           (map :val)
                           frequencies)]
    ;; (print (grid/value scan pos))
    (case (grid/value scan pos)
      ;; An open acre becomes trees if 3 or more adjacent acres contain trees
      ;; Else, nothing happens
      :o (if (>= (get neighbor-cnts :t 0) 3)
           :t
           :o)
      ;; A tree acre becomes a lumberyard if 3 or more adjacent acres were lumberyards
      ;; Else, nothing happens
      :t (if (>= (get neighbor-cnts :l 0) 3)
           :l
           :t)
      ;; A lumberyard remains a lumberyard if it was next to at least one
      ;; lumberyard and at least one acre containing trees.
      ;; Else, it becomes open.
      :l (if (and (pos? (get neighbor-cnts :l 0))
                  (pos? (get neighbor-cnts :t 0)))
           :l
           :o))))

(defn step
  "Returns the new scan state, updated by one increment in time"
  [scan]
  (let [width (grid/width scan)]
    (->> (grid/pos-seq scan)
         (map #(cell-update scan %))
         (partition width)
         (mapv vec)
         vg/->VecGrid2D)))

(defn state-at-t
  "Returns the updated state of the scan at time `t`"
  [scan t]
  (->> scan
       (iterate step)
       (drop t)
       first))

(defn state-at-large-t
  "Returns the updated state of the scan at a very large time `t`, 
   presuming that there must be some kind of cycle of repeated states."
  [scan t]
  (let [[offset repeat] (->> scan
                             (iterate step)
                             u/first-duplicates)
        cycle-size      (- repeat offset)
        extra           (mod (- t offset) cycle-size)]
    (state-at-t scan (+ offset extra))))

(defn resource-value
  "The resource value is the number of tree acres times the number of 
   lumberyard acres"
  [scan]
  (let [freqs (->> scan
                   grid/val-seq
                   frequencies)]
    (* (get freqs :t)
       (get freqs :l))))

;; Puzzle solutions
(defn part1
  "What will the total resource value of the lumber collection area be
   after 10 minutes?"
  [input]
  (->> (state-at-t input part1-time)
       resource-value))

(defn part2
  "What will the total resource value of the lumber collection area be 
   after 1000000000 minutes?"
  [input]
  (->> (state-at-large-t input part2-time)
       resource-value))