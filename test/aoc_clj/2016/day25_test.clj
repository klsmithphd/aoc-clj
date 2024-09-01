(ns aoc-clj.2016.day25-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day25 :as d25]))

(deftest is-square-wave?-test
  (testing "Confirms that the sequence is a square wave of 0,1,0,1,etc"
    (is (= true (d25/is-square-wave? [0 1])))
    (is (= true (d25/is-square-wave? [0 1 0 1 0 1])))
    (is (= false (d25/is-square-wave? [0 1 0 1 1 1])))))

(def day25-input (u/parse-puzzle-input d25/parse 2016 25))

(deftest part1-test
  (testing "Reproduces the answer for day25, part1"
    (is (= 175 (d25/part1 day25-input)))))