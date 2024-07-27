(ns aoc-clj.2016.day16
  "Solution to https://adventofcode.com/2016/day/16"
  (:require [clojure.string :as str]))

;; Constants
(def first-disk-size 272)
(def second-disk-size 35651584)

;; Input parsing
(def parse first)

;; Puzzle logic
(defn flip
  "Flip a `1` character to a `0` and vice-versa"
  [s]
  (map {\1 \0 \0 \1} s))

(defn fill
  "Applies the fill algorithm to the supplied string `s`"
  [s]
  (concat s [\0] (flip (reverse s))))

(defn fill-to-length
  "Generates ever-longer iterative fill strings until it has
   enough characters to satisfy `size`."
  [size s]
  (->> (iterate fill s)
       (drop-while #(< (count %) size))
       first
       (take size)) )

(def checksum-mapping
  "If the bits (characters) match, emit a 1, else 0"
  {[\1 \1] \1
   [\0 \0] \1
   [\0 \1] \0
   [\1 \0] \0})

(defn checksum-pass
  "Compute a checksum candidate by examining pairs of characters."
  [s]
  (map checksum-mapping (partition 2 s)))

(defn checksum
  "Computes the checksum of the supplied string.
   The checksum will always be odd-numbered in length."
  [s]
  (let [cs (checksum-pass s)]
    (if (odd? (count cs))
      (str/join cs)
      (checksum cs))))

;; Puzzle solutions
(defn part1
  "What is the correct checksum to fill a disk of length 272"
  [input]
  (checksum (fill-to-length first-disk-size input)))

(defn part2
  "What is the correct checksum to fill a disk of length 35651584"
  [input]
  (checksum (fill-to-length second-disk-size input)))