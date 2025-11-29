(ns aoc-clj.2025.day11-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2025.day11 :as d11]))

(def day11-input (u/parse-puzzle-input d11/parse 2025 1))

(deftest part1-test
  (testing "Reproduces the answer for day11, part1"
    (is (= :not-implemented (d11/part1 day11-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day11, part2"
    (is (= :not-implemented (d11/part2 day11-input)))))