(ns aoc-clj.2022.day17
  "Solution to https://adventofcode.com/2022/day/17"
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.math :as math]))

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

(def d17-s01 ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>")

(def day17-input (first (u/puzzle-input "2022/day17-input.txt")))

(defn shift-right [pos]   (update pos 0 inc))
(defn shift-left  [pos]   (update pos 0 dec))
(defn shift-down  [pos]   (update pos 1 dec))
(defn shift-up    [h pos] (update pos 1 + h))

(defn push-right
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
  [cells]
  (apply max (map second cells)))

(defn init-shape
  [height {:keys [bottom cells] :as shape}]
  (assoc shape
         :bottom (+ bottom height)
         :cells (mapv #(shift-up height %) cells)))

(defn push-move
  [rocks shape jet]
  (case jet
    \> (push-right rocks shape)
    \< (push-left rocks shape)))

(defn move
  [{:keys [rocks shape jets jet-idx] :as state}]
  (let [newshape (push-move rocks shape (get jets jet-idx))]
    (assoc state
           :jet-idx (math/mod-add (count jets) jet-idx 1)
           :shape   (move-down rocks newshape))))

(defn deposit-shape
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
  [jets n]
  (->> (init-state jets)
       (iterate deposit-shape)
       (drop n)
       first))

(defn short-circuit
  [state n old-h h old-s-cnt s-count]
  (let [h-delta (- h old-h)
        s-delta (- s-count old-s-cnt)
        repeats (quot (- n s-count) s-delta)
        repeat-height (* h-delta repeats)]
    (-> state
        (update :repeat-height + repeat-height)
        (update :shape-count + (* s-delta repeats)))))

(defn recurrence-tracker
  [n {:keys [shape-idx jet-idx height shape-count seen] :as state}]
  (let [k [shape-idx jet-idx]
        [recur-count old-height old-shape-count] (get seen k [0 0 0])
        new-state (if (= 2 recur-count)
                    (short-circuit state n old-height height old-shape-count shape-count)
                    state)]
    (assoc-in new-state [:seen k] [(inc recur-count) height shape-count])))

(defn smart-simulate
  [jets n]
  (let [update-step (comp (partial recurrence-tracker n) deposit-shape)]
    (loop [state (init-state jets)]
      (if (= n (get state :shape-count))
        state
        (recur (update-step state))))))

;; (defn tower-height-after-n
;;   [input n]
;;   (->> (simulate input n)
;;        :height))

(defn tower-height-after-n
  [input n]
  (let [{:keys [height repeat-height]} (smart-simulate input n)]
    (+ height repeat-height)))

(tower-height-after-n d17-s01 1000000000000)

(defn day17-part1-soln
  "How many units tall will the tower of rocks be after 2022 rocks have 
   stopped falling?"
  []
  (tower-height-after-n day17-input 2022))

(defn day17-part2-soln
  []
  (tower-height-after-n day17-input 1000000000000))