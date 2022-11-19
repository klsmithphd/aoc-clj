(ns aoc-clj.2015.day16
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

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

(def day16-input (parse (u/puzzle-input "2015/day16-input.txt")))

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

(defn day16-part1-soln
  []
  (matching-aunt exact-match? day16-input))

(defn day16-part2-soln
  []
  (matching-aunt range-match? day16-input))