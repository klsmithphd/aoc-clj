(ns aoc-clj.2017.day04
  "Solution to https://adventofcode.com/2017/day/4"
  (:require [clojure.math.combinatorics :as combo]
            [clojure.string :as str]))

;; Input parsing
(defn parse
  [input]
  (map #(str/split % #" ") input))

;; Puzzle logic
(defn is-valid-part1?
  "A passphrase is valid in part1 if there are no repeated words"
  [passphrase]
  (->> passphrase
       frequencies
       vals
       (apply max)
       (= 1)))

(defn not-anagrams
  "Checks that two strings a and b are not anagrams of one another"
  [[a b]]
  (not= (frequencies a) (frequencies b)))

(defn is-valid-part2?
  "A passphrase is valid in part2 if there are no words that are anagrams
   of one another"
  [passphrase]
  (->> (combo/combinations passphrase 2)
       (every? not-anagrams)))

(defn valid-passphrase-count
  "Counts how many passphrases satisfy the validity condition"
  [is-valid? passphrases]
  (count (filter is-valid? passphrases)))

;; Puzzle solutions
(defn part1
  "How many passphrases are valid given part1's validity rules?"
  [input]
  (valid-passphrase-count is-valid-part1? input))

(defn part2
  "How many passphrases are valid given part2's validity rules?"
  [input]
  (valid-passphrase-count is-valid-part2? input))

