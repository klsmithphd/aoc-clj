(ns aoc-clj.2017.day14
  "Solution to https://adventofcode.com/2017/day/14"
  (:require [aoc-clj.2017.day10 :as d10]
            [aoc-clj.utils.binary :as b]))

;; Input parsing
(def parse first)

;; Puzzle logic
(defn hex->int
  [ch]
  (case ch
    \f 15
    \e 14
    \d 13
    \c 12
    \b 11
    \a 10
    (read-string (str ch))))

(defn used-squares
  [key-str]
  (->> (map #(str key-str "-" %) (range 128))
       (map d10/knot-hash)
       (mapcat #(mapcat (comp b/int->bitstr hex->int) %))
       (filter #{\1})
       count))

;; Puzzle solutions
(defn part1
  [input]
  (used-squares input))