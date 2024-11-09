(ns aoc-clj.2018.day05-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2018.day05 :as d05]))

(def d05-s00 "dabAcCaCBAcCcaDA")

(deftest collapsed-test
  (testing "Collapses any adjacent units of opposite polarity"
    (is (= ""               (d05/collapsed "aA")))
    (is (= "aA"             (d05/collapsed "abBA")))
    (is (= "abAB"           (d05/collapsed "abAB")))
    (is (= "aabAAB"         (d05/collapsed "aabAAB")))
    (is (= "dabAaCBAcaDA"   (d05/collapsed d05-s00)))
    (is (= "dabCBAcaDA"     (d05/collapsed "dabAaCBAcaDA")))
    (is (= "dabCBAcaDA"     (d05/collapsed "dabCBAcCcaDA")))
    (is (= "dabCBAcaDA"     (d05/collapsed "dabCBAcaDA")))))

(deftest fully-collapsed-test
  (testing "Keeps collapsing pairs until no more changes"
    (is (= ""           (d05/fully-collapsed "aA")))
    (is (= ""           (d05/fully-collapsed "abBA")))
    (is (= "abAB"       (d05/fully-collapsed "abAB")))
    (is (= "aabAAB"     (d05/fully-collapsed "aabAAB")))
    (is (= "dabCBAcaDA" (d05/fully-collapsed d05-s00)))))

(deftest fully-collapsed-count-test
  (testing "Returns the number of units remaining after collapsing reactions"
    (is (= 10 (d05/fully-collapsed-count d05-s00)))))

(deftest polymers-without-units-test
  (testing "Returns a seq of new starting polymers with a unit removed"
    (is (= ["dbcCCBcCcD"
            "daAcCaCAcCcaDA"
            "dabAaBAaDA"
            "abAcCaCBAcCcaA"]
           (take 4 (d05/polymers-without-units d05-s00))))))

(deftest shortest-polymer-test
  (testing "Length of the shortest possible polymer"
    (is (= 4 (d05/shortest-polymer d05-s00)))))

(def day05-input (u/parse-puzzle-input d05/parse 2018 5))

(deftest part1-test
  (testing "Reproduces the answer for day05, part1"
    (is (= 9202 (d05/part1 day05-input)))))

(deftest ^:slow part2-test
  (testing "Reproduces the answer for day05, part2"
    (is (= 6394 (d05/part2 day05-input)))))