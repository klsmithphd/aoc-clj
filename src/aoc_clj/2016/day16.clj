(ns aoc-clj.2016.day16
  "Solution to https://adventofcode.com/2016/day/16"
  (:require [clojure.string :as str]))

;; Input parsing
(def parse first)

;; Puzzle logic
(defn flip
  [s]
  (map {\1 \0 \0 \1} s))

(defn fill
  [s]
  (concat s [\0] (flip (reverse s))))

(def checksum-mapping
  {[\1 \1] \1
   [\0 \0] \1
   [\0 \1] \0
   [\1 \0] \0})

(defn checksum-pass
  [s]
  (map checksum-mapping (partition 2 s)))

(defn checksum
  [s]
  (let [cs (checksum-pass s)]
    (if (odd? (count cs))
      (str/join cs)
      (checksum cs))))