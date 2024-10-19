(ns aoc-clj.2017.day19
  "Solution to https://adventofcode.com/2017/day/19"
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.grid :as grid :refer [value]]
            [aoc-clj.utils.grid.vecgrid :as vg]))

;; Input parsing
(defn charmap
  [ch]
  (case ch
    \  :space
    \+ :corner
    \| :vert
    \- :horiz
    (str ch)))

(defn parse
  [input]
  (vg/ascii->VecGrid2D charmap input :down true))

;; Puzzle logic
(defn start
  "Returns the starting position, the first vertical pipe at the top of
   the diagram"
  [grid]
  [(u/index-of (u/equals? :vert) (first (:v grid))) 0])

(def perpendicular
  "For a given heading, returns the perpendicular directions"
  {:n #{:e :w}
   :s #{:e :w}
   :e #{:n :s}
   :w #{:n :s}})

(defn allowed-turn
  "When at a corner, returns true if a neighboring cell is valid as
   the next pos/heading to go in"
  [heading nbr-val]
  (or (string? nbr-val)
      (case heading
        :n (= :horiz nbr-val)
        :s (= :horiz nbr-val)
        :e (= :vert nbr-val)
        :w (= :vert nbr-val))))

(defn next-cell
  "Returns the position and heading of the next step along the path
   or nil if there's no valid next cell"
  [grid {:keys [pos heading]}]
  (let [val (value grid pos)
        nxt-pos (if (= :corner val)
                  (->> (grid/neighbor-data grid pos)
                       (filter #((perpendicular heading) (:heading %)))
                       (filter #(allowed-turn heading (:val %)))
                       first)
                  (->> (grid/neighbor-data grid pos)
                       (filter #(= heading (:heading %)))
                       (remove #(= :space (:val %)))
                       first))]
    (when nxt-pos
      (select-keys nxt-pos [:pos :heading]))))

(defn path
  "Returns a seq of all the positions along the path through the network"
  [grid]
  (->> {:pos (start grid) :heading :n}
       (iterate #(next-cell grid %))
       (take-while some?)
       (map :pos)))

(defn letters-along-path
  "Returns the letters as they're observed along the path"
  [grid]
  (->> (path grid)
       (map #(value grid %))
       (filter string?)
       (apply str)))

(defn step-count
  "Returns the total number of steps to get through the network"
  [grid]
  (->> (path grid)
       count))

;; Puzzle solutions
(defn part1
  "What letters will it see (in the order it would see them) 
   if it follows the path? "
  [input]
  (letters-along-path input))

(defn part2
  "How many steps does the packet need to go?"
  [input]
  (step-count input))