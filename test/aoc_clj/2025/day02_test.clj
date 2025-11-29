(ns aoc-clj.2025.day02-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2025.day02 :as d02]))

(def day02-input (u/parse-puzzle-input d02/parse 2025 1))

(deftest part1-test
  (testing "Reproduces the answer for day02, part1"
    (is (= :not-implemented (d02/part1 day02-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day02, part2"
    (is (= :not-implemented (d02/part2 day02-input)))))