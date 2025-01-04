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
(defn double-x
  "Return a coordinate vector with the x-coordinated doubled, leaving y same"
  [[x y]]
  [(* 2 x) y])

(defn spread-x
  "Given a coordinate vector, return a collection of the coordinate and
   the location one unit to the right"
  [[x y]]
  [[x y] [(inc x) y]])

(defn widen-input
  "For part2, double the width of the warehouse space"
  [{:keys [robot boxes walls moves]}]
  {:robot (double-x robot)
   :walls (set (mapcat (comp spread-x double-x) walls))
   :boxes (set (map double-x boxes))
   :moves moves})

(defn robot-move
  "Updates the state by moving the robot to its new position"
  [state to-pos]
  (assoc state :robot to-pos))

(defn box-check-deltas
  "For `part=:part1`, just returns the delta as-is
   For `part=:part2`, it *may* modify the deltas based on the `dir` to account
   for the double-wide boxes."
  [part first? dir delta]
  (case part
    :part1 [delta]
    :part2 (case dir
             ;; In the east direction, we only need to check the immediately
             ;; adjacent cell for the first go (from robot).
             ;; For each subsequence box, we check two units to the right.
             :e (if first?
                  [delta]
                  [(v/vec-add delta [1 0])])
             ;; In the west direction, we need to check to see if there's
             ;; a box two units to the left.
             :w [(v/vec-add delta [-1 0])]
             ;; For north and south, we need to check to see if there's
             ;; a box at delta, but also one unit to the left of delta on
             ;; the first go. For each subsequent go, we check up, up-left,
             ;; and up-right.
             (:n :s) (if first?
                       [(v/vec-add delta [-1 0]) delta]
                       [(v/vec-add delta [-1 0]) delta (v/vec-add delta [1 0])]))))

(defn adjacent-boxes
  "Returns the set of boxes that are adjacent to the current position
   based on the direction of intended movement."
  [part first? boxes pos dir delta]
  (let [deltas (box-check-deltas part first? dir delta)]
    (->> (map #(v/vec-add pos %) deltas)
         (filter boxes)
         set)))

(defn next-boxes
  "For any given existing box `positions`, returns a collection of the
   adjacent boxes that will also be moved if the current boxes are moved."
  [part first? boxes dir delta positions]
  (mapcat #(adjacent-boxes part first? boxes % dir delta) positions))

(defn box-chain
  "Returns a collection of all of the touching boxes that will potentially
   be moved"
  [part boxes dir delta start-pos]
  (let [first-layer (next-boxes part true boxes dir delta [start-pos])]
    (->> (iterate (partial next-boxes part false boxes dir delta) first-layer)
         (take-while seq)
         (mapcat identity))))

(defn boxes-free-to-move?
  "Returns the state of the space just beyond the boxes to be moved"
  [part walls new-box-positions]
  (let [not-a-wall (complement walls)]
    (every? not-a-wall (case part
                         :part1 new-box-positions
                         :part2 (mapcat spread-x new-box-positions)))))

(defn box-move
  "Updates the state by moving the robot and any boxes in front of it
   if they can be moved (meaning there's a free space at the end
   of the line of boxes). If the boxes can't be moved, just returns
   the state as-is"
  [part {:keys [walls] :as state} delta box-seq to-pos]
  (let [new-boxes (map #(v/vec-add delta %) box-seq)]
    (if (boxes-free-to-move? part walls new-boxes)
      (-> (robot-move state to-pos)
          (update :boxes #(reduce disj % box-seq))
          (update :boxes #(reduce conj % new-boxes)))
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
        to-pos    (v/vec-add robot delta)
        adj-boxes (box-chain part boxes dir delta robot)]
    (cond
      (walls to-pos)  state
      (seq adj-boxes) (box-move part state delta adj-boxes to-pos)
      :else           (robot-move state to-pos))))

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

(defn part2
  "Predict the motion of the robot and boxes in this new, scaled-up warehouse.
   What is the sum of all boxes' final GPS coordinates?"
  [input]
  (->> (widen-input input)
       (all-moves :part2)
       box-gps-sum))