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
  [passphrase]
  (->> passphrase
       frequencies
       vals
       (apply max)
       (= 1)))

(defn not-anagrams
  [[a b]]
  (not= (frequencies a) (frequencies b)))

(defn is-valid-part2?
  [passphrase]
  (->> (combo/combinations passphrase 2)
       (every? not-anagrams)))

(defn valid-passphrase-count
  [is-valid? passphrases]
  (count (filter is-valid? passphrases)))

;; Puzzle solutions
(defn part1
  [input]
  (valid-passphrase-count is-valid-part1? input))

(defn part2
  [input]
  (valid-passphrase-count is-valid-part2? input))

