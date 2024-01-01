(ns aoc-clj.2019.day15-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2019.day15 :as t]))

(def day15-input (u/parse-puzzle-input t/parse 2019 15))

(deftest day15-part1-soln-test
  (testing "Can reproduce the answer for part1"
    (is (= 280 (t/day15-part1-soln day15-input)))))

(deftest day15-part2-soln-test
  (testing "Can reproduce the answer for part1"
    (is (= 400 (t/day15-part2-soln day15-input)))))