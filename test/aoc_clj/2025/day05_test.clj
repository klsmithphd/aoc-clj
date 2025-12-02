(ns aoc-clj.2025.day05-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2025.day05 :as d05]))

(def day05-input (u/parse-puzzle-input d05/parse 2025 5))

(deftest part1-test
  (testing "Reproduces the answer for day05, part1"
    (is (= :not-implemented (d05/part1 day05-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day05, part2"
    (is (= :not-implemented (d05/part2 day05-input)))))