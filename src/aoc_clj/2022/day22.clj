(ns aoc-clj.2022.day22
  "Solution to https://adventofcode.com/2022/day/22"
  (:require [aoc-clj.utils.core :as u]))

;;;; Constants

(def charmap {\  nil \. :open \# :wall})
(def turn-right {:U :R :R :D :D :L :L :U})
(def turn-left  {:U :L :L :D :D :R :R :U})
(def facing-value
  "Facing is 0 for right (>), 1 for down (v), 2 for left (<), and 3 for up (^)"
  {:R 0 :D 1 :L 2 :U 3})

;;;; Input parsing

(defn parse-line
  "Return a collection of [[x y] value] pairs based on the charmap.
   Empty space cells are removed"
  [y s]
  (filter #(some? (second %))
          (map vector
               (for [x (range (count s))] [(inc x) (inc y)])
               (map charmap s))))

(defn bounds
  "For a sorted list of lists of points, compute the extent of the bounds"
  [dir lines]
  (let [[minx miny] (-> lines first first)
        [maxx maxy] (-> lines last last)]
    (case dir
      :horiz [[miny maxy] [minx maxx]]
      :vert  [[minx maxx] [miny maxy]])))

(def dir-lookup
  {:horiz second
   :vert  first})

(defn zones
  "Compute the rectangular zones of continuous x,y values in either
   the horizontal or vertical directions as per `dir`.
   
   Zones are indicated as a range in one direction and the corresponding
   range in the orthogonal direction"
  [dir points]
  (->> points
       ;; Select just the coordinates
       (map first)
       ;; Group by either the x or y value
       (group-by (dir-lookup dir))
       ;; Sort lexigraphically by the key (the x or y value)
       sort
       ;; Take just the keys (the groupings of points for a fixed x or y)
       (map second)
       ;; Break into partitions whenever the size of the lines changes
       (partition-by count)
       ;; Apply the bounds fn on the partitions to compute their extent
       (map (partial bounds dir))))

(def horizontal-zones (partial zones :horiz))
(def vertical-zones   (partial zones :vert))

(defn parse-map
  "Populate a map based on the ASCII string representation of the grid"
  [s]
  (let [points (apply concat (map-indexed parse-line s))]
    {:grid (into {} points)
     :start (first (sort (filter #(= 1 (second %)) (map first points))))
     :h-zones (horizontal-zones points)
     :v-zones (vertical-zones points)}))

(defn num-or-string
  [s]
  (if (number? (read-string s))
    (read-string s)
    s))

(defn parse-path
  [s]
  (map num-or-string (re-seq #"\d+|[LR]" s)))

(defn parse
  [input]
  (let [[a b] (u/split-at-blankline input)]
    (assoc (parse-map a)
           :path (parse-path (first b)))))

(def day22-input (u/parse-puzzle-input parse 2022 22))

;;;; Puzzle logic

(defn zone-match
  [[a b] [[mn mx] [vmin vmax]]]
  (when (<= mn a mx)
    (if (< b vmin)
      vmax
      vmin)))

(defn lookup
  [zones pos]
  (first (filter some? (map #(zone-match pos %) zones))))

(defn wrap-around
  "Wrap horizontally or vertically when at the edge of the known grid"
  [{:keys [h-zones v-zones]} facing [x y]]
  (if (or (= facing :L) (= facing :R))
    {:facing facing :pos [(lookup h-zones [y x]) y]}
    {:facing facing :pos [x (lookup v-zones [x y])]}))

(defn next-pos
  [facing [x y]]
  (case facing
    :U [x (dec y)]
    :R [(inc x) y]
    :D [x (inc y)]
    :L [(dec x) y]))

(defn wall?
  [{:keys [grid]} pos]
  (= :wall (grid pos)))

(defn next-cell
  "Attempt to move to the next cell unless that cell is occupied
   by a wall. May wrap around based on the `wrap-fn` logic"
  [{:keys [grid wrap-fn] :as themap} {:keys [facing pos]}]
  (let [test-pos (next-pos facing pos)]
    (if (grid test-pos)
      {:facing facing :pos test-pos}
      (wrap-fn themap facing test-pos))))

(defn turn
  "Turn left or right as given by `dir`"
  [state dir]
  (update state :facing (case dir
                          "R" turn-right
                          "L" turn-left)))

(defn walk
  "Move forward the number of spaces given by `dist` unless 
   you collide with a wall."
  [themap state dist]
  (loop [s state cnt dist]
    (let [{:keys [pos] :as new-s} (next-cell themap s)]
      (if (or (zero? cnt) (wall? themap pos))
        s
        (recur new-s (dec cnt))))))

(defn apply-cmd
  "Apply the given path command (either move forward a certain number
   of spaces or turn)"
  [themap state cmd]
  (if (number? cmd)
    (walk themap state cmd)
    (turn state cmd)))

(defn follow-path
  "Apply the path commands sequentially until complete"
  [{:keys [path start] :as themap}]
  (reduce (partial apply-cmd themap) {:pos start :facing :R} path))

(defn final-password
  "The final password is the sum of 1000 times the row, 
   4 times the column, and the facing."
  [{:keys [pos facing]}]
  (let [[x y] pos]
    (+ (* 1000 y) (* 4 x) (facing-value facing))))

(defn cube-wrap-around
  "For the specifics of my puzzle input, compute the new position
   wrapping around the flattened cube. "
  [_ facing [x y]]
  (case facing
    :U (cond
         (<= 1 x 50)    {:pos [51 (+ 51 (- x 1))]    :facing :R}
         (<= 51 x 100)  {:pos [1 (+ 151 (- x 51))]   :facing :R}
         (<= 101 x 150) {:pos [(+ 1 (- x 101)) 200]  :facing :U})
    :D (cond
         (<= 1 x 50)    {:pos [(+ 101 (- x 1)) 1]    :facing :D}
         (<= 51 x 100)  {:pos [50 (+ 151 (- x 51))]  :facing :L}
         (<= 101 x 150) {:pos [100 (+ 51 (- x 101))] :facing :L})
    :L (cond
         (<= 1 y 50)    {:pos [1 (+ 101 (- 50 y))]   :facing :R}
         (<= 51 y 100)  {:pos [(+ 1 (- y 51)) 101]   :facing :D}
         (<= 101 y 150) {:pos [51 (+ 1 (- 150 y))]   :facing :R}
         (<= 151 y 200) {:pos [(+ 51 (- y 151)) 1]   :facing :D})
    :R (cond
         (<= 1 y 50)    {:pos [100 (+ 101 (- 50 y))] :facing :L}
         (<= 51 y 100)  {:pos [(+ 101 (- y 51)) 50]  :facing :U}
         (<= 101 y 150) {:pos [150 (+ 1 (- 150 y))]  :facing :L}
         (<= 151 y 200) {:pos [(+ 51 (- y 151)) 150] :facing :U})))

(defn day22-part1-soln
  "Follow the path given in the monkeys' notes. What is the final password?"
  []
  (final-password (follow-path (assoc day22-input :wrap-fn wrap-around))))

(defn day22-part2-soln
  "Fold the map into a cube, then follow the path given in the monkeys' notes. 
   What is the final password?"
  []
  (final-password (follow-path (assoc day22-input :wrap-fn cube-wrap-around))))

