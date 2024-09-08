(ns aoc-clj.2017.day07
  "Solution to https://adventofcode.com/2017/day/7"
  (:require [clojure.set :as set]))

;; Input parsing
(defn parse-line
  [line]
  (let [prgrms (re-seq #"[a-z]+" line)
        weight (read-string (re-find #"\d+" line))]
    [(first prgrms) {:weight weight :children (rest prgrms)}]))

(defn parse
  [input]
  (into {} (map parse-line input)))

;; Puzzle logic
(defn root-node
  [nodes]
  (let [parents (filter #(seq (:children (val %))) nodes)
        children (into #{} (mapcat :children (vals parents)))]
    (first (remove children (keys parents)))))

;; Puzzle solutions
(defn part1
  [input]
  (root-node input))