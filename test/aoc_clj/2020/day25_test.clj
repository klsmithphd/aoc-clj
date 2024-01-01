(ns aoc-clj.2020.day25-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2020.day25 :as t]))

(def day25-sample [5764801 17807724])

(deftest finds-loop-size
  (testing "Can find the loop-size"
    (is (= 8 (t/loop-size (first day25-sample))))
    (is (= 11 (t/loop-size (second day25-sample))))
    (is (= 14897079 (t/encryption-key day25-sample)))))

(def day25-input (u/parse-puzzle-input t/parse 2020 25))

(deftest day25-part1-soln
  (testing "Reproduces the answer for day25, part1"
    (is (= 6421487 (t/day25-part1-soln day25-input)))))