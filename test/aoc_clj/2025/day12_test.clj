(ns aoc-clj.2025.day12-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2025.day12 :as d12]))

(def day12-input (u/parse-puzzle-input d12/parse 2025 1))

(deftest part1-test
  (testing "Reproduces the answer for day12, part1"
    (is (= :not-implemented (d12/part1 day12-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day12, part2"
    (is (= :not-implemented (d12/part2 day12-input)))))