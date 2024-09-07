(ns aoc-clj.2017.day05-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day05 :as d05]))

(def d05-s00-raw
  ["0"
   "3"
   "0"
   "1"
   "-3"])

(def d05-s00
  [0 3 0 1 -3])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d05-s00 (d05/parse d05-s00-raw)))))

(deftest step-test
  (testing "Steps the state forward one iteration"
    (is (= {:jumps [1 3 0 1 -3] :idx 0 :steps 1}
           (d05/step :part1 {:jumps d05-s00 :idx 0 :steps 0})))
    (is (= {:jumps [2 3 0 1 -3] :idx 1 :steps 2}
           (d05/step :part1 {:jumps [1 3 0 1 -3] :idx 0 :steps 1})))
    (is (= {:jumps [2 4 0 1 -3] :idx 4 :steps 3}
           (d05/step :part1 {:jumps [2 3 0 1 -3] :idx 1 :steps 2})))
    (is (= {:jumps [2 4 0 1 -2] :idx 1 :steps 4}
           (d05/step :part1 {:jumps [2 4 0 1 -3] :idx 4 :steps 3})))
    (is (= {:jumps [2 5 0 1 -2] :idx 5 :steps 5}
           (d05/step :part1 {:jumps [2 4 0 1 -2] :idx 1 :steps 4})))))

(deftest steps-to-escape-test
  (testing "Counts the number of steps it takes to escape"
    (is (= 5 (d05/steps-to-escape :part1 d05-s00)))
    (is (= 10 (d05/steps-to-escape :part2 d05-s00)))))

(def day05-input (u/parse-puzzle-input d05/parse 2017 5))

(deftest part1-test
  (testing "Reproduces the answer for day05, part1"
    (is (= 339351 (d05/part1 day05-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day05, part2"
    (is (= 24315397 (d05/part2 day05-input)))))