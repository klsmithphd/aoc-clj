(ns aoc-clj.utils.grid
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.vectors :as v]))

(defprotocol Grid2D
  "A two-dimensional grid of values"
  (width [this] "The total width of the grid (number of cells in the horizontal direction)")
  (height [this] "The total height of the grid (number of cells in the vertical direction)")
  (orientation [this] "The orientation of the y-axis: :y-up or :y-down")
  (in-grid? [this pos] "Whether the provided position falls within the boundaries of the grid")
  (value  [this pos] "The value of the grid at position pos")
  (pos-seq [this] "A sequence of all of the grid positions")
  (val-seq [this] "A sequence of the values at each of the grid positions, in the same order as `pos-seq`")
  (slice [this dim idx] "A slice of the grid along dim (:row or :col) at index idx")
  (neighbors-4 [this pos] "A map of the positions and values of the four nearest (von Neumann) neighbors of position pos")
  (neighbors-8 [this pos] "A map of the positions and values of the eight nearest (Moore) neighbors, including diagonals, of position pos"))

(def relative->cardinal
  {:n {:forward :n :left :w :backward :s :right :e}
   :e {:forward :e :left :n :backward :w :right :s}
   :s {:forward :s :left :e :backward :n :right :w}
   :w {:forward :w :left :s :backward :e :right :n}})

(def y-up-cardinal-offsets
  {:n [0 1]
   :e [1 0]
   :s [0 -1]
   :w [-1 0]})

(def y-down-cardinal-offsets
  {:n [0 -1]
   :e [1 0]
   :s [0 1]
   :w [-1 0]})

(def cardinal-offsets
  {:y-up   y-up-cardinal-offsets
   :y-down y-down-cardinal-offsets})

(def y-up-extended-cardinal-offsets
  (merge y-up-cardinal-offsets
         {:ne [1 1]
          :se [1 -1]
          :sw [-1 -1]
          :nw [-1 1]}))

(def y-down-extended-cardinal-offsets
  (merge y-down-cardinal-offsets
         {:ne [1 -1]
          :se [1 1]
          :sw [-1 1]
          :nw [-1 -1]}))

(def extended-cardinal-offsets
  {:y-up   y-up-extended-cardinal-offsets
   :y-down y-down-extended-cardinal-offsets})

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

(def relative-heading
  "A mapping from a given compass heading to a map that translates
   a relative bearing to the corresponding new heading"
  (zipmap headings
          (map #(zipmap (u/rotate % rel-bearings) headings) (range 0 -8 -1))))

(defn turn
  [{:keys [heading] :as state} bearing]
  (assoc state :heading (get-in relative-heading [heading bearing])))

(defn forward
  ([state dist] (forward state dist :y-up))
  ([{:keys [heading] :as state} dist orientation]
   (let [delta (-> (get-in extended-cardinal-offsets [orientation heading])
                   (v/scalar-mult dist))]
     (update state :pos v/vec-add delta))))

(defn find-nodes
  "Returns the grid coordinate positions of all the nodes with the given value"
  [v grid]
  (->> (pos-seq grid)
       (filter #(= v (value grid %)))))

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

(defn positions
  [grid]
  (for [y (range (height grid))
        x (range (width grid))]
    [x y]))

(defn adj-coords-2d
  "Coordinates of adjacent points. If include-diagonals is not set or false, 
   returns the four adjacent points (of the von Neumann neighborhood), 
   always in the order N E S W. If include-diagonals is set to true,
   return the eight adjacent coordinates (of the Moore neighborhood)"
  ([[x y] & {:keys [include-diagonals orientation] :or {orientation :y-up}}]
   (if include-diagonals
     ;; including diagonals
     (if (= :y-up orientation)
       [[x (inc y)] [(inc x) (inc y)] [(inc x) y] [(inc x) (dec y)]
        [x (dec y)] [(dec x) (dec y)] [(dec x) y] [(dec x) (inc y)]]
       [[x (dec y)] [(inc x) (dec y)] [(inc x) y] [(inc x) (inc y)]
        [x (inc y)] [(dec x) (inc y)] [(dec x) y] [(dec x) (dec y)]])
     ;; only directly adjacent
     (let [offsets (vals (get cardinal-offsets orientation))]
       (mapv #(mapv + [x y] %) offsets)))))

(defn neighbor-data
  "Given a `grid` and a fixed cell position `pos`, return a collection of
   information about neighboring grid cells.
   
   If keyword `:diagonals` is set to a truthy value, will return values
   for the eight (Moore) neighbors. Otherwise, by default, only returns
   values for the four (von Neumann) neighbors."
  ([grid pos & {:keys [diagonals orientation]}]
   (let [grid-orientation (or orientation (:orientation grid :y-up))
         neighbors (if diagonals
                     (get extended-cardinal-offsets grid-orientation)
                     (get cardinal-offsets grid-orientation))
         is-grid-2d? (satisfies? Grid2D grid)]
     (map (fn [[bearing offset]]
            (let [neighbor-pos (mapv + pos offset)]
              {:pos     neighbor-pos
               :val     (if is-grid-2d? (value grid neighbor-pos) (get grid neighbor-pos))
               :heading bearing}))
          neighbors))))

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
  [grid pos & {:keys [include-diagonals orientation]}]
  (let [grid-orientation (or orientation (:orientation grid :y-up))
        locs (adj-coords-2d pos :include-diagonals include-diagonals :orientation grid-orientation)]
    (if (satisfies? Grid2D grid)
      (zipmap locs (map #(value grid %) locs))
      (select-keys grid locs))))

(defn neighbor-pos
  "Find the position of the neighbor in the cardinal direction `dir` 
   relative to position `pos`"
  ([pos dir] (neighbor-pos pos dir :y-up))
  ([pos dir orientation]
   (mapv + pos (get-in cardinal-offsets [orientation dir]))))

(defn neighbor-value
  "Find the value of the `grid` map at the cardinal direction `dir` 
   relative to position `pos`"
  ([grid pos dir] (neighbor-value grid pos dir :y-up))
  ([grid pos dir orientation]
   (let [neighbor-pos (neighbor-pos pos dir orientation)]
     (if (satisfies? Grid2D grid)
       (value grid neighbor-pos)
       (get grid neighbor-pos)))))

(defn rel-neighbors
  "Return a map of the relative directions (forward, backward, left, right)
   to the value of the value of the respective neighbor on the `grid`
   at position `pos` and facing cardinal direction `dir`"
  ([grid pos dir] (rel-neighbors grid pos dir :y-up))
  ([grid pos dir orientation]
   (u/fmap (fn [target-dir] (neighbor-value grid pos target-dir orientation))
           (relative->cardinal dir))))

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

(defn interpolated
  "Given a start point and end point for a line segment
   (assumed to be either horizontal, vertical, or 45-degree diagonal), 
   return all the points along the line segment.
   
   The collection returned will include both the start and end points"
  [[[x1 y1] [x2 y2]]]
  (let [dx (- x2 x1)
        dy (- y2 y1)
        steps (max (abs dx) (abs dy))
        step-x (cond (pos? dx) 1 (neg? dx) -1 :else 0)
        step-y (cond (pos? dy) 1 (neg? dy) -1 :else 0)]
    (for [i (range (inc steps))]
      [(+ x1 (* i step-x)) (+ y1 (* i step-y))])))


