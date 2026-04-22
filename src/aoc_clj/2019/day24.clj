(ns aoc-clj.2019.day24
  "Solution to https://adventofcode.com/2019/day/24"
  (:require [aoc-clj.util.interface :as u]
            [aoc-clj.utils.grid.core :as grid]
            [aoc-clj.utils.grid.mapgrid :as mapgrid-rc]))

(def bug-map
  {\. :space
   \# :bug
   \? :down})

(defn parse
  [input]
  (:grid-map (mapgrid-rc/ascii->MapGrid2D bug-map input)))

(defn conway-rule
  [space pos neighbor-fn]
  (let [neighbors (neighbor-fn space pos)
        state (space pos)
        adjacent-bug-count (u/count-if neighbors #(= :bug (val %)))]
    (case state
      :space (if (or (= 1 adjacent-bug-count) (= 2 adjacent-bug-count))
               :bug
               :space)
      :bug   (if (= 1 adjacent-bug-count)
               :bug
               :space)
      state)))

(defn conway-step
  [space rule]
  (zipmap (keys space)
          (map (partial rule space) (keys space))))

(defn conway-rule-2d
  [space pos]
  (conway-rule space pos #(select-keys %1 (grid/adj-coords-2d %2))))

(defn conway-step-2d
  [space]
  (conway-step space conway-rule-2d))

(defn biodiversity
  [space]
  (read-string (apply str "2r" (map {:bug 1 :space 0} (reverse (for [row (range 5) col (range 5)] (space [row col])))))))

(defn find-recurrence
  [space]
  (loop [grid space last (biodiversity space) seen #{}]
    (if (some? (seen last))
      last
      (let [nextgrid (conway-step-2d grid)
            nextbio (biodiversity nextgrid)]
        (recur nextgrid nextbio (conj seen last))))))

(defn base-neighbor-coords
  [[row col z]]
  [[(dec row) col z] [row (dec col) z] [(inc row) col z] [row (inc col) z]])

(defn outer-top
  [coords [row _ z]]
  (if (= 0 row)
    (conj coords [1 2 (inc z)])
    coords))
(defn outer-bottom
  [coords [row _ z]]
  (if (= 4 row)
    (conj coords [3 2 (inc z)])
    coords))
(defn outer-left
  [coords [_ col z]]
  (if (= 0 col)
    (conj coords [2 1 (inc z)])
    coords))
(defn outer-right
  [coords [_ col z]]
  (if (= 4 col)
    (conj coords [2 3 (inc z)])
    coords))
(defn inner-top
  [coords [row col z]]
  (if (= [1 2] [row col])
    (into coords (for [c (range 5)] [0 c (dec z)]))
    coords))
(defn inner-bottom
  [coords [row col z]]
  (if (= [3 2] [row col])
    (into coords (for [c (range 5)] [4 c (dec z)]))
    coords))
(defn inner-left
  [coords [row col z]]
  (if (= [2 1] [row col])
    (into coords (for [r (range 5)] [r 0 (dec z)]))
    coords))
(defn inner-right
  [coords [row col z]]
  (if (= [2 3] [row col])
    (into coords (for [r (range 5)] [r 4 (dec z)]))
    coords))

(def appenders
  [outer-top outer-bottom outer-left outer-right inner-top inner-bottom inner-left inner-right])

(defn neighbors3d-coords
  [pos]
  (let [base-coords (base-neighbor-coords pos)]
    (reduce #(%2 %1 pos) base-coords appenders)))

(defn neighbors3d
  [space pos]
  (let [coords (neighbors3d-coords pos)
        vals (map space coords)]
    (zipmap coords vals)))

(defn space3d-init
  [level0 depth]
  (merge
   (zipmap (for [z (range (- depth) (inc depth))
                 row (range 5)
                 col (range 5)]
             [row col z])
           (cycle (flatten [(repeat 12 :space) :down (repeat 12 :space)])))
   (zipmap (map (fn [[row col]] [row col 0]) (keys level0))
           (vals (assoc level0 [2 2] :down)))))

(defn space3d-level
  [space z]
  (let [coords (for [row (range 5) col (range 5)] [row col])]
    (zipmap coords (map (fn [[row col]] (space [row col z])) coords))))

(defn conway-rule-3d
  [space pos]
  (conway-rule space pos neighbors3d))

(defn conway-step3d
  [space]
  (conway-step space conway-rule-3d))

(defn simulate
  [level0 steps]
  (let [depth (quot steps 2)
        space3d (space3d-init level0 depth)]
    (first (drop steps (iterate conway-step3d space3d)))))

(defn bug-count
  [space3d]
  (u/count-if space3d #(= :bug (val %))))

(defn part1
  [input]
  (find-recurrence input))

(defn part2
  [input]
  (bug-count (simulate input 200)))
