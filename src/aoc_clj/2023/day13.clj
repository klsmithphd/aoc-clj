(ns aoc-clj.2023.day13
  (:require [aoc-clj.utils.core :as u]))

(defn parse
  [input]
  (u/split-at-blankline input))

(defn mirror-test
  [idx [a b]]
  (let [size-a (count a)
        size-b (count b)]
    (if (< size-a size-b)
      [idx (every? identity (map = a (reverse (take size-a b))))]
      [idx (every? identity (map = (drop (- size-a size-b) a) (reverse b)))])))

(defn vert-mirror
  [rows]
  (->> (range 1 (count rows))
       (map #(split-at % rows))
       (map-indexed mirror-test)
       (filter second)
       (ffirst)))

(defn mirror-pos
  [rows]
  (let [h-mirror (vert-mirror rows)
        v-mirror (vert-mirror (u/transpose rows))]
    (if (nil? v-mirror)
      {:type :horizontal :pos (inc h-mirror)}
      {:type :vertical :pos (inc v-mirror)})))

(defn summarize-math
  [{:keys [type pos]}]
  (case type
    :vertical pos
    :horizontal (* 100 pos)))

(defn summarize
  [input]
  (->> input
       (map mirror-pos)
       (map summarize-math)
       (reduce +)))

(defn day13-part1-soln
  [input]
  (summarize input))