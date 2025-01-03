(ns aoc-clj.2024.day12
  "Solution to https://adventofcode.com/2024/day/12"
  (:require [clojure.set :as set]
            [aoc-clj.utils.grid :as grid]
            [aoc-clj.utils.grid.mapgrid :as mg]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.vectors :as v]))

;; Input parsing
(defn adjacent
  "Returns a set of the adjacent cells that are part of the same plot"
  [cells cell]
  (set/intersection cells (set (grid/adj-coords-2d cell))))

(defn region
  "Given the cells belonging to a plot and a specific cell, return a set
   of all the contiguously connected cells"
  [cells cell]
  (loop [regn #{cell}
         front (adjacent cells cell)
         counter 0]
    (if-not (seq front)
      regn
      (let [new-region (into regn front)]
        (recur new-region
               (set (remove new-region (mapcat #(adjacent cells %) front)))
               (inc counter))))))

(defn regions
  "Returns all the regions (mutually connected cells)"
  [cells]
  (loop [groups #{} yet-ungrouped cells]
    (if (empty? yet-ungrouped)
      groups
      (let [new-group (region cells (first yet-ungrouped))]
        (recur (conj groups new-group)
               (remove new-group yet-ungrouped))))))

(defn plots
  "Returns a map of all the lettered plots, with the plot letter as the key
   and the continguous coordinate location regions as the value"
  [{:keys [grid]}]
  (->> (group-by val grid)
       (u/fmap #(regions (set (map key %))))))

(defn parse
  [input]
  (plots (mg/ascii->MapGrid2D identity input :down true)))

;; Puzzle logic
(defn area
  "Computes the area of a plot"
  [cells]
  (count cells))

(defn cell-edges
  "For any cell on the perimeter of the cell plot, returns a map of that
   cells positions to the absolute bearing directions of its edges between it
   and non-plot cells"
  [cells cell]
  [cell (->> grid/cardinal-offsets
             (remove #(cells (v/vec-add cell (val %))))
             keys
             set)])

(defn perimeter-data
  "Returns a collection of maps of coordinates to edge directions for all
   the cells that comprise the perimeter of a plot."
  [cells]
  (->> cells
       (map #(cell-edges cells %))
       (remove (comp empty? second))
       (into {})))

(defn perimeter
  "Computes the perimeter distance of a plot (including both inner and outer
   perimeters)"
  [cells]
  (->> (perimeter-data cells)
       vals
       (map count)
       (reduce +)))

(defn y-desc-then-x-asc-compare
  "Comparator to sort y values in a descending order then x values in an
   ascending order for x,y coordinate pairs"
  [[xa ya] [xb yb]]
  (let [yc (compare yb ya)]
    (if (zero? yc)
      (compare xa xb)
      yc)))

(defn side-aggregator
  "Helper function that counts up the number of unique sides for a given
   perimeter cell and its non-region neighbor directions"
  [{:keys [visited] :as agg} [pos edge-dirs]]
  ;; For a given cell, we look at the direction of its edges, and 
  ;; discount any directional edges that match a previously visited
  ;; nearest neighbor's edges. (This allows us to treat adjacent cell's
  ;; edges as part of the same side.)
  (let [neigh-edge-dirs  (->> (keep visited (grid/adj-coords-2d pos))
                              (apply set/union))
        new-sides        (count (set/difference edge-dirs neigh-edge-dirs))]
    (-> agg
        (assoc-in [:visited pos] edge-dirs)
        (update :sides + new-sides))))

(defn sides
  "Counts the number of distinct sides a given contigous region of cells has"
  [cells]
  (let [perim-data (perimeter-data cells)]
    ;; Examine the cells in reading order (i.e. top left down to bottom right)
    (->> (sort-by key y-desc-then-x-asc-compare perim-data)
         (reduce side-aggregator {:visited {} :sides 0})
         :sides)))

(defn region-price
  "Returns the price, which is the product of the area and the perimeter"
  [part cells]
  (* (area cells) (case part
                    :part1 (perimeter cells)
                    :part2 (sides cells))))

(defn plot-price
  "Returns the price for a given plot of a type of plant"
  [part [_ regions]]
  (->> (map #(region-price part %) regions)
       (reduce +)))

(defn total-price
  "Computes the total price for a map of regions"
  [part region-map]
  (->> (map #(plot-price part %) region-map)
       (reduce +)))

;; Puzzle solutions
(defn part1
  "What is the total price of fencing all regions on your map?"
  [input]
  (total-price :part1 input))

(defn part2
  "What is the new total price of fencing all regions on your map?"
  [input]
  (total-price :part2 input))