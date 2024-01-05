(ns aoc-clj.2015.day05-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day05 :as t]))

(deftest nice-strings
  (testing "Identifies niceness using the first set of rules"
    (is (t/nice? "ugknbfddgicrmopn"))
    (is (t/nice? "aaa"))
    ;; No repeated character
    (is (not (t/nice? "jchzalrnumimnmhp")))
    (is (not (t/repeated-char? "jchzalrnumimnmhp")))
    ;; Has one of the banned pairs (xy)
    (is (not (t/nice? "haegwjzuvuyypxyu")))
    (is (not (t/no-invalid-pairs? "haegwjzuvuyypxyu")))
    ;; Not enough vowels
    (is (not (t/nice? "dvszwmarrgswjxmb")))
    (is (not (t/three-vowels? "dvszwmarrgswjxmb")))))

(deftest new-nice-strings
  (testing "Identifies niceness using the second set of rules"
    (is (t/new-nice? "qjhvhtzxzqqjkmpb"))
    (is (t/new-nice? "xxyxx"))
    ;; Missing repeating letter with intervening letter
    (is (not (t/new-nice? "uurcxstgmygtbstg")))
    (is (not (t/repeat-with-letter-between? "uurcxstgmygtbstg")))
    ;; Missing repeated pair
    (is (not (t/new-nice? "ieodomkazucvgmuy")))
    (is (not (t/non-overlapping-pair? "ieodomkazucvgmuy")))))

(def day05-input (u/parse-puzzle-input t/parse 2015 5))

(deftest part1-test
  (testing "Reproduces the answer for day05, part1"
    (is (= 255 (t/part1 day05-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day05, part2"
    (is (= 55 (t/part2 day05-input)))))