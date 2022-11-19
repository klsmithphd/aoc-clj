(ns aoc-clj.2015.day10
  (:require [clojure.string :as str]))

(def day10-input "1113222113")

(defn look-and-say
  [s]
  (let [chunks (partition-by identity s)
        counts (map #(vector (str  (count %)) (str (first %))) chunks)]
    (str/join  (flatten  counts))))

(defn day10-part1-soln
  []
  (count (nth (iterate look-and-say day10-input) 40)))

(defn day10-part2-soln
  []
  (count (nth (iterate look-and-say day10-input) 50)))