(ns aoc-clj.2017.day15-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day15 :as d15]))

(def d15-s00-raw
  ["Generator A uses 65"
   "Generator B uses 8921"])

(def d15-s00 [65 8921])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d15-s00 (d15/parse d15-s00-raw)))))

(deftest part1-pairs-test
  (testing "Generates the sequence of paired values in part1"
    (is (= [[1092455 430625591]
            [1181022009 1233683848]
            [245556042 1431495498]
            [1744312007 137874439]
            [1352636452 285222916]]
           (d15/part1-pairs 5 d15-s00)))))

(deftest part2-pairs-test
  (testing "Generates the sequence of paired values in part2"
    (is (= [[1352636452 1233683848]
            [1992081072 862516352]
            [530830436 1159784568]
            [1980017072 1616057672]
            [740335192 412269392]]
           (d15/part2-pairs 5 d15-s00)))))

(deftest ^:slow judge-count-test
  (testing "How many values match within the sample size"
    (is (= 588 (d15/judge-count d15/part1-pairs d15/part1-limit d15-s00)))
    (is (= 309 (d15/judge-count d15/part2-pairs d15/part2-limit d15-s00)))))

(def day15-input (u/parse-puzzle-input d15/parse 2017 15))

(deftest part1-test
  (testing "Reproduces the answer for day15, part1"
    (is (= 594 (d15/part1 day15-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day15, part2"
    (is (= 328 (d15/part2 day15-input)))))