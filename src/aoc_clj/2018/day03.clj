(ns aoc-clj.2018.day03
  "Solution to https://adventofcode.com/2018/day/3"
  (:require [clojure.set :as set]
            [clojure.string :as string]))

(defn parse-line
  [line]
  (let [vars (->> (string/split line #"[@\,\:x]")
                  (map string/trim))
        [id x y w h] vars]
    {:id id
     :x (read-string x)
     :y (read-string y)
     :w (read-string w)
     :h (read-string h)}))

(defn parse
  [input]
  (map parse-line input))

(defn swath-grid
  [{:keys [x y w h]}]
  (for [i (range x (+ x w))
        j (range y (+ y h))]
    [i j]))

(defn swath-grid-with-ids
  [{:keys [id x y w h]}]
  (into {}
        (for [i (range x (+ x w))
              j (range y (+ y h))]
          [[i j] [id]])))

(defn overlapping-squares
  [swaths]
  (->> swaths
       (mapcat swath-grid)
       frequencies
       (filter #(> (val %) 1))
       count))

(defn overlapping-swath-ids
  [swaths]
  (->> swaths
       (map swath-grid-with-ids)
       (apply merge-with into)
       vals
       (filter #(> (count %) 1))
       flatten
       (into #{})))

(defn all-swath-ids
  [swaths]
  (into #{} (map :id swaths)))

(defn nonoverlapping-swath
  [swaths]
  (let [all-ids (all-swath-ids swaths)
        overlap-ids (overlapping-swath-ids swaths)]
    (first (set/difference all-ids overlap-ids))))

(defn day03-part1-soln
  [input]
  (overlapping-squares input))

(defn day03-part2-soln
  [input]
  (nonoverlapping-swath input))


