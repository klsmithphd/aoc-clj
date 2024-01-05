(ns aoc-clj.2015.day20-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day20 :as t]))

(deftest presents-test
  (testing "Counts the right number of presents at the first few houses"
    (is (= [10 30 40 70 60 120 80 150 130]
           (map t/house-presents (range 1 10))))))

(def day20-input (u/parse-puzzle-input t/parse 2015 20))

(deftest part1-test
  (testing "Reproduces the answer for day20, part1"
    (is (= 776160 (t/part1 day20-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day20, part2"
    (is (= 786240 (t/part2 day20-input)))))