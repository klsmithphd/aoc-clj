(ns aoc-clj.2018.day10
  "Solution to https://adventofcode.com/2018/day/10"
  (:require [aoc-clj.utils.grid :as grid]
            [aoc-clj.utils.grid.mapgrid :as mg]
            [aoc-clj.utils.vectors :as v]))

;; Constants
(def time-guess-span 100)

;; Input parsing
(defn parse-line
  [line]
  (->> (re-seq #"-?\d+" line)
       (map read-string)
       (partition 2)))

(defn parse
  [input]
  (map parse-line input))

;; Puzzle logic
(defn bounds
  "Returns the min/max x-bounds and min/max y-bounds for the provided positions"
  [positions]
  (let [xs (map first positions)
        ys (map second positions)]
    [[(apply min xs) (apply max xs)]
     [(apply min ys) (apply max ys)]]))

(defn area
  "Given the bounds, computes the area "
  [[[mnx mxx] [mny mxy]]]
  (* (- mxx mnx) (- mxy mny)))

(defn pos-at-t
  "Returns the position of a light at time t, given its initial position and
   velocity"
  [t [pos vel]]
  (v/vec-add pos (v/scalar-mult vel t)))

(defn positions-at-t
  "Returns the positions of all the lights at time t"
  [lights t]
  (map #(pos-at-t t %) lights))

(defn time-to-axis
  "Computes the minimum time required for the given light to cross the y-axis"
  [[[x _] [vx _]]]
  (quot (abs x) vx))

(defn time-guess
  "Make a guess as to when to start checking if the lights are
   in a pattern based on when all the negative-x-position lights
   have crossed the y axis"
  [lights]
  (->> (filter #(neg? (ffirst %)) lights)
       (map time-to-axis)
       (apply max)))

(defn condensed-time
  "Computes the time at which the lights will be most condensed
   (i.e. occupy the smallest area)"
  [lights]
  (let [start (time-guess lights)]
    (->> (range start (+ start time-guess-span))
         (apply min-key #(area (bounds (positions-at-t lights %)))))))

(defn light-message
  "Returns a pixelfont (ASCII block letters) representation of the message"
  [lights]
  (let [time                  (condensed-time lights)
        positions             (positions-at-t lights time)
        [[mnx mxx] [mny mxy]] (bounds positions)
        shifted               (map #(v/vec-add [(- mnx) (- mny)] %) positions)
        grid                  (mg/->MapGrid2D
                               (inc (- mxx mnx)) (inc (- mxy mny))
                               (zipmap shifted (repeat :on)))]
    (grid/Grid2D->ascii {\# :on \  nil} grid :down true)))

;; Puzzle solutions
(defn part1
  "What message will eventually appear in the sky?"
  [input]
  (light-message input))

(defn part2
  "How many seconds would they have needed to wait for that message to appear?"
  [input]
  (condensed-time input))