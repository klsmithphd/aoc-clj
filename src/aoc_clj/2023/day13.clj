(ns aoc-clj.2023.day13
  (:require [aoc-clj.utils.core :as u]))

(defn parse
  [input]
  (u/split-at-blankline input))

(defn differences
  [a b]
  (if (= a b) 0 1))

(defn mirror-test
  [idx [a b]]
  (let [size-a (count a)
        size-b (count b)]
    (if (< size-a size-b)
      [idx (reduce + (map differences
                          (apply str a)
                          (apply str (reverse (take size-a b)))))]
      [idx (reduce + (map differences
                          (apply str (drop (- size-a size-b) a))
                          (apply str (reverse b))))])))

(defn test-splits
  [rows]
  (->> (range 1 (count rows))
       (map #(split-at % rows))
       (map-indexed mirror-test)))

(defn vert-mirror
  [diffs rows]
  (->> (test-splits rows)
       (filter (comp #(= diffs %) second))
       (ffirst)))

(defn mirror-pos
  [diffs rows]
  (let [h-mirror (vert-mirror diffs rows)
        v-mirror (vert-mirror diffs (mapv #(apply str %) (u/transpose rows)))]
    (if (nil? v-mirror)
      {:type :horizontal :pos (inc h-mirror)}
      {:type :vertical :pos (inc v-mirror)})))

(defn summarize-math
  [{:keys [type pos]}]
  (case type
    :vertical pos
    :horizontal (* 100 pos)))

(defn summarize
  [diffs input]
  (->> input
       (map (partial mirror-pos diffs))
       (map summarize-math)
       (reduce +)))

(defn day13-part1-soln
  [input]
  (summarize 0 input))

(defn day13-part2-soln
  [input]
  (summarize 1 input))