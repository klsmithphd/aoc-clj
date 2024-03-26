(ns aoc-clj.2016.day05-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day05 :as d05]))

(def d05-s00 "abc")

(deftest five-zero-indices-test
  (testing "Finds the next index that results in a hash starting with five zeroes"
    (is (= 3231929 (first (d05/five-zero-indices d05-s00))))))

(deftest password-part1-test
  (testing "Identifies the password using part1 logic"
    (is (= "18f47a30" (d05/password-part1 d05-s00)))))

(deftest password-part2-test
  (testing "Identifies the password using part2 logic"
    (is (= "05ace8e3" (d05/password-part2 d05-s00)))))

(def day05-input (u/parse-puzzle-input d05/parse 2016 5))

(deftest part1-test
  (testing "Reproduces the answer for day05, part1"
    (is (= "f97c354d" (d05/part1 day05-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day05, part2"
    (is (= "863dde27" (d05/part2 day05-input)))))