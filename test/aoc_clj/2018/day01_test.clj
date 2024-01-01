(ns aoc-clj.2018.day01-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2018.day01 :as t]))

(def day01-input (u/parse-puzzle-input t/parse 2018 1))

(deftest day01-part1-soln
  (testing "Reproduces the answer for day01, part1"
    (is (= 543 (t/day01-part1-soln day01-input)))))

(deftest day01-part2-soln
  (testing "Reproduces the answer for day01, part2"
    (is (= 621 (t/day01-part2-soln day01-input)))))