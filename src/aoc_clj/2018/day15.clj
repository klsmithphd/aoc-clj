(ns aoc-clj.2018.day15
  "Solution to https://adventofcode.com/2018/day/15"
  (:require [aoc-clj.utils.grid.mapgrid :as mg]
            [aoc-clj.utils.grid :as grid]))

;; Constants
(def init-hp 200)
(def attack-power 3)

;; Input parsing
(def charmap
  {\# :wall
   \. :open
   \E :elf
   \G :goblin})

(defn unit?
  [[_ v]]
  (or (= :elf v) (= :goblin v)))

(defn init-unit
  [[k v]]
  [k {:type v :hp init-hp}])

(defn reading-compare
  [[x1 y1] [x2 y2]]
  (if (zero? (compare y1 y2))
    (compare x1 x2)
    (compare y1 y2)))

(defn reading-order
  [units]
  (sort-by #(:pos (val %)) reading-compare units))

(defn parse
  [input]
  (let [grid (:grid (mg/ascii->MapGrid2D charmap input :down true))]
    {:walls (->> (filter #(= :wall (val %)) grid)
                 keys
                 set)
     :units (->> (filter unit? grid)
                 (map init-unit)
                 (into (sorted-map-by reading-compare)))}))

;; Puzzle logic
(def enemy
  {:goblin :elf
   :elf :goblin})

(defn enemies
  [units {:keys [type]}]
  (filter #(= (enemy type) (:type %)) units))

(defn in-range-enemies
  "Returns true if the selected unit is in attack range of any of its
   enemies"
  [{:keys [units]} [pos {:keys [type]}]]
  (->> (grid/adj-coords-2d pos)
       (filter #(= (enemy type) (:type (units %))))))

;; To be implemented
(defn attack
  [board unit]
  (if-let [target (->> (in-range-enemies board unit)
                       (sort-by #(:hp (val %)) <)
                       (partition-by #(:hp (val %)))
                       first
                       reading-order
                       first)]
    (let [{:keys [pos hp]} target
          new-hp (- hp attack-power)]
      (if (<= new-hp 0)
        (update board :units dissoc pos)
        (assoc-in board [:units pos :hp] new-hp)))
    board))

;; To be implemented
(defn move
  [board unit]
  board)

(defn turn
  "Return the updated state of the game board based on allowing the
   selected unit to take its turn"
  [board unit]
  (let [in-range  (in-range-enemies board unit)
        new-board (if (empty? in-range)
                    (move board unit)
                    board)]
    (attack new-board unit)))