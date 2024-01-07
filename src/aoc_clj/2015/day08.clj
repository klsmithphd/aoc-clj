(ns aoc-clj.2015.day08
  "Solution to https://adventofcode.com/2015/day/8"
  (:require [clojure.string :as str]))

;; Input parsing
(def parse identity)

;; Puzzle logic
(defn hex->unicode
  "Takes a hex representation like `\\xNN` and changes it to a
   Java/Clojure Unicode representation like `\\u00NN`"
  [s]
  (str "\\u00" (subs s 2)))

(defn unescape
  "Unescape the string"
  [s]
  (read-string (str/replace s #"\\x[0-9a-f]{2}" #(hex->unicode %))))

(defn escape
  "Escape backslashes and double-quote characters and wrap in new double-quotes"
  [s]
  (let [new-s (-> s (str/replace "\\" "\\\\") (str/replace "\"" "\\\""))]
    (str/join ["\"" new-s "\""])))

(defn unescaped-difference
  "The total difference in size between the code for the string literals
   and the actual strings"
  [input]
  (- (reduce + (map count input))
     (reduce + (map (comp count unescape) input))))

(defn escaped-difference
  "The total difference in size between the re-escaped string literals
   and the code for the string literals"
  [input]
  (- (reduce + (map (comp count escape) input))
     (reduce + (map count input))))

;; Puzzle solutions
(defn part1
  "Difference in size between unescaped and escaped strings"
  [input]
  (unescaped-difference input))

(defn part2
  "Difference in size between doubly escaped and escaped strings"
  [input]
  (escaped-difference input))