(ns aoc-clj.2023.day17
  (:require [clojure.string :as str]
            [aoc-clj.utils.graph :as g :refer [Graph]]
            [aoc-clj.utils.grid :as grid :refer [width height value]]
            [aoc-clj.utils.grid.vecgrid :as vg]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.math :as math]))

(defn parse-line
  [line]
  (u/str->vec (str/join " " line)))

(defn parse
  [input]
  (vg/->VecGrid2D (mapv parse-line input)))

(def allowed-headings
  {:R [:R :U :D]
   :L [:L :U :D]
   :U [:U :L :R]
   :D [:D :L :R]})

;; TODO - Copied from Day 16 -- should be a library fn
(defn next-cell
  "Returns the next cell from the position along the `heading` direction"
  [[x y] heading]
  (case heading
    :U [x (dec y)]
    :R [(inc x) y]
    :D [x (inc y)]
    :L [(dec x) y]))

;; TODO - Copied from Day 16 -- should be a library fn
(defn in-grid?
  "Returns true if the position is contained within the `grid`"
  [grid [x y]]
  (let [h (dec (height grid))
        w (dec (width grid))]
    (and (<= 0 x w) (<= 0 y h))))

(defn new-vertex
  [pos heading count]
  {:pos pos :heading heading :count count})

(defn part1-rules
  [vertices]
  (remove #(>= (:count %) 3) vertices))

(defn part1-finish?
  [end {:keys [pos]}]
  (= end pos))

(defn part2-rules
  [vertices]
  (let [straight (first vertices)]
    (if (< (:count straight) 4)
      [straight]
      (remove #(>= (:count %) 10) vertices))))

(defn part2-finish?
  [end {:keys [pos count]}]
  (and (= end pos)
       (<= 3 count)))

(defn next-vertices
  [grid steering-rules {:keys [pos heading count]}]
  (let [headings  (allowed-headings heading)
        positions (map #(next-cell pos %) headings)
        counts    [(inc count) 0 0]
        vertices  (map new-vertex positions headings counts)]
    (->> vertices
         steering-rules
         (filter #(in-grid? grid (:pos %))))))

(defrecord CityGrid [grid steering-rules]
  Graph
  (edges
    [_ v]
    (next-vertices grid steering-rules v))
  (distance
    [_ _ {:keys [pos]}]
    (value grid pos)))

(defn min-heat-loss
  [grid steering finish?]
  (let [end [(dec (width grid)) (dec (height grid))]
        heuristic #(math/manhattan end (:pos %))
        graph (->CityGrid grid steering)]
    (->>
     (g/a-star graph
               {:pos [0 0] :heading :R :count 0}
               (partial finish? end)
               heuristic)
     (drop 1)
     (map :pos)
     (map #(value grid %))
     (reduce +))))

(defn day17-part1-soln
  [input]
  (min-heat-loss input part1-rules part1-finish?))

(defn day17-part2-soln
  [input]
  (min-heat-loss input part2-rules part2-finish?))
