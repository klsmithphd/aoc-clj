(ns aoc-clj.2023.day18
  (:require [clojure.string :as str]
            [aoc-clj.utils.math :as math]))

(defn parse-line
  [line]
  (let [[a b c] (str/split line #" ")]
    {:dir a
     :dist (read-string b)
     :color (subs c 2 (dec (count c)))}))

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

(defn interpret-hex
  [{:keys [color]}]
  {:dist (read-string (str "0x" (subs color 0 5)))
   :dir  (case (subs color 5 6)
           "0" "R"
           "1" "D"
           "2" "L"
           "3" "U")})

(defn dig-area-reinterpreted
  [steps]
  (dig-area (map interpret-hex steps)))

(defn day18-part1-soln
  [input]
  (dig-area input))

(defn day18-part2-soln
  [input]
  (dig-area-reinterpreted input))