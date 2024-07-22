(ns aoc-clj.2016.day09
  "Solution to https://adventofcode.com/2016/day/9"
  (:require [clojure.string :as str]))

;; Constants
(def open-paren \()
(def close-paren \))


;; Input parsing
(def parse first)


;; Puzzle logic
(defn parse-marker
  "Parse a marker, represented as a string like `(1x5)`, into a
   char-count and repeat-count tuple"
  [s]
  (->> (re-seq #"\d+" s)
       (map read-string)))

(defn split-at-marker
  "Takes a sequence of characters that contains a marker sequence, e.g.
   `[( 1 x 5 ) X Y Z]`, and returns a pair of just the marker
   sequence and the remaining character sequence, e.g.
   
   `[[( 1 x 5], [X Y Z]]`"
  [chars]
  (let [[marker [_ & nxt-chs]] (split-with #(not= close-paren %) chars)]
    [marker nxt-chs]))


(declare decompressed-count)
(defn process-marker
  "Apply the decompression logic of the marker to the given characters,
   returning the count of characters decompressed by the marker and
   the remaining characters (if any) after the marker's window.
   
   If `recur?` is true, markers within the window will be recursively
   expanded as well."
  [chars recur?]
  (let [[marker nxt-chars] (split-at-marker chars)
        [window rpt] (parse-marker (str/join marker))
        cnt (if recur?
              (decompressed-count (take window nxt-chars) recur?)
              window)]
    [(* rpt cnt) (drop window nxt-chars)]))


(defn decompressed-count
  "Computes the length of the decompressed string. If the optional
   `recur?` argument is set to `true`, markers will be expanded 
   recursively."
  ([s]
   (decompressed-count s false))
  ([s recur?]
   (loop [total 0 chars s]
     (if (not (seq chars))
       total
       (if (not= open-paren (first chars))
         (recur (inc total) (rest chars))
         (let [[amt nxt-chars] (process-marker chars recur?)]
           (recur (+ total amt) nxt-chars)))))))

;; Puzzle solutions
(defn part1
  "Length of the decompressed input"
  [input]
  (decompressed-count input))

(defn part2
  "Length of the decompressed input using version 2 of the format"
  [input]
  (decompressed-count input true))
