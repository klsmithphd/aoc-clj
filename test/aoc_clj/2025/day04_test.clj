(ns aoc-clj.2025.day04-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2025.day04 :as d04]))

(def day04-input (u/parse-puzzle-input d04/parse 2025 1))

(deftest part1-test
  (testing "Reproduces the answer for day04, part1"
    (is (= :not-implemented (d04/part1 day04-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day04, part2"
    (is (= :not-implemented (d04/part2 day04-input)))))