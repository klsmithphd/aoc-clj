(ns aoc-clj.2022.day14
 "Solution to https://adventofcode.com/2022/day/14"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(defn parse-line
  [line]
  (mapv #(read-string (str "[" % "]")) (str/split line #" \-\> ")))

(defn points
  [[[x1 y1] [x2 y2]]]
  (let [dir-y (if (<= y1 y2) +1 -1)
        dir-x (if (<= x1 x2) +1 -1)]
    (for [y (range y1 (+ y2 dir-y) dir-y)
          x (range x1 (+ x2 dir-x) dir-x)]
      [x y])))

(defn trace-lines
  [line]
  (concat (mapcat points (partition 2 1 line))))

(defn rocks
  [input]
  (zipmap
   (distinct (mapcat trace-lines input))
   (repeat :rock)))

(defn parse
  [input]
  (rocks (map parse-line input)))

(def d14-s01
  (parse
   ["498,4 -> 498,6 -> 496,6"
    "503,4 -> 502,4 -> 502,9 -> 494,9"]))

(def day14-input (parse (u/puzzle-input "2022/day14-input.txt")))

(defn open?
  [grid pos]
  ((complement #{:rock :sand}) (get grid pos :air)))

(defn move
  [grid [x y]] 
  (let [moves [;; straight down
               [x (inc y)]
               ;; down to the left
               [(dec x) (inc y)]
               ;; down to the right
               [(inc x) (inc y)]]]
    (first (filter #(open? grid %) moves))))

(defn deposit-sand-grain
  [grid]
  (loop [pos [500 0]]
    (if (nil? (move grid pos))
      (assoc grid pos :sand)
      (recur (move grid pos)))))

(defn sand-cells
  [grid]
  (map first (filter #(= :sand (val %)) grid)))

(sand-cells
 (deposit-sand-grain
  (deposit-sand-grain (rocks d14-s01))))

