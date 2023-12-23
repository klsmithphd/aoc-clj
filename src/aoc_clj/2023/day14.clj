(ns aoc-clj.2023.day14
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.grid :refer [width height]]
            [aoc-clj.utils.grid.vecgrid :as vg]))

(def charmap
  {\O :r ;; rolling rock
   \. :s ;; space
   \# :w ;; wall --- fixed, square rock
   })

(defn parse
  [input]
  (vg/ascii->VecGrid2D charmap input :down true))

(defn rev-compare
  [a b]
  (compare b a))

(defn roll-row
  "Allows the round rocks denoted by \\O to roll in the direction dictated
   by `dir`, with 0 meaning rocks roll east (to the right) and any other
   value (typically 1) meaning roll west (to the left)"
  [dir row]
  (let [sort-fn (if (zero? dir) sort (partial sort rev-compare))]
    (->> row
         (partition-by #{:w})
         (map sort-fn)
         flatten)))

(def roll-east  #(map (partial roll-row 1) %))
(def roll-west  #(map (partial roll-row 0) %))
(def roll-south #(map (partial roll-row 1) %))
(def roll-north #(map (partial roll-row 0) %))

(defn roll-north-solo
  "Roll the round rocks toward the north (top) end of the grid"
  [{:keys [v]}]
  (-> v u/transpose roll-north u/transpose vg/->VecGrid2D))

(defn spin-cycle
  "Each cycle tilts the platform four times so that the rounded rocks roll 
   north, then west, then south, then east. After each tilt, 
   the rounded rocks roll as far as they can before the platform 
   tilts in the next direction."
  [{:keys [v]}]
  (->> v
       u/transpose roll-north u/transpose
       roll-west
       u/transpose roll-south u/transpose
       roll-east
       vg/->VecGrid2D))

(defn round-rock-positions
  [{:keys [v] :as grid}]
  (let [h (height grid)
        w (width grid)
        values (zipmap (for [y (range h) x (range w)] [x y])
                       (flatten v))]
    (set (keys (filter #(= :r (val %)) values)))))

(defn cycle-params
  [{:keys [state-set states idx]} state]
  (if (state-set state)
    (let [start (u/index-of (u/equals? state) states)]
      (reduced [start (- idx start)]))
    {:state-set (conj state-set state)
     :states    (conj states state)
     :idx       (inc idx)}))

(defn spin-cycle-params
  [grid]
  (->> (iterate spin-cycle grid)
       (map round-rock-positions)
       (reduce cycle-params {:state-set #{} :states [] :idx 0})))

;; (defn total-load
;;   "The amount of load caused by a single rounded rock (O) is equal to the 
;;    number of rows from the rock to the south edge of the platform, 
;;    including the row the rock is on.

;;    The total load is the sum of the load caused by all of the rounded rocks."
;;   [{:keys [v] :as grid}]
;;   (let [h (height grid)]
;;     (->> (map #(count (filter #{:r} %)) v)
;;          (map * (range h 0 -1))
;;          (reduce +))))

(defn total-load
  "The amount of load caused by a single rounded rock (O) is equal to the 
   number of rows from the rock to the south edge of the platform, 
   including the row the rock is on.
   
   The total load is the sum of the load caused by all of the rounded rocks."
  [{:keys [v]}]
  (let [height (count v)]
    (reduce + (map-indexed (fn [idx row]
                             (* (- height idx)
                                (count (filter #{:r} row)))) v))))


(defn total-load-after-n-spin-cycles
  [grid n]
  (let [[start length] (spin-cycle-params grid)]
    (if (< n start)
      (total-load (nth (iterate spin-cycle grid) n))
      (total-load (nth (iterate spin-cycle grid) (+ start (mod (- n start) length)))))))

(defn day14-part1-soln
  "Tilt the platform so that the rounded rocks all roll north. 
   Afterward, what is the total load on the north support beams?"
  [input]
  (total-load (roll-north-solo input)))

(defn day14-part2-soln
  "Run the spin cycle for 1000000000 cycles. 
   Afterward, what is the total load on the north support beams?"
  [input]
  (total-load-after-n-spin-cycles input 1000000000))