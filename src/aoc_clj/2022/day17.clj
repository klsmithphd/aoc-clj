(ns aoc-clj.2022.day17
  "Solution to https://adventofcode.com/2022/day/17"
  (:require [aoc-clj.utils.math :as math]))

;;;; Constants

(def shapes
  "Each rock appears so that its left edge is two units away from the left wall"
  [;; horizontal line ####
   {:left 2 :right 5 :bottom 4 :falling? true
    :cells [[2 4] [3 4] [4 4] [5 4]]}
   ;;          #
   ;;  plus   ###
   ;;          #
   {:left 2 :right 4 :bottom 4 :falling? true
    :cells [[3 4] [2 5] [3 5] [4 5] [3 6]]}
   ;;           #
   ;;   ell     #
   ;;         ###
   {:left 2 :right 4 :bottom 4 :falling? true
    :cells [[2 4] [3 4] [4 4] [4 5] [4 6]]}
   ;; vertical line
   {:left 2 :right 2 :bottom 4 :falling? true
    :cells [[2 4] [2 5] [2 6] [2 7]]}
   ;; square
   {:left 2 :right 3 :bottom 4 :falling? true
    :cells [[2 4] [3 4] [2 5] [3 5]]}])

;;;; Input parsing

(def parse first)

;;;; Puzzle logic

(defn shift-right [pos]   (update pos 0 inc))
(defn shift-left  [pos]   (update pos 0 dec))
(defn shift-down  [pos]   (update pos 1 dec))
(defn shift-up    [h pos] (update pos 1 + h))

(defn push-right
  "Move the falling shape one unit to the right, if possible"
  [rocks {:keys [left right cells] :as shape}]
  (if (= 6 right)
    shape
    (let [newcells (mapv shift-right cells)]
      (if (some rocks newcells)
        shape
        (assoc shape
               :cells newcells
               :right (inc right)
               :left  (inc left))))))

(defn push-left
  "Move the falling shape one unit to the left, if possible"
  [rocks {:keys [left right cells] :as shape}]
  (if (= 0 left)
    shape
    (let [newcells (mapv shift-left cells)]
      (if (some rocks newcells)
        shape
        (assoc shape
               :cells newcells
               :right (dec right)
               :left  (dec left))))))

(defn move-down
  "Move the falling shape down one unit, if possible. If not possible
   (because of an obstacle), update the state to mark that the shape
   is no longer falling"
  [rocks {:keys [bottom cells] :as shape}]
  (if (= 1 bottom)
    (assoc shape :falling? false)
    (let [newcells (mapv shift-down cells)]
      (if (some rocks newcells)
        (assoc shape :falling? false)
        (assoc shape
               :cells newcells
               :bottom (dec bottom))))))

(defn tower-height
  "Compute the current maximum height of the tower"
  [cells]
  (apply max (map second cells)))

(defn init-shape
  "Position the shape at its initial location given the current 
   height of the shapes that have already fallen.
   
   Each rock appears so that its left edge is two units away from the left 
   wall and its bottom edge is three units above the highest rock in the 
   room (or the floor, if there isn't one)"
  [height {:keys [bottom cells] :as shape}]
  (assoc shape
         :bottom (+ bottom height)
         :cells (mapv #(shift-up height %) cells)))

(defn push-move
  "Push the rock left or right, depending upon the direction of the jet"
  [rocks shape jet]
  (case jet
    \> (push-right rocks shape)
    \< (push-left rocks shape)))

(defn move
  "Move the rock according to the current jet"
  [{:keys [rocks shape jets jet-idx] :as state}]
  (let [newshape (push-move rocks shape (get jets jet-idx))]
    (assoc state
           :jet-idx (math/mod-add (count jets) jet-idx 1)
           :shape   (move-down rocks newshape))))

(defn deposit-shape
  "Deposit one new rock, moving it horizontally according to the jets
   until it comes to rest.
   
   After a rock appears, it alternates between being pushed by a jet of hot 
   gas one unit (in the direction indicated by the next symbol in the jet 
   pattern) and then falling one unit down. If any movement would cause any 
   part of the rock to move into the walls, floor, or a stopped rock, the 
   movement instead does not occur. If a downward movement would have caused 
   a falling rock to move into the floor or an already-fallen rock, the falling 
   rock stops where it is (having landed on something) and a new rock 
   immediately begins falling."
  [{:keys [jets rocks height shape-idx jet-idx] :as state}]
  (loop [shape-state {:rocks rocks
                      :shape (init-shape height (get shapes shape-idx))
                      :jets jets
                      :jet-idx jet-idx}]
    (if (not (get-in shape-state [:shape :falling?]))
      (-> state
          (assoc :rocks (into rocks (get-in shape-state [:shape :cells])))
          (assoc :height (max height
                              (tower-height (get-in shape-state [:shape :cells]))))
          (update :shape-idx #(math/mod-add 5 % 1))
          (assoc :jet-idx (get shape-state :jet-idx))
          (update :shape-count inc))
      (recur (move shape-state)))))

(defn init-state
  "Initialize the state map given the jets (our puzzle input)"
  [jets]
  {:jets jets
   :rocks #{}
   :height 0
   :shape-idx 0
   :jet-idx 0
   :shape-count 0
   :seen {}
   :repeat-height 0})

(defn simulate
  "Continue to deposit rocks until n have fallen"
  [jets n]
  (->> (init-state jets)
       (iterate deposit-shape)
       (drop n)
       first))

(defn short-circuit
  "When a recurrence pattern has been discovered, keep track of 
   how much we can skip simulating, i.e. how much height will be added
   by the repeats and how many rocks will fall"
  [state n old-h h old-s-cnt s-count]
  (let [h-delta (- h old-h)
        s-delta (- s-count old-s-cnt)
        repeats (quot (- n s-count) s-delta)
        repeat-height (* h-delta repeats)]
    (-> state
        (update :repeat-height + repeat-height)
        (update :shape-count + (* s-delta repeats)))))

(defn recurrence-tracker
  "Keep track of seeing a shape and jet-index combo. When we have
   seen the same combination for a third time, we can then shortcut
   the remainder of most of the simulation and jump to just the very
   end"
  [n {:keys [shape-idx jet-idx height shape-count seen] :as state}]
  (let [k [shape-idx jet-idx]
        [recur-count old-height old-shape-count] (get seen k [0 0 0])
        new-state (if (= 2 recur-count)
                    (short-circuit state n old-height height old-shape-count shape-count)
                    state)]
    (assoc-in new-state [:seen k] [(inc recur-count) height shape-count])))

(defn smart-simulate
  "Similar to `simulate`, this function simulates depositing rocks until
   n have fallen, but this implementation uses additional book-keeping
   to skip large swaths of simulation once a recurrence pattern has been
   discovered"
  [jets n]
  (let [update-step (comp (partial recurrence-tracker n) deposit-shape)]
    (loop [state (init-state jets)]
      (if (= n (get state :shape-count))
        state
        (recur (update-step state))))))

(defn tower-height-after-n
  "Given the jets as input, determine how high the rock tower is after
   n rocks have fallen."
  [input n]
  (let [{:keys [height repeat-height]} (smart-simulate input n)]
    (+ height repeat-height)))

;;;; Puzzle solutions

(defn day17-part1-soln
  "How many units tall will the tower of rocks be after 2022 rocks have 
   stopped falling?"
  [input]
  (tower-height-after-n input 2022))

(defn day17-part2-soln
  "How tall will the tower be after 1000000000000 rocks have stopped?"
  [input]
  (tower-height-after-n input 1000000000000))