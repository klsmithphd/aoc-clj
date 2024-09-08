(ns aoc-clj.2017.day09
  "Solution to https://adventofcode.com/2017/day/9"
  (:require [clojure.string :as str]))

;; Constants
(def cancel-ptn #"!.")
(def garbage-ptn #"\<[^\>]*\>")

;; Input parsing
(def parse first)

;; Puzzle logic
(defn without-canceled
  "Return a new version of the string without any canceled sequences"
  [s]
  (str/replace s cancel-ptn ""))

(defn without-garbage
  "Given a string, return all non-overlapping garbage sequences"
  [s]
  (str/replace s garbage-ptn ""))

(defn without-commas
  "Return a new string stripped of all commas"
  [s]
  (str/replace s "," ""))

(defn cleaned
  "Return a string with the canceled, garbage sequence, and commas removed.  
   This string should only contain open or closed brace characters"
  [s]
  (-> s without-canceled without-garbage without-commas))

(defn scores
  "Compute the depth score for each open-close brace pair.   
   Returns in depth-first order."
  [s]
  (loop [depths [] depth 0 chars (cleaned s)]
    (if-not (seq chars)
      depths
      (if (= \} (first chars))
        (recur (conj depths depth) (dec depth) (rest chars))
        (recur depths (inc depth) (rest chars))))))

(defn total-score
  "Compute the total depth score for the string"
  [s]
  (reduce + (scores s)))

(defn garbage-count
  "Counts the total number of non-canceled garbage characters to 
   remove from the string"
  [s]
  (->> (without-canceled s)
       (re-seq garbage-ptn)
       (map (comp dec dec count))
       (reduce +)))

;; Puzzle solutions
(defn part1
  "What is the total score for all the groups in your input?"
  [input]
  (total-score input))

(defn part2
  "How many non-canceld characters are within the garbage in your input?"
  [input]
  (garbage-count input))