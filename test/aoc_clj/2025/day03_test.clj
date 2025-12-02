(ns aoc-clj.2025.day03-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2025.day03 :as d03]))

(def day03-input (u/parse-puzzle-input d03/parse 2025 3))

(deftest part1-test
  (testing "Reproduces the answer for day03, part1"
    (is (= :not-implemented (d03/part1 day03-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day03, part2"
    (is (= :not-implemented (d03/part2 day03-input)))))