(ns aoc-clj.2020.day17
  "Solution to https://adventofcode.com/2020/day/17"
  (:require [aoc-clj.grid.interface :as mapgrid]
            [aoc-clj.util.interface :as u]))

(defn twod->threed
  [[row col]]
  [row col 0])

(defn parse
  [ascii-lines]
  (let [char-map {\. :inactive
                  \# :active}
        slice (mapgrid/ascii->MapGrid2D char-map ascii-lines)]
    (update slice :grid-map (partial u/kmap twod->threed))))

(def adjacent-dirs-3d
  (->> (for [z (range -1 2)
             y (range -1 2)
             x (range -1 2)]
         [x y z])
       (filter #(not= [0 0 0] %))))

(def adjacent-dirs-4d
  (->> (for [w (range -1 2)
             z (range -1 2)
             y (range -1 2)
             x (range -1 2)]
         [x y z w])
       (filter #(not= [0 0 0 0] %))))

(defn adjacent-3d
  [pos]
  (map #(mapv + pos %) adjacent-dirs-3d))

(defn adjacent-4d
  [pos]
  (map #(mapv + pos %) adjacent-dirs-4d))

(defn rules
  [adjacency grid pos]
  (let [state            (get grid pos :inactive)
        active-neighbors (->> (adjacency pos)
                              (map grid)
                              (filter #{:active})
                              count)]
    (case state
      ;;  "If a cube is active 
      ;;  and exactly 2 or 3 of its neighbors are also active, 
      ;;  the cube remains active. 
      ;;  Otherwise, the cube becomes inactive.
      :active (if (or (= 2 active-neighbors)
                      (= 3 active-neighbors))
                :active
                :inactive)
      ;;  If a cube is inactive 
      ;;  but exactly 3 of its neighbors are active, 
      ;;  the cube becomes active. 
      ;;  Otherwise, the cube remains inactive
      :inactive (if (= 3 active-neighbors)
                  :active
                  :inactive))))

(def rules-3d (partial rules adjacent-3d))
(def rules-4d (partial rules adjacent-4d))

;; TODO - come back and make this only expand from
;; known points instead of the entire grid
(defn scan-space-3d
  [height width limit]
  (for [z   (range (- limit) (inc limit))
        row (range (- limit) (+ height limit))
        col (range (- limit) (+ width limit))]
    [row col z]))

;; TODO - come back and make this only expand from
;; known points instead of the entire grid
(defn scan-space-4d
  [height width limit]
  (for [w   (range (- limit) (inc limit))
        z   (range (- limit) (inc limit))
        row (range (- limit) (+ height limit))
        col (range (- limit) (+ width limit))]
    [row col z w]))

(defn evolve-3d
  [{:keys [height width grid-map]} limit]
  (loop [statemap grid-map cnt 0]
    (if (= limit cnt)
      statemap
      (let [locs (scan-space-3d height width limit)
            newvals (map (partial rules-3d statemap) locs)]
        (recur (zipmap locs newvals) (inc cnt))))))

(defn evolve-4d
  [{:keys [height width grid-map]} limit]
  (loop [statemap grid-map cnt 0]
    (if (= limit cnt)
      statemap
      (let [locs (scan-space-4d height width limit)
            newvals (map (partial rules-4d statemap) locs)]
        (recur (zipmap locs newvals) (inc cnt))))))

(defn promote-to-4d
  [{:keys [grid-map] :as input}]
  (let [add-dim (fn [[row col z]] [row col z 0])]
    (assoc input :grid-map (u/kmap add-dim grid-map))))

(defn part1
  [input]
  (->> (evolve-3d input 6)
       vals
       (filter #{:active})
       count))

(defn part2
  [input]
  (->> (evolve-4d (promote-to-4d input) 6)
       vals
       (filter #{:active})
       count))