(ns aoc-clj.2022.day14
  "Solution to https://adventofcode.com/2022/day/14"
  (:require [clojure.string :as str]))

;;;; Input parsing

(defn parse-line
  [line]
  (mapv #(read-string (str "[" % "]")) (str/split line #" \-\> ")))

(defn points
  "Given a start point and end point for a line segment, return all
   the points along the line segment"
  [[[x1 y1] [x2 y2]]]
  (let [dir-y (if (<= y1 y2) +1 -1)
        dir-x (if (<= x1 x2) +1 -1)]
    (for [y (range y1 (+ y2 dir-y) dir-y)
          x (range x1 (+ x2 dir-x) dir-x)]
      [x y])))

(defn trace-lines
  [line]
  (concat (mapcat points (partition 2 1 line))))

(defn rocks
  [input]
  (set (mapcat trace-lines input)))

(defn parse
  [input]
  (rocks (map parse-line input)))

;;;; Puzzle logic

(defn lowest
  [grid]
  (apply max (map second grid)))

(defn init-state
  [grid]
  {:grid grid
   :lowpoint (lowest grid)
   :last-added nil})

(defn move
  "A unit of sand always falls down one step if possible. 
   If the tile immediately below is blocked (by rock or sand), 
   the unit of sand attempts to instead move diagonally one step down and 
   to the left. If that tile is blocked, the unit of sand attempts to instead 
   move diagonally one step down and to the right. Sand keeps moving as long
   as it is able to do so, at each step trying to move down, then down-left, 
   then down-right. If all three possible destinations are blocked, 
   the unit of sand comes to rest and no longer moves, 
   at which point the next unit of sand is created back at the source."
  [grid [x y]]
  (let [moves [;; straight down
               [x (inc y)]
               ;; down to the left
               [(dec x) (inc y)]
               ;; down to the right
               [(inc x) (inc y)]]]
    (first (remove grid moves))))

(defn deposit-sand-grain
  "Deposit one new grain of sand, given the current layout of rock and
   sand given by `grid`. Returns a vector of the new grid and the position
   of the newly deposited sand grain."
  [{:keys [lowpoint grid] :as state}]
  (loop [pos [500 0]]
    (if (or (nil? (move grid pos))
            (>= (second pos) lowpoint))
      (-> state
          (update :grid conj pos)
          (assoc  :last-added pos))
      (recur (move grid pos)))))

(defn flow-check
  [{:keys [lowpoint last-added]}]
  (or (nil? last-added)
      (< (second last-added) lowpoint)))

(defn not-blocked?
  [{:keys [last-added]}]
  (or (nil? last-added)
      (not= [500 0] last-added)))

(defn sand-until-condition
  "Continuously deposit new sand grains into the grid as long as the 
   condition indicated by `pred` remains true. Returns the number of
   sand grains deposited."
  [pred grid]
  (->> (iterate deposit-sand-grain (init-state grid))
       (take-while pred)
       count))

(defn sand-until-continuous-flow
  "Let sand deposit until it will start to flow continuously (i.e. flow
   beyond the lowest rock in the grid). Returns the number of sand grains
   deposited."
  [grid]
  (dec (sand-until-condition flow-check grid)))

(defn add-floor
  "Update the map with a large floor surface of rock, two units below
   the lowest known rock point"
  [grid]
  (let [floor (+ 2 (lowest grid))
        width (* 2 floor)]
    (into grid (for [x (range (- 500 width) (+ 500 width))]
                 [x floor]))))

(defn sand-until-blocked
  "Let sand deposit until it reaches the source and blocks it, based
   on the knowledge that there's a floor. Returns the count of sand grains
   deposited."
  [grid]
  (sand-until-condition not-blocked? (add-floor grid)))

;;;; Puzzle solutions

(defn part1
  "Using your scan, simulate the falling sand. How many units of sand come 
   to rest before sand starts flowing into the abyss below?"
  [input]
  (sand-until-continuous-flow input))

(defn part2
  "Using your scan, simulate the falling sand until the source of the sand 
   becomes blocked. How many units of sand come to rest?"
  [input]
  (sand-until-blocked input))

