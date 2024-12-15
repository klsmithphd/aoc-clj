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
  (let [[grid-str moves-str] (u/split-at-blankline input)]
    {:grid (mg/ascii->MapGrid2D grid-charmap grid-str)
     :moves (parse-moves moves-str)}))

;; Puzzle logic
(defn robot-pos
  "Returns the location of the robot"
  [grid]
  (first (filter #(= :robot (grid/value grid %)) (grid/pos-seq grid))))

(defn box-positions
  "Returns the location of all the boxes"
  [grid]
  (filter #(= :box (grid/value grid %)) (grid/pos-seq grid)))

(defn robot-move
  "Updates the state by moving the robot from its current position
   to the new position and marking the previous position as a space"
  [state robot-pos to-pos]
  (-> state
      (assoc :robot-pos to-pos)
      (assoc-in [:grid :grid to-pos] :robot)
      (assoc-in [:grid :grid robot-pos] :space)))

(defn box-move
  "Updates the state by moving the robot and any boxes in front of it
   if they can be moved (meaning there's a free space at the end
   of the line of boxes). If the boxes can't be moved, just returns
   the state as-is"
  [{:keys [grid robot-pos] :as state} to-pos delta]
  (let [box-seq (->> (iterate #(v/vec-add delta %) to-pos)
                     (take-while #(= :box (grid/value grid %))))
        after-boxes (->> (v/vec-add delta (last box-seq))
                         (grid/value grid))]
    (case after-boxes
      :wall state
      :space
      (let [new-boxes (zipmap (map #(v/vec-add delta %) box-seq)
                              (repeat :box))]
        (-> (robot-move state robot-pos to-pos)
            (update-in [:grid :grid] into new-boxes))))))

(defn move
  "Attempts to perform the move of the robot.
   If the move attempts to go into a wall, nothing changes.
   If the move is to a free space, the robot moves to that space.
   If the move encounters a box, then we check to see if what could be
   a line of 1-to-N boxes can be moved."
  [{:keys [grid robot-pos] :as state} dir]
  (let [delta  (grid/cardinal-offsets dir)
        to-pos (v/vec-add robot-pos delta)]
    (case (grid/value grid to-pos)
      :wall state
      :space (robot-move state robot-pos to-pos)
      :box   (box-move state to-pos delta))))

(defn all-moves
  "Returns the state after all the moves in the move sequence have
   been attempted"
  [{:keys [grid moves]}]
  (let [start (robot-pos grid)]
    (reduce move {:grid grid :robot-pos start} moves)))

(defn box-gps
  "Returns the GPS coordinate of a box, which is 100 times its distance
   from the top edge of the map plus its distance from the left edge"
  [height [x y]]
  (+ x (* (- height y 1) 100)))

(defn box-gps-sum
  "Returns the sum of the GPS coordinates of all the boxes"
  [{:keys [grid]}]
  (let [height (grid/height grid)]
    (->> (box-positions grid)
         (map #(box-gps height %))
         (reduce +))))

;; Puzzle solutions
(defn part1
  "After the robot is finished moving, what is the sum of all boxes'
   GPS coordinates?"
  [input]
  (-> input all-moves box-gps-sum))