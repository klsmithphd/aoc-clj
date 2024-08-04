(ns aoc-clj.2016.day22
  "Solution to https://adventofcode.com/2016/day/22")

;; Input parsing
(defn parse-line
  [line]
  (let [[x y size used avail usepct] (map read-string (re-seq #"\d+" line))]
    {:pos [x y]
     :size size
     :used used
     :avail avail
     :usepct usepct}))

(defn parse
  [input]
  (map parse-line (drop 2 input)))