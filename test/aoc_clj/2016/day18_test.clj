(ns aoc-clj.2016.day18-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day18 :as d18]))

(def d18-s00 "..^^.")
(def d18-s01 ".^^.^.^^^^")

(deftest next-row-test
  (testing "Computes the next row correctly"
    (is (= ".^^^^" (d18/next-row d18-s00)))
    (is (= "^^..^" (d18/next-row (d18/next-row d18-s00))))))

(deftest safe-tile-count-test
  (testing "Computes the number of safe tiles"
    (is (= 6 (d18/safe-tile-count 3 d18-s00)))
    (is (= 38 (d18/safe-tile-count 10 d18-s01)))))

(def day18-input (u/parse-puzzle-input d18/parse 2016 18))

(deftest part1-test
  (testing "Reproduces the answer for day18, part1"
    (is (= 1926 (d18/part1 day18-input)))))

;; FIXME - Too Slow - https://github.com/klsmithphd/aoc-clj/issues/82
(deftest ^:slow part2-test
  (testing "Reproduces the answer for day18, part2"
    (is (= 19986699 (d18/part2 day18-input)))))
