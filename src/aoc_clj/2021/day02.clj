(ns aoc-clj.2021.day02
  "Solution to https://adventofcode.com/2021/day/2"
  (:require [clojure.string :as str]))

(defn parse-line
  [line]
  (let [[cmd val] (str/split line #" ")]
    [(keyword cmd) (read-string val)]))

(defn parse
  [input]
  (map parse-line input))

(defn part1-rules
  [[pos depth] [cmd val]]
  (case cmd
    :forward [(+ pos val) depth]
    :down    [pos (+ depth val)]
    :up      [pos (- depth val)]))

(defn part2-rules
  [[pos depth aim] [cmd val]]
  (case cmd
    :forward [(+ pos val) (+ depth (* aim val)) aim]
    :down    [pos depth (+ aim val)]
    :up      [pos depth (- aim val)]))

(defn sub-end-state
  [cmds rules]
  (reduce rules [0 0 0] cmds))

(defn part1
  [input]
  (reduce * (sub-end-state input part1-rules)))

(defn part2
  [input]
  (reduce * (drop-last (sub-end-state input part2-rules))))