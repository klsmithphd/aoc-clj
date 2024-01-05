(ns aoc-clj.2016.day03-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day03 :as t]))

(def d03-s00
  [[101 301 501]
   [102 302 502]
   [103 303 503]
   [201 401 601]
   [202 402 602]
   [203 403 603]])

(deftest group-by-columns
  (testing "Reorders the triangles to be read by columns instead of rows"
    (is (= '((101 102 103)
             (201 202 203)
             (301 302 303)
             (401 402 403)
             (501 502 503)
             (601 602 603))
           (t/group-by-columns d03-s00)))))

(def day03-input (u/parse-puzzle-input t/parse 2016 3))

(deftest part1-test
  (testing "Reproduces the answer for day03, part1"
    (is (= 869 (t/part1 day03-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day03, part2"
    (is (= 1544 (t/part2 day03-input)))))