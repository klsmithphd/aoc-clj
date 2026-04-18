(ns aoc-clj.2019.day11
  "Solution to https://adventofcode.com/2019/day/11"
  (:require [manifold.stream :as s]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.grid :as grid]
            [aoc-clj.utils.grid.vecgrid :refer [->VecGrid2D]]
            [aoc-clj.utils.intcode :as intcode]))

(def parse u/firstv)

(defn turn-and-move
  [{:keys [heading pos] :as state} bearing]
  (-> state
      (grid/turn bearing)
      (grid/forward 1 :y-up)))

(defn move-and-paint
  [{:keys [hull pos] :as state} paint turn-cmd]
  (let [bearing (case turn-cmd 0 :left 1 :right)
        new-state (turn-and-move state bearing)]
    (assoc new-state :hull (assoc hull pos paint))))

(defn robot-step
  [in out {:keys [hull pos] :as state}]
  (let [_ (s/put! in (get hull pos 0))
        paint @(s/take! out)
        turn  @(s/take! out)]
    (if (and paint turn)
      (move-and-paint state paint turn)
      state)))

(defn paint-bot
  [intcode start-panel]
  (let [in (s/stream)
        out (s/stream)
        stepper (partial robot-step in out)
        program (future (intcode/intcode-exec intcode in out))]
    (loop [state {:hull {[0,0] start-panel} :pos [0,0] :heading :n}]
      (if (realized? program)
        state
        (recur (stepper state))))))

(defn part1
  [input]
  (count (keys (:hull (paint-bot input 0)))))

(defn- derive-part2
  [input]
  (->> (paint-bot input 1)
       :hull
       grid/mapgrid->vectors
       (->VecGrid2D :y-up)
       (grid/Grid2D->ascii {\  0 \* 1})
       print))

(defn part2
  [input]
  (comment
    (derive-part2 input)
    "Prints out:
      **** *    **** ***  *  *   ** ***   **    
         * *    *    *  * * *     * *  * *  *   
        *  *    ***  ***  **      * *  * *  *   
       *   *    *    *  * * *     * ***  ****   
      *    *    *    *  * * *  *  * * *  *  *   
      **** **** **** ***  *  *  **  *  * *  *   ")
  "ZLEBKJRA")
