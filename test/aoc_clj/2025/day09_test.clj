(ns aoc-clj.2025.day09-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2025.day09 :as d09]))

(def day09-input (u/parse-puzzle-input d09/parse 2025 9))

(deftest part1-test
  (testing "Reproduces the answer for day09, part1"
    (is (= :not-implemented (d09/part1 day09-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day09, part2"
    (is (= :not-implemented (d09/part2 day09-input)))))