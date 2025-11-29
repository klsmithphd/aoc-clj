(ns aoc-clj.2025.day07-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2025.day07 :as d07]))

(def day07-input (u/parse-puzzle-input d07/parse 2025 1))

(deftest part1-test
  (testing "Reproduces the answer for day07, part1"
    (is (= :not-implemented (d07/part1 day07-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day07, part2"
    (is (= :not-implemented (d07/part2 day07-input)))))