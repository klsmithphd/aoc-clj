(ns aoc-clj.2023.day24)

(defn parse-line
  [line]
  (let [[a b c d e f] (map read-string (re-seq #"\-?\d+" line))]
    [[a b c] [d e f]]))

(defn parse
  [input]
  (map parse-line input))