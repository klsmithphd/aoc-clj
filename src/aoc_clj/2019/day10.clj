(ns aoc-clj.2019.day10
  "Solution to https://adventofcode.com/2019/day/10"
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.vectors :as v]))

(defn parse
  [ascii-asteroids]
  (for [y (range (count ascii-asteroids))
        x (range (count (first ascii-asteroids)))
        :when (= \# (nth (nth ascii-asteroids y) x))]
    [x y]))

(defn quadrant
  [[x1 y1] [x2 y2]]
  (let [deltay (- y1 y2)
        deltax (- x2 x1)]
    (if (pos? deltay)
      (if (pos? deltax)
        :q1
        :q2)
      (if (pos? deltax)
        :q4
        :q3))))

(defn slope
  "Slope between two points, using coordinates where y-values increase downward"
  [[x1 y1] [x2 y2]]
  (cond
    (= x1 x2) (if (> y1 y2)
                [:up]
                [:down])
    (= y1 y2) (if (> x2 x1)
                [:right]
                [:left])
    :else [(/ (- y1 y2) (- x2 x1)) (quadrant [x1 y1] [x2 y2])]))

(defn visible-asteroid-count
  "Find all asteroids in others that are visible to x"
  [x others]
  (let [others-not-x (filter #(not= x %) others)
        slopes (map (partial slope x) others-not-x)]
    (count (set slopes))))

(defn best-location
  [asteroids]
  (let [counts (map #(visible-asteroid-count % asteroids) asteroids)
        visibles (zipmap asteroids counts)]
    (apply max-key second visibles)))

(defn angle
  [[slope quadrant]]
  (let [angle (case slope
                :up (/ Math/PI 2)
                :right 0
                :down (* 3 (/ Math/PI 2))
                :left Math/PI
                (Math/atan slope))]
    (Math/toDegrees (case quadrant
                      :q2 (+ Math/PI angle)
                      :q3 (+ Math/PI angle)
                      :q4 (+ (* 2 Math/PI) angle)
                      angle))))

(defn polar->compass
  [angle]
  (mod (- 450 angle) 360))

(defn sort-by-distance
  [x others]
  (sort-by (partial v/manhattan x) others))

(defn pad-coll
  [size coll]
  (take size (concat coll (repeat nil))))

(defn asteroids-laser-order
  "Find the order in which the asteroids will be destroyed by the laser"
  [x others]
  (let [others-not-x (filter #(not= x %) others)
        slopes (map (partial slope x) others-not-x)
        angles (map (comp polar->compass angle) slopes)
        by-angle (->> (map vector others-not-x angles)
                      (group-by second)
                      (u/fmap #(map first %)))
        by-angle-sorted (u/fmap (partial sort-by-distance x) by-angle)
        depth  (apply max (map count (vals by-angle-sorted)))
        full-matrix (u/fmap (partial pad-coll depth) by-angle-sorted)]
    (->> (sort-by key full-matrix)
         (map second)
         (apply mapcat vector)
         (filter some?))))

(defn part1
  [input]
  (best-location input))

(defn part2
  [input]
  (let [pos (first (part1 input))
        [x y] (nth (asteroids-laser-order pos input) 199)]
    (+ (* 100 x) y)))

