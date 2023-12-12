(ns aoc-clj.2023.day11
  (:require [clojure.math.combinatorics :as combo]
            [aoc-clj.utils.grid :as grid :refer [height width slice value]]
            [aoc-clj.utils.grid.vecgrid :as vg]
            [aoc-clj.utils.math :as math]))

(def charmap {\. :empty \# :galaxy})

(defn parse
  [input]
  (vg/ascii->VecGrid2D charmap input :down true))

(defn empty-slice?
  [slice]
  (every? #(= :empty %) slice))

(defn empty-row-ids
  [grid]
  (->> (range (height grid))
       (filter #(empty-slice? (flatten (:v (slice grid :row %)))))))

(defn empty-col-ids
  [grid]
  (->> (range (width grid))
       (filter #(empty-slice? (flatten (:v (slice grid :col %)))))))

(defn empty-ids
  [grid]
  {:rows (set (empty-row-ids grid))
   :cols (set (empty-col-ids grid))})

(defn doubled-entries
  [idxs items]
  (->> items
       (map-indexed (fn [idx itm]
                      (if (idxs idx)
                        [itm itm]
                        [itm])))
       (apply concat)
       vec))

(defn expanded
  [{:keys [v] :as grid}]
  (let [{:keys [rows cols]} (empty-ids grid)
        expanded-cols (mapv #(doubled-entries cols %) v)
        expanded-rows (doubled-entries rows expanded-cols)]
    (vg/->VecGrid2D expanded-rows)))

(defn galaxies
  [grid]
  (let [locs (for [y (range (height grid))
                   x (range (width grid))]
               [x y])]
    (filter #(= :galaxy (value grid %)) locs)))

(defn galaxy-pair-distance-sum
  [grid]
  (->> (combo/combinations (galaxies grid) 2)
       (map #(apply math/manhattan %))
       (reduce +)))

(defn day11-part1-soln
  [input]
  (galaxy-pair-distance-sum (expanded input)))

