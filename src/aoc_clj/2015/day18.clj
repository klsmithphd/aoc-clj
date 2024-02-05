(ns aoc-clj.2015.day18
  "Solution to https://adventofcode.com/2015/day/18"
  (:require [aoc-clj.utils.grid.mapgrid :as mapgrid]))

;; Constants
(def soln-steps 100)

;; Input parsing
(def char-map {\. :off \# :on})
(defn parse
  [input]
  (mapgrid/ascii->MapGrid2D char-map input))

;; Puzzle logic
(defn adj-coords
  [[x y]]
  (filter #(not= [x y] %) (for [y (range (dec y) (+ y 2))
                                x (range (dec x) (+ x 2))]
                            [x y])))

(defn neighbors
  [grid pos]
  (let [locs (adj-coords pos)]
    (zipmap locs (map grid locs))))

(defn corner?
  "Is the given position one of the four corner cells?"
  [{:keys [width height]} pos]
  (or (= pos [0 0])
      (= pos [0 (dec height)])
      (= pos [(dec width) 0])
      (= pos [(dec width) (dec height)])))

(defn corners-on
  "Force set the four corners to an on position"
  [{:keys [width height grid] :as input}]
  (assoc input :grid (assoc grid
                            [0 0] :on
                            [(dec width) 0] :on
                            [0 (dec height)] :on
                            [(dec width) (dec height)] :on)))

(defn update-lights
  "Update the light value in the grid at the given `position`, based
   on current neighbor values"
  ([input pos]
   (update-lights false input pos))
  ([corners-on? {:keys [grid] :as input} pos]
   (if (and corners-on? (corner? input pos))
     :on
     (let [state (grid pos)
           on-neighs (count (filter #(= :on (val %)) (neighbors grid pos)))]
       (case state
         :on  (if (or (= 2 on-neighs) (= 3 on-neighs))
                :on
                :off)
         :off (if (= 3 on-neighs)
                :on
                :off))))))

(defn step
  "Update all lights in the grid based on their neighbors."
  ([input]
   (step false input))
  ([corners-on? {:keys [grid] :as input}]
   (assoc input :grid
          (zipmap (keys grid)
                  (map (partial update-lights corners-on? input) (keys grid))))))

(defn lights-on-at-step-n
  "Evolve the light grid to the nth step"
  ([n input]
   (lights-on-at-step-n false n input))
  ([corners-on? n input]
   (->>  (iterate (partial step corners-on?) input)
         (drop n)
         first
         :grid
         (filter #(= :on (val %)))
         count)))

;; Puzzle solutions
(defn part1
  "How many lights are on after 100 steps"
  [input]
  (lights-on-at-step-n soln-steps input))

(defn part2
  "How many lights are on after 100 steps when the corners are kept always on"
  [input]
  (lights-on-at-step-n true soln-steps (corners-on input)))