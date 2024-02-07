(ns aoc-clj.2015.day18
  "Solution to https://adventofcode.com/2015/day/18"
  (:require [clojure.set :as set]
            [aoc-clj.utils.grid :as grid]))

;; Constants
(def iterations 100)

;; Input parsing
(def grid
  "Returns a list of all NxN [x y] position vecs in a grid of size N"
  (memoize
   (fn [size]
     (for [y (range size) x (range size)] [x y]))))

(defn parse
  [input]
  (let [size   (count input)
        lights (->> (grid size)
                    (filter #(= \# (get-in (vec input) (vec (reverse %)))))
                    set)]
    [size lights]))

;; Puzzle logic
(def neighbors
  (memoize
   (fn [pos]
     (grid/adj-coords-2d pos :include-diagonals true))))

(defn on-neighbors
  [lights pos]
  (->> (neighbors pos) (filter lights) count))

(def corners
  (memoize
   (fn [size]
     #{[0 0] [(dec size) 0] [0 (dec size)] [(dec size) (dec size)]})))

(defn corners-on
  [[size lights]]
  [size (set/union (corners size) lights)])

(defn on-condition
  [lights pos]
  (if (lights pos)
    (<= 2 (on-neighbors lights pos) 3)
    (== 3 (on-neighbors lights pos))))

(defn step
  ([state]
   (step state false))

  ([[size lights] corners?]
   (let [new-lights (->> (grid size)
                         (filter #(on-condition lights %))
                         set)
         new-state [size new-lights]]
     (if corners?
       (corners-on new-state)
       new-state))))

(defn lights-at-n
  "Returns the number of lights that are on as of iteration `n`

   If `corners?` is set to `true`, the four corners will be forced to be
   in an `on` state at all times."
  ([state n]
   (lights-at-n state n false))
  ([state n corners?]
   (-> (iterate #(step % corners?) state) (nth n) second count)))

;; ;; Puzzle solutions
(defn part1
  "How many lights are on after 100 steps"
  [input]
  (lights-at-n input iterations))

(defn part2
  "How many lights are on after 100 steps when the corners are kept always on"
  [input]
  (lights-at-n (corners-on input) iterations true))


;; (defn on-set
;;   [input]
;;   (let [height (count input)
;;         width  (count (first input))]
;;     (->>
;;      (for [y (u/rev-range height)
;;            x (range width)]
;;        (when (= \# (get-in input [y x])) [x y]))
;;      (filter some?)
;;      set)))

;; (def neighbor-positions
;;   (memoize
;;    (fn [n]
;;      (for [y (range n)
;;            x (range n)]
;;        (grid/adj-coords-2d [x y] :include-diagonals :true)))))


;; (defn corners-on
;;   "Force set the four corners to an on position"
;;   [grid]
;;   (let [x (dec (height grid))]
;;     (-> grid
;;         (assoc-in [:v 0 0] :on)
;;         (assoc-in [:v x 0] :on)
;;         (assoc-in [:v 0 x] :on)
;;         (assoc-in [:v x x] :on))))

;; (defn next-light-value
;;   "Computes the value of the light for the next step.

;;    A light which is on stays on when 2 or 3 neighbors are on, turns off otherwise.
;;    A light which is off turns on if 3 neighbors are on, stays off otherwise."
;;   [light on-neighbors]
;;   (case light
;;     :on  (if (<= 2 on-neighbors 3) :on :off)
;;     :off (if (= 3 on-neighbors)    :on :off)))

;; (defn update-lights
;;   "Update the light value in the grid at the given `position`, based
;;    on current neighbor values"
;;   [grid pos]
;;   (let [light (value grid pos)
;;         on-neighbors (->> (grid/neighbor-data grid pos :diagonals true)
;;                           (map :val)
;;                           (filter (u/equals? :on))
;;                           count)]
;;     (next-light-value light on-neighbors)))

;; (defn step
;;   "Update all lights in the grid based on their neighbors."
;;   ([grid]
;;    (step false grid))
;;   ([corners-on? grid]
;;    (let [new-data (->> (pos-seq grid)
;;                        (map #(update-lights grid %))
;;                        (partition (height grid))
;;                        (mapv vec)
;;                        vg/->VecGrid2D)]
;;      (if corners-on?
;;        (corners-on new-data)
;;        new-data))))

;; (defn lights-on-at-step-n
;;   "Evolve the light grid to the nth step"
;;   ([n grid]
;;    (lights-on-at-step-n false n grid))
;;   ([corners-on? n grid]
;;    (->>  (iterate (partial step corners-on?) grid)
;;          (drop n)
;;          first
;;          val-seq
;;          (filter (u/equals? :on))
;;          count)))