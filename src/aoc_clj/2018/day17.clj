(ns aoc-clj.2018.day17
  "Solution to https://adventofcode.com/2018/day/17")

;; Input parsing
(defn parse-line
  [line]
  (let [nums (map read-string (re-seq #"\d+" line))
        dir  (case (subs line 0 1)
               "x" :v
               "y" :h)]
    (conj nums dir)))

(defn parse
  [input]
  (map parse-line input))

;; Puzzle logic
(defn clay-line
  [[dir fixed start end]]
  (for [changing (range start (inc end))]
    (case dir
      :v [fixed changing]
      :h [changing fixed])))

(defn all-clay
  [veins]
  (->> veins
       (mapcat clay-line)
       set))