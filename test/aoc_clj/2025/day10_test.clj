(ns aoc-clj.2025.day10-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2025.day10 :as d10]))

(def day10-input (u/parse-puzzle-input d10/parse 2025 10))

(deftest part1-test
  (testing "Reproduces the answer for day10, part1"
    (is (= :not-implemented (d10/part1 day10-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day10, part2"
    (is (= :not-implemented (d10/part2 day10-input)))))