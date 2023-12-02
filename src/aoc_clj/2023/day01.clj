(ns aoc-clj.2023.day01
  "Solution to https://adventofcode.com/2023/day/1"
  (:require [clojure.string :as str]))

(def parse identity)

(def spelled
  {"one"   1
   "two"   2
   "three" 3
   "four"  4
   "five"  5
   "six"   6
   "seven" 7
   "eight" 8
   "nine"  9})

(def spelled-regex
  "A zero-width with positive-lookahead pattern to find spelled out
   words or individual digits. The complexity of the zero-width 
   positive-lookahead is necessary to handle cases like `eightwo`,
   which should match both `eight` and `two`
   
   Evaluates to #'(?=(\\d|one|two|three|four|five|six|seven|eight|nine))' "
  (re-pattern (str "(?=(\\d|" (str/join "|" (keys spelled)) "))")))

(defn s->int
  "Return the supplied string `s` as an int. If `s` is a single character, convert directly to an int. If longer, convert the spelled-out string to its numerical equivalent."
  [s]
  (if (= 1 (count s))
    (read-string s)
    (spelled s)))

(defn digits
  "Returns a list of all the digits found in the string `s`. If
   `spelled` is `True`, spelled-out digits are included."
  ([s]
   (digits s false))
  ([s spelled]
   (if spelled
     (->> s
          (re-seq spelled-regex)
          (map second)
          (map s->int))
     (map s->int (re-seq #"\d" s)))))


(defn calibration-value
  "The calibration value can be found by combining the first digit and the
   last digit (in that order) to form a single two-digit number. If `spelled`
   is `True`, spelled-out digits are included."
  ([s]
   (calibration-value s false))
  ([s spelled]
   (let [digs (digits s spelled)]
     (+ (* 10 (first digs)) (last digs)))))

(defn calibration-value-sum
  "Returns the sum of the calibration values for each line of the
   input. If `spelled` = True, spelled-out digits are included."
  ([input]
   (calibration-value-sum input false))
  ([input spelled]
   (reduce + (map #(calibration-value % spelled) input))))

(defn day01-part1-soln
  "Consider your entire calibration document. 
   What is the sum of all of the calibration values?"
  [input]
  (calibration-value-sum input))

(defn day01-part2-soln
  "It looks like some of the digits are actually spelled out with letters: 
   one, two, three, four, five, six, seven, eight, and nine also count 
   as valid 'digits'.
   What is the sum of all of the calibration values?"
  [input]
  (calibration-value-sum input true))