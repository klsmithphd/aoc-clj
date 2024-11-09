(ns aoc-clj.2018.day07
  "Solution to https://adventofcode.com/2018/day/7"
  (:require [clojure.set :as set]
            [aoc-clj.utils.core :as u]))

(defn parse-line
  [line]
  (reverse (map second (re-seq #"tep ([A-Z])" line))))

(defn parse
  [input]
  (let [deps (->> (map parse-line input)
                  (group-by first)
                  (u/fmap #(set (map second %))))
        upstream  (set (apply concat (vals deps)))
        to-add    (->> (set/difference upstream (set (keys deps)))
                       (map #(vector % #{})))]
    (into deps to-add)))