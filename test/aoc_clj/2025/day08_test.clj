(ns aoc-clj.2025.day08-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2025.day08 :as d08]))

(def day08-input (u/parse-puzzle-input d08/parse 2025 8))

(deftest part1-test
  (testing "Reproduces the answer for day08, part1"
    (is (= :not-implemented (d08/part1 day08-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day08, part2"
    (is (= :not-implemented (d08/part2 day08-input)))))