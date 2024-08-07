(ns aoc-clj.2016.day21-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day21 :as d21]))

(def d21-s00-raw
  ["swap position 4 with position 0"
   "swap letter d with letter b"
   "reverse positions 0 through 4"
   "rotate left 1 step"
   "move position 1 to position 4"
   "move position 3 to position 0"
   "rotate based on position of letter b"
   "rotate based on position of letter d"])

(def d21-s00
  [["swap-positions" [4 0]]
   ["swap-letters" ["d" "b"]]
   ["reverse" [0 4]]
   ["rotate-left" 1]
   ["move" [1 4]]
   ["move" [3 0]]
   ["rotate" "b"]
   ["rotate" "d"]])

(def d21-s00-seq
  ["abcde"
   "ebcda"
   "edcba"
   "abcde"
   "bcdea"
   "bdeac"
   "abdec"
   "ecabd"
   "decab"])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d21-s00 (d21/parse d21-s00-raw)))))

(deftest scramble-step-test
  (testing "Correctly applies the instructions in sequence"
    (is (= d21-s00-seq (reductions d21/scramble-step "abcde" d21-s00)))))

(def day21-input (u/parse-puzzle-input d21/parse 2016 21))

(deftest part1-test
  (testing "Reproduces the answer for day21, part1"
    (is (= "bgfacdeh" (d21/part1 day21-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day21, part1"
    (is (= "bdgheacf" (d21/part2 day21-input)))))