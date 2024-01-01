(ns aoc-clj.2019.day05-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2019.day05 :as t]))

(def day05-input (u/parse-puzzle-input t/parse 2019 5))

(deftest day05-part1-soln-test
  (testing "Can reproduce the answer for part1"
    (is (= 12234644 (t/day05-part1-soln day05-input)))))

(deftest day05-part2-soln-test
  (testing "Can reproduce the answer for part2"
    (is (= 3508186 (t/day05-part2-soln day05-input)))))