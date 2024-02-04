(ns aoc-clj.2015.day16
  "Solution to https://adventofcode.com/2015/day/16"
  (:require [clojure.string :as str]))

;; Constants
(def criteria
  {:children 3
   :cats 7
   :samoyeds 2
   :pomeranians 3
   :akitas 0
   :vizslas 0
   :goldfish 5
   :trees 3
   :cars 2
   :perfumes 1})

;; Input parsing
(defn parse-line
  [line]
  (let [[aunt props] (str/split line #": " 2)
        aunt-num     (read-string (subs aunt 4 (count aunt)))
        pairs        (str/split props #", ")
        attrs        (mapv (fn [pair]
                             (let [[k v] (str/split pair #": ")]
                               [(keyword k) (read-string v)])) pairs)]
    [aunt-num (into {} attrs)]))

(defn parse
  [input]
  (into {} (mapv parse-line input)))

;; Puzzle logic
(defn exact-match?
  "Checks if the given aunt's properties exactly match thos of the criteria"
  [[_ props]]
  (= props (select-keys criteria (keys props))))

(defn attr-compare
  "For four properties (cats, trees, pomerians, and goldfish), the value
   must be greater than or less than, not equal to the expected value. 
   For all others, we're looking for an exact match"
  [[attr actual-val]]
  (let [expected-val (criteria attr)]
    (case attr
      :cats        (> actual-val expected-val)
      :trees       (> actual-val expected-val)
      :pomeranians (< actual-val expected-val)
      :goldfish    (< actual-val expected-val)
      (= actual-val expected-val))))

(defn range-match?
  "Use the logic of part 2 where some attributes need to be greater
   than or less than, not just equal, to determine whether an aunt
   matches the criteria"
  [[_ props]]
  (every? true? (map attr-compare props)))

(defn matching-aunt
  "Use the supplied criteria filter-"
  [criteria aunts]
  (-> (filter criteria aunts) first key))

;; Puzzle solutions
(defn part1
  "Find the aunt whose properties exactly match the search criteria"
  [input]
  (matching-aunt exact-match? input))

(defn part2
  "Find the aunt whose properties satisfied the range comparisonsß"
  [input]
  (matching-aunt range-match? input))