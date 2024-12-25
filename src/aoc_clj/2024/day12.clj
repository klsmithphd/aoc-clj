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
   cells positions to the absolute bearing directions of the non-plot cells"
  [cells cell]
  [cell (->> grid/cardinal-offsets
             (remove #(cells (v/vec-add cell (val %))))
             keys
             set)])

(defn perimeter-data
  "Returns a collection of maps of positions-to-bearings for all the cells
   that comprise the perimeter of a plot."
  [cells]
  (->> cells
       (map #(cell-edges cells %))
       (into {})))

(defn perimeter
  "Computes the perimeter of a plot (including both inner and outer
   perimeters)"
  [cells]
  (->> (perimeter-data cells)
       vals
       (map count)
       (reduce +)))

(defn side-aggregator
  "Helper function that counts up the number of unique sides for a given
   perimeter cell and its non-region neighbor directions"
  [{:keys [visited] :as agg} [pos dirs]]
  (let [neighbor-dirs  (->> (keep visited (grid/adj-coords-2d pos))
                            (apply set/union))
        new-sides      (count (set/difference dirs neighbor-dirs))]
    (-> agg
        (assoc-in [:visited pos] dirs)
        (update :sides + new-sides))))

;; Not quite on the right track here, but something like this:
(defn adjacent-order
  [cells]
  (loop [ordered [] queue cells]
    (if (empty? queue)
      ordered
      (let [cell   (first queue)
            neighs (->> (grid/adj-coords-2d cell)
                        (filter (set cells))
                        (remove (set ordered)))
            new-order (conj ordered cell)]
        (recur new-order
               (if (empty? neighs)
                 (remove (set new-order) cells)
                 neighs))))))

(defn sides
  "Counts the number of distinct sides a given contigous region of cells has"
  [cells]
  (let [perim-data (perimeter-data cells)
        order      (adjacent-order (keys perim-data))]
    (->> order
         (map #(vector % (perim-data %)))
         (reduce side-aggregator {:visited {} :sides 0})
         :sides)))

;; (defn count-sides
;;   "Counts the number of sides a given contiguous region has"
;;   [cells]
;;   (let [perimeter (perimeter-cells cells)
;;         visited (atom #{})
;;         sides (atom 0)]
;;     (doseq [cell perimeter]
;;       (when-not (contains? @visited cell)
;;         (swap! sides inc)
;;         (loop [current cell]
;;           (swap! visited conj current)
;;           (let [neighbors (filter #(and (contains? perimeter %)
;;                                         (not (contains? @visited %)))
;;                                   (grid/adj-coords-2d current))]
;;             (when (seq neighbors)
;;               (recur (first neighbors)))))))
;;     @sides))

;; (defn sides
;;   "Counts the number of sides a given contiguous region has"
;;   [cells]
;;   (loop [remaining (perimeter-cells cells)
;;          visited #{}
;;          side-count 0]
;;     (if (empty? remaining)
;;       side-count
;;       (let [cell      (first remaining)
;;             neighbors (grid/adj-coords-2d cell)
;;             remaining (disj remaining cell)]
;;         (if (contains? visited cell)
;;           (recur remaining visited side-count)
;;           (recur remaining
;;                  (conj visited cell)
;;                  (inc side-count)))))))


(defn region-price
  "Returns the price, which is the product of the area and the perimeter"
  [cells]
  (* (perimeter cells) (area cells)))

(defn plot-price
  "Returns the price for a given plot of a type of plant"
  [[_ regions]]
  (->> (map region-price regions)
       (reduce +)))

(defn total-price
  "Computes the total price for a map of regions"
  [region-map]
  (->> (map plot-price region-map)
       (reduce +)))

;; Puzzle solutions
(defn part1
  "What is the total price of fencing all regions on your map?"
  [input]
  (total-price input))