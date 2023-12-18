(ns aoc-clj.2023.day18
  (:require [clojure.string :as str]
            [aoc-clj.utils.math :as math]))

(defn parse-line
  [line]
  (let [[a b c] (str/split line #" ")]
    {:dir a
     :dist (read-string b)
     :color (subs c 1 (dec (count c)))}))

(defn parse
  [input]
  (map parse-line input))

(defn vertices-step
  [vertices {:keys [dir dist]}]
  (let [[sx sy] (or (last vertices) [0 0])]
    (conj vertices (case dir
                     "U" [sx (- sy dist)]
                     "D" [sx (+ sy dist)]
                     "L" [(- sx dist) sy]
                     "R" [(+ sx dist) sy]))))

(defn vertices
  [steps]
  (reduce vertices-step [] steps))

;; TODO make a utility function 
(defn ring
  "Returns a collection wrapped back around to its beginning, with the
   first element repeated as its last. If the size of `coll` is `n`,
   then `(ring coll)` returns a collection of size n+1"
  [coll]
  (let [n (count coll)]
    (take (inc n) (cycle coll))))

(defn edges
  "Take a collection of vertices and return a collection of all the edges"
  [vertices]
  (->> (ring vertices)
       (partition 2 1)))

(defn perimeter-length
  [vertices]
  (->> (edges vertices)
       (map #(apply math/manhattan %))
       (reduce +)))

(defn shoelace-step
  "Helper function for Shoelace formula
   https://en.wikipedia.org/wiki/Shoelace_formula"
  [[[x1 y1] [x2 y2]]]
  (- (* x1 y2) (* x2 y1)))

(defn polygon-area
  "Computes area of a polygon given
   https://en.wikipedia.org/wiki/Shoelace_formula"
  [vertices]
  (let [area2 (->> (edges vertices)
                   (map shoelace-step)
                   (reduce +))]
    (abs (/ area2 2))))

(defn interior-count
  "Computes the number of interior points using Pick's theorem"
  [vertices]
  (let [area     (polygon-area vertices)
        boundary (perimeter-length vertices)]
    (- area (/ boundary 2) -1)))

(defn dig-area
  "Computes the total dug-up area given the steps by adding
   the interior and perimeter points"
  [steps]
  (let [vs       (vertices steps)
        area     (polygon-area vs)
        boundary (perimeter-length vs)]
    (+ area (/ boundary 2) 1)))

(defn day18-part1-soln
  [input]
  (dig-area input))


(defn trench-step
  [points {:keys [dir dist]}]
  (let [[sx sy] (or (last points) [0 0])]
    (into points (case dir
                   "U" (for [y (range (dec sy) (- sy dist 1) -1)] [sx y])
                   "D" (for [y (range (inc sy) (+ sy dist 1))]    [sx y])
                   "L" (for [x (range (dec sx) (- sx dist 1) -1)] [x sy])
                   "R" (for [x (range (inc sx) (+ sx dist 1))]    [x sy])))))

(defn trench
  [steps]
  (reduce trench-step [] steps))

(defn interior-counts
  [coll]
  (let [change-signals (map #(not= 1 (- %1 %2)) (next coll) coll)]
    change-signals))

(interior-counts [0 1 2 5 6 7])
(interior-counts [0 2 5 7 8 9])

(defn consecutive-groups
  [coll]
  (->> coll
       (map-indexed (fn [i x] [(- x i) x]))
       (partition-by first)
       (map (partial map second))))

;; (defn vertices
;;   [coll]
;;   (->> (consecutive-groups coll)
;;        (mapcat (fn [nums] [(first nums) (last nums)]))
;;        (dedupe)))

(vertices [0 2 3 4 5 9])

(defn interior
  [points]
  (->>
   (sort points)
   (partition-by first)
   (map (partial map second))
   (map consecutive-groups)))



;; sort the points, default will give all x = i with y's ascending, then x = i+1
;; For each x
  ;; group ys into consecutive ranges
  ;; count the cells between endpoints"

