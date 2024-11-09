(ns aoc-clj.2018.day05-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2018.day05 :as d05]))

(deftest collapsed-test
  (testing "Collapses any adjacent units of opposite polarity"
    (is (= ""               (d05/collapsed "aA")))
    (is (= "aA"             (d05/collapsed "abBA")))
    (is (= "abAB"           (d05/collapsed "abAB")))
    (is (= "aabAAB"         (d05/collapsed "aabAAB")))
    (is (= "dabAaCBAcaDA"   (d05/collapsed "dabAcCaCBAcCcaDA")))
    (is (= "dabCBAcaDA"     (d05/collapsed "dabAaCBAcaDA")))
    (is (= "dabCBAcaDA"     (d05/collapsed "dabCBAcCcaDA")))
    (is (= "dabCBAcaDA"     (d05/collapsed "dabCBAcaDA")))))

(deftest fully-collapsed-test
  (testing "Keeps collapsing pairs until no more changes"
    (is (= ""           (d05/fully-collapsed "aA")))
    (is (= ""           (d05/fully-collapsed "abBA")))
    (is (= "abAB"       (d05/fully-collapsed "abAB")))
    (is (= "aabAAB"     (d05/fully-collapsed "aabAAB")))
    (is (= "dabCBAcaDA" (d05/fully-collapsed "dabAcCaCBAcCcaDA")))))

(deftest fully-collapsed-count-test
  (testing "Returns the number of units remaining after collapsing reactions"
    (is (= 10 (d05/fully-collapsed-count "dabAcCaCBAcCcaDA")))))

(def day05-input (u/parse-puzzle-input d05/parse 2018 5))

(deftest part1-test
  (testing "Reproduces the answer for day05, part1"
    (is (= 9202 (d05/part1 day05-input)))))