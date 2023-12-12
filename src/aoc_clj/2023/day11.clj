(ns aoc-clj.2023.day11
  (:require [aoc-clj.utils.grid :as grid :refer [height width slice]]
            [aoc-clj.utils.grid.vecgrid :as vg]))

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
