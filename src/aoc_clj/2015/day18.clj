(ns aoc-clj.2015.day18
  (:require [aoc-clj.utils.grid.mapgrid :as mapgrid]
            [aoc-clj.utils.core :as u]))

(def char-map {\. :off \# :on})
(def day18-input
  (mapgrid/ascii->MapGrid2D char-map (u/puzzle-input "inputs/2015/day18-input.txt")))

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
  [{:keys [width height]} pos]
  (or (= pos [0 0])
      (= pos [0 (dec height)])
      (= pos [(dec width) 0])
      (= pos [(dec width) (dec height)])))

(defn corners-on
  [{:keys [width height grid] :as input}]
  (assoc input :grid (assoc grid
                            [0 0] :on
                            [(dec width) 0] :on
                            [0 (dec height)] :on
                            [(dec width) (dec height)] :on)))

(defn update-lights
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
  ([input]
   (step false input))
  ([corners-on? {:keys [grid] :as input}]
   (assoc input :grid
          (zipmap (keys grid)
                  (map (partial update-lights corners-on? input) (keys grid))))))

(defn lights-on-at-step-n
  ([n input]
   (lights-on-at-step-n false n input))
  ([corners-on? n input]
   (->>  (nth (iterate (partial step corners-on?) input) n)
         :grid
         (filter #(= :on (val %)))
         count)))

(defn day18-part1-soln
  []
  (lights-on-at-step-n 100 day18-input))

(defn day18-part2-soln
  []
  (lights-on-at-step-n true 100 (corners-on day18-input)))