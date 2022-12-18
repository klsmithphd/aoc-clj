(ns aoc-clj.2022.day17
  "Solution to https://adventofcode.com/2022/day/17"
  (:require [aoc-clj.utils.core :as u]))

(def shapes
  "Each rock appears so that its left edge is two units away from the left wall"
  [;; horizontal line ####
   {:left 2 :right 5 :bottom 0 :falling? true
    :cells [[2 0] [3 0] [4 0] [5 0]]}
   ;;          #
   ;;  plus   ###
   ;;          #
   {:left 2 :right 4 :bottom 0 :falling? true
    :cells [[3 0] [2 1] [3 1] [4 1] [3 2]]}
   ;;           #
   ;;   ell     #
   ;;         ###
   {:left 2 :right 4 :bottom 0 :falling? true
    :cells [[2 0] [3 0] [4 0] [4 1] [4 2]]}
   ;; vertical line
   {:left 2 :right 2 :bottom 0 :falling? true
    :cells [[2 0] [2 1] [2 2] [2 3]]}
   ;; square
   {:left 2 :right 3 :bottom 0 :falling? true
    :cells [[2 0] [3 0] [2 1] [3 1]]}])

(def d16-s01 ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>")

(def day17-input (first (u/puzzle-input "2022/day17-input.txt")))

(defn shift-right [[x y]] [(inc x) y])
(defn shift-left  [[x y]] [(dec x) y])
(defn shift-down  [[x y]] [x (dec y)])

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
  (if (= 0 bottom)
    (assoc shape :falling? false)
    (let [newcells (mapv shift-down cells)]
      (if (some (complement nil?) (map grid newcells))
        (assoc shape :falling? false)
        (assoc shape
               :cells newcells
               :bottom (dec bottom))))))

(defn push-move
  [grid jet shape]
  (case jet
    \> (push-right grid shape)
    \< (push-left grid shape)))

(defn move
  [{:keys [grid shape jets] :as state}]
  (let [shifted (push-move grid shape (first jets))]
    (assoc state
           :jets (rest jets)
           :shape (move-down grid shifted))))

(defn deposit-shape
  [grid shape jets]
  (loop [state {:grid grid :shape shape :jets jets}]
    (if (get-in state [:shape :falling])
      [(into grid (zipmap (get-in state [:shape :cells])
                          (repeat :rock)))]
      (recur (move state)))))

