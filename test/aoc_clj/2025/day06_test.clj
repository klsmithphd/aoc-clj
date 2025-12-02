(ns aoc-clj.2025.day06-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2025.day06 :as d06]))

(def day06-input (u/parse-puzzle-input d06/parse 2025 6))

(deftest part1-test
  (testing "Reproduces the answer for day06, part1"
    (is (= :not-implemented (d06/part1 day06-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day06, part2"
    (is (= :not-implemented (d06/part2 day06-input)))))