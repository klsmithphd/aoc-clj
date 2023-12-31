(ns aoc-clj.2019.day11
  (:require [manifold.stream :as s]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.grid :as grid]
            [aoc-clj.utils.grid.vecgrid :refer [->VecGrid2D]]
            [aoc-clj.utils.intcode :as intcode]))

(def day11-input (u/firstv (u/puzzle-input "inputs/2019/day11-input.txt")))

(defn turn-left-and-move
  [{:keys [direction] [x y] :position}]
  (case direction
    :up    {:position [(dec x) y] :direction :left}
    :left  {:position [x (dec y)] :direction :down}
    :down  {:position [(inc x) y] :direction :right}
    :right {:position [x (inc y)] :direction :up}))

(defn turn-right-and-move
  [{:keys [direction] [x y] :position}]
  (case direction
    :up    {:position [(inc x) y] :direction :right}
    :right {:position [x (dec y)] :direction :down}
    :down  {:position [(dec x) y] :direction :left}
    :left  {:position [x (inc y)] :direction :up}))

(defn move-and-paint
  [{:keys [hull position] :as state} paint turn]
  (assoc (case turn
           0 (turn-left-and-move state)
           1 (turn-right-and-move state))
         :hull (assoc hull position paint)))

(defn robot-step
  [in out {:keys [hull position] :as state}]
  (let [_ (s/put! in (get hull position 0))
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
    (loop [state {:hull {[0,0] start-panel} :position [0,0] :direction :up}]
      (if (realized? program)
        state
        (recur (stepper state))))))

(defn day11-part1-soln
  []
  (count (keys (:hull (paint-bot day11-input 0)))))

(defn- derive-day11-part2-soln
  []
  (->> (paint-bot day11-input 1)
       :hull
       grid/mapgrid->vectors
       ->VecGrid2D
       (grid/Grid2D->ascii {\  0 \* 1})
       print))

(comment
  (derive-day11-part2-soln)
  "Prints out:
    **** *    **** ***  *  *   ** ***   **    
       * *    *    *  * * *     * *  * *  *   
      *  *    ***  ***  **      * *  * *  *   
     *   *    *    *  * * *     * ***  ****   
    *    *    *    *  * * *  *  * * *  *  *   
    **** **** **** ***  *  *  **  *  * *  *   ")

(defn day11-part2-soln
  []
  "ZLEBKJRA")
