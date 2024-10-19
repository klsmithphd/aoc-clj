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

(def d19-s00-raw
  ["     |          "
   "     |  +--+    "
   "     A  |  C    "
   " F---|----E|--+ "
   "     |  |  |  D "
   "     +B-+  +--+ "])

(def d19-s00 (parse d19-s00-raw))

;; Puzzle logic
(defn start
  [grid]
  [(u/index-of (u/equals? :vert) (first (:v grid))) 0])

(def perpendicular
  {:n #{:e :w}
   :s #{:e :w}
   :e #{:n :s}
   :w #{:n :s}})

(defn allowed-turn
  [heading nbr-val]
  (or (string? nbr-val)
      (case heading
        :n (= :horiz nbr-val)
        :s (= :horiz nbr-val)
        :e (= :vert nbr-val)
        :w (= :vert nbr-val))))

(defn next-cell
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
  [grid]
  (->> {:pos (start grid) :heading :n}
       (iterate #(next-cell grid %))
       (take-while some?)
       (map :pos)))

(defn letters-along-path
  [grid]
  (->> (path grid)
       (map #(value grid %))
       (filter string?)
       (apply str)))

(defn step-count
  [grid]
  (->> (path grid)
       count))

;; Puzzle solutions
(defn part1
  [input]
  (letters-along-path input))

(defn part2
  [input]
  (step-count input))