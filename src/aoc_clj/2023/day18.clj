(ns aoc-clj.2023.day18
  (:require [clojure.string :as str]))

(defn parse-line
  [line]
  (let [[a b c] (str/split line #" ")]
    {:dir a
     :dist (read-string b)
     :color (subs c 1 (dec (count c)))}))

(defn parse
  [input]
  (map parse-line input))