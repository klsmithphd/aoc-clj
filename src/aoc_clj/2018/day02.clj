(ns aoc-clj.2018.day02
  "Solution to https://adventofcode.com/2018/day/2"
  (:require [clojure.math.combinatorics :as combo]
            [clojure.string :as str]))

;; Input parsing
(def parse identity)

;; Puzzle logic
(defn repeat-count
  "Returns 1 if the `repeat-num` is found in the `coll`, else 0"
  [repeat-num coll]
  (if (some #(= repeat-num %) coll) 1 0))

(defn repeat-counter
  "Returns 1 if the box-id has at least one letter that repeats `repeat-num`
   times, else 0"
  [repeat-num box-id]
  (->> box-id
       frequencies
       (map second)
       (repeat-count repeat-num)))

(defn checksum
  "Computes the 'checksum' which is defined as the product
   between the number of box-ids that contain any pair of matching
   characters and the number of box-ids that contain a triple of matching
   characters"
  [box-ids]
  (let [doubles (reduce + (map (partial repeat-counter 2) box-ids))
        triples (reduce + (map (partial repeat-counter 3) box-ids))]
    (* doubles triples)))

(defn char-cmp
  "Returns zero if the characters are the same, else 1"
  [c1 c2]
  (if (= c1 c2) 0 1))

(defn edit-distance
  "Returns the sum of the number of character differences"
  [[str1 str2]]
  (reduce + (map char-cmp str1 str2)))

(defn chars-in-common
  "Returns the characters in common between two strings"
  [[str1 str2]]
  (->> (map list str1 str2)
       (filter #(apply = %))
       (map first)
       str/join))

(defn closest-boxids-common-chars
  "Given a list of box-ids, find the pair that is only one off in edit distance
   and return the characters they have in common"
  [box-ids]
  (->> (combo/combinations box-ids 2)
       (filter #(= 1 (edit-distance %)))
       first
       chars-in-common))

;; Puzzle solutions
(defn part1
  "What is the checksum for your list of box IDs?"
  [input]
  (checksum input))

(defn part2
  "What letters are common between the two correct box IDs?s"
  [input]
  (closest-boxids-common-chars input))
