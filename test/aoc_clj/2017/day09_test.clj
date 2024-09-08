(ns aoc-clj.2017.day09-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day09 :as d09]))

(def d09-s00 "<>")
(def d09-s01 "<random characters>")
(def d09-s02 "<<<<>")
(def d09-s03 "<{!>}>")
(def d09-s04 "<!!>")
(def d09-s05 "<!!!>>")
(def d09-s06 "<{o\"i!a,<{i<a>")

(def d09-s07 "{}")
(def d09-s08 "{{{}}}")
(def d09-s09 "{{},{}}")
(def d09-s10 "{{{},{},{{}}}}")
(def d09-s11 "{<{},{},{{}}>}")
(def d09-s12 "{<a>,<a>,<a>,<a>}")
(def d09-s13 "{{<a>},{<a>},{<a>},{<a>}}")
(def d09-s14 "{{<!>},{<!>},{<!>},{<a>}}")

(deftest cleaned-test
  (testing "Strips the garbage from the character stream"
    (is (= "" (d09/cleaned d09-s00)))
    (is (= "" (d09/cleaned d09-s01)))
    (is (= "" (d09/cleaned d09-s02)))
    (is (= "" (d09/cleaned d09-s03)))
    (is (= "" (d09/cleaned d09-s04)))
    (is (= "" (d09/cleaned d09-s05)))
    (is (= "" (d09/cleaned d09-s06)))

    (is (= "{}"           (d09/cleaned d09-s07)))
    (is (= "{{{}}}"       (d09/cleaned d09-s08)))
    (is (= "{{}{}}"       (d09/cleaned d09-s09)))
    (is (= "{{{}{}{{}}}}" (d09/cleaned d09-s10)))
    (is (= "{}"           (d09/cleaned d09-s11)))
    (is (= "{}"           (d09/cleaned d09-s12)))
    (is (= "{{}{}{}{}}"   (d09/cleaned d09-s13)))
    (is (= "{{}}"         (d09/cleaned d09-s14)))))

(deftest scores-test
  (testing "Computes the depth scores correctly"
    (is (= [1]           (d09/scores d09-s07)))
    (is (= [3 2 1]       (d09/scores d09-s08)))
    (is (= [2 2 1]       (d09/scores d09-s09)))
    (is (= [3 3 4 3 2 1] (d09/scores d09-s10)))
    (is (= [1]           (d09/scores d09-s11)))
    (is (= [1]           (d09/scores d09-s12)))
    (is (= [2 2 2 2 1]   (d09/scores d09-s13)))
    (is (= [2 1]         (d09/scores d09-s14)))))

(deftest total-score-test
  (testing "Computes the total depth score"
    (is (= 1  (d09/total-score d09-s07)))
    (is (= 6  (d09/total-score d09-s08)))
    (is (= 5  (d09/total-score d09-s09)))
    (is (= 16 (d09/total-score d09-s10)))
    (is (= 1  (d09/total-score d09-s12)))
    (is (= 9  (d09/total-score d09-s13)))
    (is (= 3  (d09/total-score d09-s14)))))

(deftest garbage-count-test
  (testing "Counts number of non-canceled garbage chars removed"
    (is (= 0 (d09/garbage-count "<>")))
    (is (= 17 (d09/garbage-count "<random characters>")))))

(def day09-input (u/parse-puzzle-input d09/parse 2017 9))

(deftest part1-test
  (testing "Reproduces the answer for day09, part1"
    (is (= 16689 (d09/part1 day09-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day09, part2"
    (is (= 7982 (d09/part2 day09-input)))))