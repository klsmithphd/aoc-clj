(ns aoc-clj.2022.day17
  "Solution to https://adventofcode.com/2022/day/17"
  (:require [aoc-clj.utils.core :as u]))

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

(def d16-s01 ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>")

(def day17-input (first (u/puzzle-input "2022/day17-input.txt")))

(defn shift-right [[x y]]   [(inc x) y])
(defn shift-left  [[x y]]   [(dec x) y])
(defn shift-down  [[x y]]   [x (dec y)])
(defn shift-up    [h [x y]] [x (+ h y)])

(defn push-right
  [grid {:keys [left right cells] :as shape}]
  (if (= 6 right)
    shape
    (let [newcells (mapv shift-right cells)]
      (if (some (complement nil?) (map grid newcells))
        shape
        (assoc shape
               :cells newcells
               :right (inc right)
               :left  (inc left))))))

(defn push-left
  [grid {:keys [left right cells] :as shape}]
  (if (= 0 left)
    shape
    (let [newcells (mapv shift-left cells)]
      (if (some (complement nil?) (map grid newcells))
        shape
        (assoc shape
               :cells newcells
               :right (dec right)
               :left  (dec left))))))

(defn move-down
  [grid {:keys [bottom cells] :as shape}]
  (if (= 1 bottom)
    (assoc shape :falling? false)
    (let [newcells (mapv shift-down cells)]
      (if (some (complement nil?) (map grid newcells))
        (assoc shape :falling? false)
        (assoc shape
               :cells newcells
               :bottom (dec bottom))))))

(defn tower-height
  [grid]
  (if (keys grid)
    (apply max (map second (keys grid)))
    0))

(defn init-shape
  [grid {:keys [bottom cells] :as shape}]
  (let [height (tower-height grid)]
    (assoc shape
           :bottom (+ bottom height)
           :cells (mapv #(shift-up height %) cells))))

(defn push-move
  [grid shape jet]
  (case jet
    \> (push-right grid shape)
    \< (push-left grid shape)))

(defn move
  [{:keys [grid shape jets] :as state}]
  (let [newshape (push-move grid shape (first jets))]
    (assoc state
           :jets (rest jets)
           :shape (move-down grid newshape))))

(defn deposit-shape
  [[grid shapes jets]]
  (loop [state {:grid grid
                :shape (init-shape grid (first shapes))
                :jets jets}]
    (if (not (get-in state [:shape :falling?]))
      [(into grid (zipmap (get-in state [:shape :cells])
                          (repeat :rock)))
       (rest shapes)
       (get state :jets)]
      (recur (move state)))))

(defn tower-height-after-n
  [input n]
  (->> (iterate deposit-shape [{} (cycle shapes) (cycle input)])
       (drop n)
       ffirst
       tower-height))

(defn day17-part1-soln
  "How many units tall will the tower of rocks be after 2022 rocks have 
   stopped falling?"
  []
  (tower-height-after-n day17-input 2022))