(ns aoc-clj.2021.day20
  "Solution to https://adventofcode.com/2021/day/20"
  (:require [aoc-clj.utils.grid.mapgrid :as mapgrid]
            [aoc-clj.utils.core :as u]))

(def char-map {\. 0 \# 1})

(defn parse
  [input]
  (let [[part1 part2] (u/split-at-blankline input)]
    {:algorithm (mapv char-map (first part1))
     :image (mapgrid/ascii->MapGrid2D char-map part2 :down true)}))

;; TODO variant of adj-coords. Consider consolidating
(defn three-by-cell
  [[x y]]
  (for [ny (range (dec y) (+ y 2))
        nx (range (dec x) (+ x 2))]
    [nx ny]))

;; TODO make this a utility fn
(defn binstr->int
  [s]
  (Integer/parseInt s 2))

(defn next-pixel
  [{:keys [algorithm image field]} pos]
  (->> (three-by-cell pos)
       (map #(get-in image [:grid %] field))
       (apply str)
       binstr->int
       (get algorithm)))

(defn enhance
  [{:keys [image algorithm field] :as input}]
  (let [{:keys [width height]} image
        coords (for [y (range -1 (+ height 1))
                     x (range -1 (+ width 1))]
                 [x y])
        vals   (map (partial next-pixel input) coords)
        adj-coords (map (fn [[a b]] [(inc a) (inc b)]) coords)]
    (assoc input
           :image {:width (+ 2 width)
                   :height (+ 2 height)
                   :grid (zipmap adj-coords vals)}
           :field (if (zero? field) (get algorithm 0) (get algorithm 511)))))

(defn enhance-n-times
  [input n]
  (let [start (assoc input :field 0)]
    (nth (iterate enhance start) n)))

(defn illuminated
  [{:keys [image]}]
  (->> image
       :grid
       vals
       (filter pos?)
       count))

(defn part1
  [input]
  (illuminated (enhance-n-times input 2)))

(defn part2
  [input]
  (illuminated (enhance-n-times input 50)))
