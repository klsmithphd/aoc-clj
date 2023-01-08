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
      (if (some (complement nil?) (map rocks newcells))
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
      (if (some (complement nil?) (map rocks newcells))
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
      (if (some (complement nil?) (map rocks newcells))
        (assoc shape :falling? false)
        (assoc shape
               :cells newcells
               :bottom (dec bottom))))))

(defn tower-height
  [rocks]
  (if (keys rocks)
    (apply max (map second (keys rocks)))
    0))

(defn init-shape
  [rocks {:keys [bottom cells] :as shape}]
  (let [height (tower-height rocks)]
    (assoc shape
           :bottom (+ bottom height)
           :cells (mapv #(shift-up height %) cells))))

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
  [{:keys [rocks shapes jets shape-idx jet-idx]}]
  (loop [state {:rocks rocks
                :shape (init-shape rocks (get shapes shape-idx))
                :jets jets
                :jet-idx jet-idx}]
    (if (not (get-in state [:shape :falling?]))
      {:rocks (into rocks (zipmap (get-in state [:shape :cells])
                                  (repeat :rock)))
       :shapes shapes
       :jets   jets
       :shape-idx (math/mod-add 5 shape-idx 1)
       :jet-idx   (get-in state [:jet-idx])}
      (recur (move state)))))

(defn simulate
  [jets n]
  (->> {:rocks {} :shapes shapes :jets jets :shape-idx 0 :jet-idx 0}
       (iterate deposit-shape)
       (drop n)
       first))

(defn tower-height-after-n
  [input n]
  (->> (simulate input n)
       :rocks
       tower-height))

(defn day17-part1-soln
  "How many units tall will the tower of rocks be after 2022 rocks have 
   stopped falling?"
  []
  (tower-height-after-n day17-input 2022))