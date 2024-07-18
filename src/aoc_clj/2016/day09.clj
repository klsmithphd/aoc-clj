(ns aoc-clj.2016.day09
  "Solution to https://adventofcode.com/2016/day/9"
  (:require [clojure.string :as str]))

;; Input parsing
(def parse first)


;; Puzzle logic
(defn repeat-coll
  [n coll]
  (flatten (repeat n coll)))

(defn decompress
  [s]
  (loop [c (first s) cs (rest s) output []]
    (if (not c)
      (str/join output)
      (if (not= \( c)
        (recur (first cs) (rest cs) (conj output c))
        (let [splits (str/split (apply str cs) #"\)")
              r (str/join ")" (rest splits))
              [num repeats] (map read-string (str/split (first splits) #"x"))
              repeat-seq (take num r)
              remainder (drop num r)]
          (recur (first remainder)
                 (rest remainder)
                 (into output (repeat-coll repeats repeat-seq))))))))


;; TODO --- remove the duplicated logic between v2 and v1 of the format
(defn decompress-count
  [s]
  (loop [char-count 0 remaining s]
    (let [open-marker-loc (str/index-of remaining "(")]
      (if (nil? open-marker-loc)
        (+ char-count (count remaining))
        (let [close-marker-loc (str/index-of remaining ")")
              marker (subs remaining (inc open-marker-loc) close-marker-loc)
              [size repeats] (map read-string (str/split marker #"x"))
              after (subs remaining (inc close-marker-loc))
              repeat-string (subs after 0 size)
              new (subs after size)]
          (recur (+ (* (decompress-count repeat-string) repeats)
                    char-count open-marker-loc) new))))))

;; Puzzle solutions
(defn part1
  "Length of the decompressed input"
  [input]
  (count (decompress input)))

(defn part2
  "Length of the decompressed input using version 2 of the format"
  [input]
  (decompress-count input))
