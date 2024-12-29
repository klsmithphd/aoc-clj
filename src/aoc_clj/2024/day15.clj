(ns aoc-clj.2024.day15
  "Solution to https://adventofcode.com/2024/day/15"
  (:require [clojure.string :as str]
            [aoc-clj.utils.grid :as grid]
            [aoc-clj.utils.grid.mapgrid :as mg]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.vectors :as v]))

;; Input parsing
(def grid-charmap
  {\# :wall
   \O :box
   \@ :robot
   \. :space})

(def move-charmap
  {\< :w
   \> :e
   \^ :n
   \v :s})

(defn parse-moves
  [moves-strs]
  (->> (str/join moves-strs)
       (map move-charmap)))

(defn parse
  [input]
  (let [[grid-str moves-str] (u/split-at-blankline input)
        grid (mg/ascii->MapGrid2D grid-charmap grid-str :down true)]
    {:robot (first (grid/find-nodes :robot grid))
     :walls (set (grid/find-nodes :wall grid))
     :boxes (set (grid/find-nodes :box grid))
     :moves (parse-moves moves-str)}))

;; Puzzle logic
(defn robot-move
  "Updates the state by moving the robot to its new position"
  [state to-pos]
  (assoc state :robot to-pos))

(defn boxes-to-be-moved
  "Returns a seq of all the boxes that are going to each be pushed if
   the robot attempts to move to `to-pos`"
  [part boxes delta to-pos]
  (->> (iterate #(v/vec-add delta %) to-pos)
       (take-while boxes)))

(defn boxes-free-to-move?
  "Returns the state of the space just beyond the boxes to be moved"
  [part walls delta boxes-to-move]
  (let [not-a-wall (complement walls)]
    (->> (last boxes-to-move)
         (v/vec-add delta)
         not-a-wall)))

(defn box-move
  "Updates the state by moving the robot and any boxes in front of it
   if they can be moved (meaning there's a free space at the end
   of the line of boxes). If the boxes can't be moved, just returns
   the state as-is"
  [part {:keys [boxes walls] :as state} delta to-pos]
  (let [box-seq     (boxes-to-be-moved part boxes delta to-pos)]
    (if (boxes-free-to-move? part walls delta box-seq)
      (let [new-boxes  (map #(v/vec-add delta %) box-seq)]
        (-> (robot-move state to-pos)
            (update :boxes #(reduce disj % box-seq))
            (update :boxes #(reduce conj % new-boxes))))
      state)))

(defn move
  "Attempts to perform the move of the robot.
   If the move attempts to go into a wall, nothing changes.
   If the move is to a free space, the robot moves to that space.
   If the move encounters a box, then we check to see if what could be
   a line of 1-to-N boxes can be moved."
  [part {:keys [walls boxes robot] :as state} dir]
  (let [delta  (case dir
                 :n [0 -1]
                 :s [0 1]
                 :e [1 0]
                 :w [-1 0])
        to-pos (v/vec-add robot delta)]
    (cond
      (walls to-pos) state
      (boxes to-pos) (box-move part state delta to-pos)
      :else          (robot-move state to-pos))))

(defn all-moves
  "Returns the state after all the moves in the move sequence have
   been attempted"
  [part input]
  (reduce (partial move part) (u/without-keys input [:moves]) (:moves input)))

(defn box-gps
  "Returns the GPS coordinate of a box, which is 100 times its distance
   from the top edge of the map plus its distance from the left edge"
  [[x y]]
  (+ x (* y 100)))

(defn box-gps-sum
  "Returns the sum of the GPS coordinates of all the boxes"
  [{:keys [boxes]}]
  (->> boxes
       (map box-gps)
       (reduce +)))

;; Puzzle solutions
(defn part1
  "After the robot is finished moving, what is the sum of all boxes'
   GPS coordinates?"
  [input]
  (->> input
       (all-moves :part1)
       box-gps-sum))

;; In part 2, the boxes are 2 units wide in the horizontal direction.
;; This means that it's possible that pushing on one box up or down can
;; cause more than one adjacent box to be pushed. 
;; 
;; The logic I have generally works, but the concept of how to identify
;; the box chain needs to change.
;;
;; Needs:
;; * a new representation that knows that boxes are either 1 unit or 2 units wide
;; * a function to expand the input space by 2x in the horizontal dir
;; * New logic for identifying the chain of boxes (and any walls they touch)

;; One possible representation -- leave the grid as is, but have the robot
;; only move 1/2 a step in the :w :e directions. Then, when checking to 
;; see if there are any touching boxes, I need to look at boxes whose
;; coordinates are 1/2 a unit to the left or equal to the x pos of
;; the previous box (or robot)
;; 

