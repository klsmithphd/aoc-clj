(ns aoc-clj.2023.day18
  (:require [clojure.string :as str]
            [aoc-clj.utils.geometry :as geo]))

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
  "Compute the next vertex along the path by interepreting the offset
   relative to the last vertex point in `vertices`"
  [vertices {:keys [dir dist]}]
  (let [[sx sy] (or (last vertices) [0 0])]
    (conj vertices (case dir
                     "U" [sx (- sy dist)]
                     "D" [sx (+ sy dist)]
                     "L" [(- sx dist) sy]
                     "R" [(+ sx dist) sy]))))

(defn vertices
  "Given the dig instruction steps, return the ordered collection of vertices
   of the corners of the dig path"
  [steps]
  (reduce vertices-step [] steps))

(defn dig-area
  "Computes the total dug-up area given the steps by adding
   the interior and perimeter points"
  [steps]
  (let [edges    (geo/vertices->edges (vertices steps))
        interior (geo/interior-count edges)
        boundary (geo/perimeter-length edges)]
    (bigint (+ interior boundary))))

(defn interpret-hex
  "Each hexadecimal code is six hexadecimal digits long. 
   The first five hexadecimal digits encode the distance in meters as a 
   five-digit hexadecimal number. The last hexadecimal digit encodes the 
   direction to dig: 0 means R, 1 means D, 2 means L, and 3 means U."
  [{:keys [color]}]
  {:dist (read-string (str "0x" (subs color 0 5)))
   :dir  (case (subs color 5 6)
           "0" "R"
           "1" "D"
           "2" "L"
           "3" "U")})

(defn dig-area-reinterpreted
  "Compute the dig area after re-interpreting the color hex code as
   described in part 2"
  [steps]
  (dig-area (map interpret-hex steps)))

(defn day18-part1-soln
  "The Elves are concerned the lagoon won't be large enough; 
   if they follow their dig plan, how many cubic meters of lava could it hold?"
  [input]
  (dig-area input))

(defn day18-part2-soln
  "Convert the hexadecimal color codes into the correct instructions; 
   if the Elves follow this new dig plan, how many cubic meters of lava 
   could the lagoon hold?"
  [input]
  (dig-area-reinterpreted input))