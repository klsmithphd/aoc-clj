(ns aoc-clj.2015.day05-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day05 :as d05]))

(def d05-s00 "ugknbfddgicrmopn")
(def d05-s01 "aaa")
(def d05-s02 "jchzalrnumimnmhp")
(def d05-s03 "haegwjzuvuyypxyu")
(def d05-s04 "dvszwmarrgswjxmb")

(def d05-s05 "qjhvhtzxzqqjkmpb")
(def d05-s06 "xxyxx")
(def d05-s07 "uurcxstgmygtbstg")
(def d05-s08 "ieodomkazucvgmuy")

(deftest part1-nice-test
  (testing "Identifies niceness using the first set of rules"
    (is (d05/part1-nice? d05-s00))
    (is (d05/part1-nice? d05-s01))
    ;; No repeated character
    (is (not (d05/repeated-char? d05-s02)))
    (is (not (d05/part1-nice? d05-s02)))
    ;; Has one of the banned pairs (xy)
    (is (not (d05/no-invalid-pairs? d05-s03)))
    (is (not (d05/part1-nice? d05-s03)))
    ;; Not enough vowels
    (is (not (d05/three-vowels? d05-s04)))
    (is (not (d05/part1-nice? d05-s04)))))

(deftest part2-nice-test
  (testing "Identifies niceness using the second set of rules"
    (is (d05/part2-nice? d05-s05))
    (is (d05/part2-nice? d05-s06))
    ;; Missing repeating letter with intervening letter
    (is (not (d05/repeat-with-letter-between? d05-s07)))
    (is (not (d05/part2-nice? d05-s07)))
    ;; Missing repeated pair
    (is (not (d05/non-overlapping-pair? d05-s08)))
    (is (not (d05/part2-nice? d05-s08)))))

(def day05-input (u/parse-puzzle-input d05/parse 2015 5))

(deftest part1-test
  (testing "Reproduces the answer for day05, part1"
    (is (= 255 (d05/part1 day05-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day05, part2"
    (is (= 55 (d05/part2 day05-input)))))