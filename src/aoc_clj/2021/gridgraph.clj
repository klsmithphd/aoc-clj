(ns aoc-clj.2021.gridgraph
  (:require [aoc-clj.utils.grid :as grid]
            [aoc-clj.utils.graph :as g :refer [Graph]]))

(defrecord GridGraph [grid]
  Graph
  (vertices
    [_]
    (keys (:grid grid)))

  (edges
    [_ v]
    (->> (grid/adj-coords-2d v)
         (filter (comp some? (:grid grid)))))

  (distance
    [_ _ v2]
    (get-in grid [:grid v2])))

;; (defn in-bounds?
;;   [width height [x y]]
;;   (and (<= 0 x (dec width))
;;        (<= 0 y (dec height))))

;; (defrecord TiledGridGraph [grid tile-count]
;;   Graph
;;   (vertices
;;     [_]
;;     (let [maxx (dec (:width grid))
;;           maxy (dec (:height grid))]
;;       (for [y (range (* tile-count maxy))
;;             x (range (* tile-count maxx))]
;;         [x y])))

;;   (edges
;;     [_ v]
;;     (->> (grid/adj-coords-2d v)
;;          (filter (partial in-bounds?
;;                           (* tile-count (:width grid))
;;                           (* tile-count (:height grid))))))

;;   (distance
;;     [_ _ [x y]]
;;     (let [tilex (quot x (:width grid))
;;           posx  (mod  x (:width grid))
;;           tiley (quot y (:height grid))
;;           posy  (mod  y (:height grid))
;;           raw   (get-in grid [:grid [posx posy]])
;;           to-add (mod (+ tilex tiley) 9)
;;           adj   (+ raw to-add)]
;;       (if (>= adj 10) (mod adj 9) adj))))