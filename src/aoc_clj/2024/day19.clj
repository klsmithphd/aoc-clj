(ns aoc-clj.2024.day19
  "Solution to https://adventofcode.com/2024/day/19"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

;; Input parsing
(defn parse
  [input]
  (let [[towels patterns] (u/split-at-blankline input)]
    {:towels (str/split (first towels) #", ")
     :patterns patterns}))

;; Puzzle logic
(defn possible-helper?
  [towels pattern]
  (if (empty? pattern)
    [true]
    (if-let [matches (filter #(str/starts-with? pattern %) towels)]
      (->> matches
           (map #(subs pattern (count %)))
           (mapcat #(possible-helper? towels %)))
      [false])))

(defn possible?
  [towels pattern]
  (boolean (not-empty (possible-helper? towels pattern))))

(defn possible-count
  [{:keys [towels patterns]}]
  (->> patterns
       (filter #(possible? towels %))
       count))

;; Puzzle solutions
(defn part1
  [input]
  (possible-count input))


;; r -> ["" b]
;; w -> r
;; b -> ["" r {w [u]}]
;; g -> ["" b]


;; gur, uub, wgwu, rggu, urguur

;; (str/starts-with?)