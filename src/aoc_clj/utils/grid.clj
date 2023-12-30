(ns aoc-clj.utils.grid
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(def relative->cardinal
  {:n {:forward :n :left :w :backward :s :right :e}
   :e {:forward :e :left :n :backward :w :right :s}
   :s {:forward :s :left :e :backward :n :right :w}
   :w {:forward :w :left :s :backward :e :right :n}})

(def cardinal-offsets
  {:n [0 1]
   :e [1 0]
   :s [0 -1]
   :w [-1 0]})

(def extended-cardinal-offsets
  (merge cardinal-offsets
         {:ne [1 1]
          :se [1 -1]
          :sw [-1 -1]
          :nw [-1 1]}))

(def headings
  "The eight cardinal and intercardinal compass headings"
  [:n :ne :e :se :s :sw :w :nw])

(def rel-bearings
  "Eight relative bearings"
  [:forward
   :forward-right
   :right
   :backward-right
   :backward
   :backward-left
   :left
   :forward-left])

(def relative-bearing
  "A mapping from a given compass heading to a map that translates
   each compass heading to its relative bearing"
  (zipmap headings
          (map #(zipmap (u/rotate % headings) rel-bearings) (range 8))))

(defprotocol Grid2D
  "A two-dimensional grid of values"
  (width [this] "The total width of the grid (number of cells in the horizontal direction)")
  (height [this] "The total height of the grid (number of cells in the vertical direction)")
  (in-grid? [this pos] "Whether the provided position falls within the boundaries of the grid")
  (value [this pos] "The value of the grid at position pos")
  (slice [this dim idx] "A slice of the grid along dim (:row or :col) at index idx")
  (neighbors-4 [this pos] "A map of the positions and values of the four nearest (von Neumann) neighbors of position pos")
  (neighbors-8 [this pos] "A map of the positions and values of the eight nearest (Moore) neighbors, including diagonals, of position pos"))

(defn Grid2D->ascii
  "Convert a Grid2D into an ASCII-art string representation.
   
   charmap is a map where the keys are ASCII chars and
   the values are expected to be symbols to use in
   your application. Ex.: (def charmap {\\. :space \\# :wall})
   
   If `down` is specified, the first row represents zero and the
   y coordinate increases in the down direction, i.e the way
   screen coordinates typically work."
  [charmap grid2d & {:keys [down]}]
  (let [chars (u/invert-map charmap)
        w (width grid2d)
        h (height grid2d)
        rep (partition w (for [y (if down (range h) (u/rev-range h))
                               x (range w)]
                           (chars (value grid2d [x y]))))]
    (str/join "\n" (mapv #(apply str %) rep))))

(defn within-grid?
  "Returns true if the position is contained within the `grid`"
  [grid [x y]]
  (and (<= 0 x (dec (width grid)))
       (<= 0 y (dec (height grid)))))

(defn adj-coords-2d
  "Coordinates of adjacent points. If include-diagonals is not set or false, 
   returns the four adjacent points (of the von Neumann neighborhood), 
   always in the order N E S W. If include-diagonals is set to true,
   return the eight adjacent coordinates (of the Moore neighborhood)"
  [[x y] & {:keys [include-diagonals]}]
  (if include-diagonals
    ;; including diagonals
    (->> (for [ny (range (dec y) (+ y 2))
               nx (range (dec x) (+ x 2))]
           [nx ny])
         (filter #(not= [x y] %)))
    ;; only directly adjacent
    [[x (inc y)] [(inc x) y] [x (dec y)] [(dec x) y]]))

(defn- neighbor-datum
  "Constructs a map of the position, value, and absolute bearing from
   the position `pos`"
  [grid pos [bearing offset]]
  (let [neighbor-pos (mapv + pos offset)]
    {:pos         neighbor-pos
     :val         (value grid neighbor-pos)
     :heading bearing}))

(defn neighbor-data
  "Given a `grid` and a fixed cell position `pos`, return a collection of
   information about neighboring grid cells.
   
   If keyword `:diagonals` is set to a truthy value, will return values
   for the eight (Moore) neighbors. Otherwise, by default, only returns
   values for the four (von Neumann) neighbors."
  ([grid pos & {:keys [diagonals]}]
   (let [neighbors (if diagonals
                     extended-cardinal-offsets
                     cardinal-offsets)]
     (map (partial neighbor-datum grid pos) neighbors))))

(defn- add-rel-bearing
  "Uses knowledge of the absolute heading of the original position and
   augments the neighbor datum with a relative bearing"
  [heading neighbor]
  (assoc neighbor :bearing
         (get-in relative-bearing [heading (:heading neighbor)])))

(defn with-rel-bearings
  "Augments neighbor data with the relative heading of each neighboring
   position, given the original position's `heading`"
  [heading neighbor-data]
  (map (partial add-rel-bearing heading) neighbor-data))

(defn adj-coords-3d
  "Coordinates of adjacent points in 3D"
  [[x y z]]
  [[x y (inc z)]
   [x y (dec z)]
   [x (inc y) z]
   [x (dec y) z]
   [(inc x) y z]
   [(dec x) y z]])

(defn neighbors-2d
  "Map of the positions and values of the nearest neighbors to pos"
  [grid pos & {:keys [include-diagonals]}]
  (select-keys grid (adj-coords-2d pos :include-diagonals include-diagonals)))

(defn neighbor-pos
  "Find the position of the neighbor in the cardinal direction `dir` 
   relative to position `pos`"
  [pos dir]
  (mapv + pos (cardinal-offsets dir)))

(defn neighbor-value
  "Find the value of the `grid` map at the cardinal direction `dir` 
   relative to position `pos`"
  [grid pos dir]
  (grid (mapv + pos (cardinal-offsets dir))))

(defn rel-neighbors
  "Return a map of the relative directions (forward, backward, left, right)
   to the value of the value of the respective neighbor on the `grid`
   at position `pos` and facing cardinal direction `dir`"
  [grid pos dir]
  (u/fmap (partial neighbor-value grid pos) (relative->cardinal dir)))

(defn mapgrid->vectors
  "Convert a (sparse) mapgrid `m` (a map with [x y] coordinates as keys) 
   to a 2D vector of vectors. The new vectors will span the coordinate
   space of the keys of the mapgrid. Missing values will be filled in
   by the `not-found` arg or 0 by default."
  ([m]
   (mapgrid->vectors m 0))
  ([m not-found]
   (let [xs     (map first (keys m))
         ys     (map second (keys m))
         minx   (apply min xs)
         maxx   (apply max xs)
         miny   (apply min ys)
         maxy   (apply max ys)
         width  (- maxx minx -1)
         values (for [y (range maxy (dec miny) -1)
                      x (range minx (inc maxx))]
                  (get m [x y] not-found))]
     (mapv vec (partition width values)))))


