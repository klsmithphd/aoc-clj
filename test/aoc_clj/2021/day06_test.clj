(ns aoc-clj.2021.day06-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2021.day06 :as t]))

(def d06-s00
  (t/parse ["3,4,3,1,2"]))

(deftest fish-after-n-days
  (testing "Computes the number of fish after n days with sample data"
    (is (= 26   (t/fish-after-n-days d06-s00 18)))
    (is (= 5934 (t/fish-after-n-days d06-s00 80)))
    (is (= 26984457539 (t/fish-after-n-days d06-s00 256)))))

(def day06-input (u/parse-puzzle-input t/parse 2021 6))

(deftest part1-test
  (testing "Reproduces the answer for day06, part1"
    (is (= 386755 (t/part1 day06-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day06, part2"
    (is (= 1732731810807 (t/part2 day06-input)))))