(ns aoc-clj.2025.day01-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2025.day01 :as d01]))

(def day01-input (u/parse-puzzle-input d01/parse 2025 1))

(deftest part1-test
  (testing "Reproduces the answer for day01, part1"
    (is (= :not-implemented (d01/part1 day01-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day01, part2"
    (is (= :not-implemented (d01/part2 day01-input)))))