(ns aoc-clj.2024.day06
  "Solution to https://adventofcode.com/2024/day/6"
  (:require [aoc-clj.utils.grid :as grid :refer [value]]
            [aoc-clj.utils.grid.mapgrid :as mg]))

;; Input parsing
(def charmap {\. nil \# :wall \^ :guard})

(defn parse
  [input]
  (mg/ascii->MapGrid2D charmap input))

;; Puzzle logic
(defn guard-start
  "Returns the starting state of the guard given the grid"
  [{:keys [grid]}]
  {:heading :n
   :pos (ffirst (filter #(= :guard (val %)) grid))
   :visited #{}})

(defn guard-state
  "Returns the current state of the guard, which is just the submap
   of data including the guard's current position and heading"
  [guard]
  (select-keys guard [:pos :heading]))

(defn next-move
  "Returns an updated guard state after making one move."
  [grid guard]
  (let [ahead     (grid/forward guard 1)
        new-guard (if (= :wall (value grid (:pos ahead)))
                    (next-move grid (grid/turn guard :right))
                    ahead)]
    (update new-guard :visited conj (guard-state guard))))

(defn new-state?
  "Returns true if the current location hasn't been previously encountered"
  [{:keys [visited] :as guard}]
  (not (visited (guard-state guard))))

(defn guard-path
  "Returns the path that the guard will follow"
  [grid]
  (->> (guard-start grid)
       (iterate #(next-move grid %))
       (take-while new-state?)
       (take-while #(grid/in-grid? grid (:pos %)))))

(defn will-loop-with-obstruction?
  "Identifies whether adding an obstruction at `obs` will
   cause the guard to enter a loop"
  [grid obs]
  (let [new-grid (assoc-in grid [:grid obs] :wall)
        end      (last (guard-path new-grid))]
    (not (new-state? (next-move new-grid end)))))

(defn looping-guard-paths
  "Returns the count of the number of obstructions that can be placed
   in the guard's path in order to cause them to loop forever"
  [grid]
  (let [start #{(:pos (guard-start grid))}
        orig-path (->> (guard-path grid)
                       (map :pos)
                       set
                       (remove start))]
    (->> orig-path
         (filter #(will-loop-with-obstruction? grid %))
         count)))

(defn distinct-guard-positions
  "Returns the count of the unique positions that the guard visited"
  [grid]
  (->> (guard-path grid)
       (map :pos)
       set
       count))

;; Puzzle solutions
(defn part1
  "How many distinct positions will the guard visit before leaving the
   mapped area?"
  [input]
  (distinct-guard-positions input))

(defn part2
  "You need to get the guard stuck in a loop by adding a single new
   obstruction. How many different positions could you choose for this
   obstruction?"
  [input]
  (looping-guard-paths input))