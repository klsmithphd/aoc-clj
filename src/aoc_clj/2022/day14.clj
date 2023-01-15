(ns aoc-clj.2022.day14
  "Solution to https://adventofcode.com/2022/day/14"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

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
  (zipmap
   (distinct (mapcat trace-lines input))
   (repeat :rock)))

(defn parse
  [input]
  (rocks (map parse-line input)))

(def day14-input (u/parse-puzzle-input parse 2022 14))

;;;; Puzzle logic

(defn lowest
  [grid]
  (apply max (map second (keys grid))))

(defn open?
  [grid pos]
  ((complement #{:rock :sand}) (get grid pos :air)))

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
    (first (filter #(open? grid %) moves))))

(defn deposit-sand-grain
  "Deposit one new grain of sand, given the current layout of rock and
   sand given by `grid`. Returns a vector of the new grid and the position
   of the newly deposited sand grain."
  [[grid _]]
  (let [lowpoint (lowest grid)]
    (loop [pos [500 0]]
      (if (or (nil? (move grid pos))
              (>= (second pos) lowpoint))
        [(assoc grid pos :sand) pos]
        (recur (move grid pos))))))

(defn flow-check
  [lowpoint x]
  (or (nil? (second x))
      (< (second (second x)) lowpoint)))

(defn not-blocked?
  [x]
  (or (nil? (second x))
      (not= [500 0] (second x))))

(defn sand-until-condition
  "Continuously deposit new sand grains into the grid as long as the 
   condition indicated by `pred` remains true. Returns the number of
   sand grains deposited."
  [pred grid]
  (->> (iterate deposit-sand-grain [grid nil])
       (take-while pred)
       count))

(defn sand-until-continuous-flow
  "Let sand deposit until it will start to flow continuously (i.e. flow
   beyond the lowest rock in the grid). Returns the number of sand grains
   deposited."
  [grid]
  (let [not-done? (partial flow-check (lowest grid))]
    (dec (sand-until-condition not-done? grid))))

(defn add-floor
  "Update the map with a large floor surface of rock, two units below
   the lowest known rock point"
  [grid]
  (let [floor (+ 2 (lowest grid))
        width (* 2 floor)]
    (into grid (for [x (range (- 500 width) (+ 500 width))]
                 [[x floor] :rock]))))

(defn sand-until-blocked
  "Let sand deposit until it reaches the source and blocks it, based
   on the knowledge that there's a floor. Returns the count of sand grains
   deposited."
  [grid]
  (sand-until-condition not-blocked? (add-floor grid)))

;;;; Puzzle solutions

(defn day14-part1-soln
  "Using your scan, simulate the falling sand. How many units of sand come 
   to rest before sand starts flowing into the abyss below?"
  []
  (sand-until-continuous-flow day14-input))

(defn day14-part2-soln
  "Using your scan, simulate the falling sand until the source of the sand 
   becomes blocked. How many units of sand come to rest?"
  []
  (sand-until-blocked day14-input))

