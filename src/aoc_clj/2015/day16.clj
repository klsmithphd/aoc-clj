(ns aoc-clj.2015.day16
  "Solution to https://adventofcode.com/2015/day/16"
  (:require [clojure.string :as str]))

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

(defn parse-line
  [line]
  (let [[aunt props] (str/split line #": " 2)
        [_ aunt-no] (str/split aunt #" ")
        pairs (str/split props #", ")
        attrs (mapv (fn [x]
                      (let [[k v] (str/split x #": ")]
                        [(keyword k) (read-string v)])) pairs)]
    [(read-string aunt-no) (into {} attrs)]))

(defn parse
  [input]
  (into {} (mapv parse-line input)))

(defn exact-match?
  [[_ props]]
  (let [sub-criteria (select-keys criteria (keys props))]
    (= props sub-criteria)))

(defn attr-compare
  [attr expected-val actual-val]
  (case attr
    :cats        (> actual-val expected-val)
    :trees       (> actual-val expected-val)
    :pomeranians (< actual-val expected-val)
    :goldfish    (< actual-val expected-val)
    (= actual-val expected-val)))

(defn range-match?
  [[_ props]]
  (let [attrs (keys props)]
    (every? true? (map #(attr-compare % (criteria %) (props %)) attrs))))

(defn matching-aunt
  [criteria aunts]
  (ffirst (filter criteria aunts)))

(defn part1
  [input]
  (matching-aunt exact-match? input))

(defn part2
  [input]
  (matching-aunt range-match? input))