(ns aoc-clj.utils.grid.core
  (:require [clojure.string :as str]
            [aoc-clj.util.interface :as u]
            [aoc-clj.utils.vectors :as v]))

(defprotocol Grid2D
  "A two-dimensional grid of values indexed by [row col]"
  (width [this] "The total width of the grid (number of columns)")
  (height [this] "The total height of the grid (number of rows)")
  (in-grid? [this pos] "Whether the provided position falls within the boundaries of the grid")
  (value [this pos] "The value of the grid at position [row col]")
  (pos-seq [this] "A sequence of all grid positions in row-major (top-to-bottom) order")
  (val-seq [this] "A sequence of values at each position, in the same order as pos-seq")
  (slice [this dim idx] "A slice of the grid along dim (:row or :col) at index idx")
  (neighbors-4 [this pos] "A map of positions to values for the four nearest (von Neumann) neighbors of pos")
  (neighbors-8 [this pos] "A map of positions to values for the eight nearest (Moore) neighbors of pos"))

(def relative->cardinal
  "For each absolute heading, a map from relative bearing keyword to the
   resulting absolute heading. E.g. facing :n, :right leads to :e."
  {:n {:forward :n :left :w :backward :s :right :e}
   :e {:forward :e :left :n :backward :w :right :s}
   :s {:forward :s :left :e :backward :n :right :w}
   :w {:forward :w :left :s :backward :e :right :n}})

(def cardinal-offsets
  "Map from cardinal heading keyword to [row col] offset.
   Row increases downward, so :n decreases row and :s increases row."
  {:n [-1 0]
   :e [0 1]
   :s [1 0]
   :w [0 -1]})

(def extended-cardinal-offsets
  "Map from all eight compass heading keywords to [row col] offsets,
   combining the four cardinal directions with the four intercardinal diagonals."
  (merge cardinal-offsets
         {:ne [-1 1]
          :se [1 1]
          :sw [1 -1]
          :nw [-1 -1]}))

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
  "Update a state map's :heading by rotating from the current heading
   by the given relative bearing (e.g. :left, :right, :backward)."
  [{:keys [heading] :as state} bearing]
  (assoc state :heading (get-in relative-heading [heading bearing])))

(defn forward
  "Move a state map's :pos by dist steps in its current :heading direction."
  [{:keys [heading] :as state} dist]
  (let [delta (-> (extended-cardinal-offsets heading)
                  (v/scalar-mult dist))]
    (update state :pos v/vec-add delta)))

(defn find-nodes
  "Returns the grid positions of all cells with the given value"
  [v grid]
  (->> (pos-seq grid)
       (filter #(= v (value grid %)))))

(defn Grid2D->ascii
  "Convert a Grid2D into an ASCII-art string representation.

   charmap is a map where the keys are ASCII chars and the values are the
   symbols used in your application. Ex.: (def charmap {\\. :space \\# :wall})

   Row 0 is always the top row; no :down option is needed."
  [charmap grid]
  (let [chars (u/invert-map charmap)
        w     (width grid)
        h     (height grid)
        rep   (partition w (for [row (range h)
                                 col (range w)]
                             (chars (value grid [row col]))))]
    (str/join "\n" (mapv #(apply str %) rep))))

(defn within-grid?
  "Returns true if [row col] falls within the grid bounds"
  [grid [row col]]
  (and (<= 0 row (dec (height grid)))
       (<= 0 col (dec (width grid)))))

(defn positions
  "All [row col] positions in the grid, in row-major (top-to-bottom) order"
  [grid]
  (for [row (range (height grid))
        col (range (width grid))]
    [row col]))

(defn adj-coords-2d
  "Coordinates of adjacent points in [row col] space.

   If include-diagonals is false or absent, returns the four von Neumann
   neighbors in N E S W order. If include-diagonals is true, returns all
   eight Moore neighbors."
  [[row col] & {:keys [include-diagonals]}]
  (if include-diagonals
    (->> (for [nr (range (dec row) (+ row 2))
               nc (range (dec col) (+ col 2))]
           [nr nc])
         (filter #(not= [row col] %)))
    [[(dec row) col] [row (inc col)] [(inc row) col] [row (dec col)]]))

(defn- neighbor-datum
  [grid pos [bearing offset]]
  (let [npos (mapv + pos offset)]
    {:pos     npos
     :val     (value grid npos)
     :heading bearing}))

(defn neighbor-data
  "Given a grid and a cell position pos, return a collection of maps
   describing neighboring cells, each with :pos, :val, and :heading keys.

   If :diagonals is truthy, returns the eight Moore neighbors; otherwise
   returns the four von Neumann neighbors."
  [grid pos & {:keys [diagonals]}]
  (let [neighbors (if diagonals extended-cardinal-offsets cardinal-offsets)]
    (map (partial neighbor-datum grid pos) neighbors)))

(defn- add-rel-bearing
  [heading neighbor]
  (assoc neighbor :bearing
         (get-in relative-bearing [heading (:heading neighbor)])))

(defn with-rel-bearings
  "Augments neighbor data with a :bearing key giving each neighbor's
   bearing relative to the given absolute heading"
  [heading neighbor-data]
  (map (partial add-rel-bearing heading) neighbor-data))

(defn adj-coords-3d
  "Coordinates of the six face-adjacent points in 3D"
  [[x y z]]
  [[x y (inc z)]
   [x y (dec z)]
   [x (inc y) z]
   [x (dec y) z]
   [(inc x) y z]
   [(dec x) y z]])

(defn neighbors-2d
  "Map of positions to values for the nearest neighbors of pos.
   Works correctly with any Grid2D implementation."
  [grid pos & {:keys [include-diagonals]}]
  (let [locs (adj-coords-2d pos :include-diagonals include-diagonals)]
    (zipmap locs (map (partial value grid) locs))))

(defn neighbor-pos
  "Position of the neighbor in cardinal direction dir from pos"
  [pos dir]
  (mapv + pos (cardinal-offsets dir)))

(defn neighbor-value
  "Value of the grid map at the cardinal direction dir from pos.
   grid must be callable as a function (e.g. a plain Clojure map)."
  [grid pos dir]
  (grid (mapv + pos (cardinal-offsets dir))))

(defn rel-neighbors
  "Map of relative directions (:forward :backward :left :right) to neighbor
   values, for a grid position pos facing cardinal direction dir.
   grid must be callable as a function (e.g. a plain Clojure map)."
  [grid pos dir]
  (u/fmap (partial neighbor-value grid pos) (relative->cardinal dir)))

(defn mapgrid->vectors
  "Convert a sparse map with [row col] coordinate keys to a 2D vector of
   vectors spanning the full coordinate range of the keys.
   Missing entries are filled with not-found (default 0)."
  ([m]
   (mapgrid->vectors m 0))
  ([m not-found]
   (let [rows   (map first (keys m))
         cols   (map second (keys m))
         minrow (apply min rows)
         maxrow (apply max rows)
         mincol (apply min cols)
         maxcol (apply max cols)
         w      (- maxcol mincol -1)
         values (for [row (range minrow (inc maxrow))
                      col (range mincol (inc maxcol))]
                  (get m [row col] not-found))]
     (mapv vec (partition w values)))))

(defn interpolated
  "Given start and end points of a horizontal or vertical line segment,
   returns all [row col] positions along it, including both endpoints."
  [[[r1 c1] [r2 c2]]]
  (let [dir-r (if (<= r1 r2) +1 -1)
        dir-c (if (<= c1 c2) +1 -1)]
    (for [r (range r1 (+ r2 dir-r) dir-r)
          c (range c1 (+ c2 dir-c) dir-c)]
      [r c])))
