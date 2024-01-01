(ns aoc-clj.2015.day16-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day16 :as t]))

(def day16-input (u/parse-puzzle-input t/parse 2015 16))

(deftest day16-part1-soln
  (testing "Reproduces the answer for day16, part1"
    (is (= 213 (t/day16-part1-soln day16-input)))))

(deftest day16-part2-soln
  (testing "Reproduces the answer for day16, part2"
    (is (= 323 (t/day16-part2-soln day16-input)))))