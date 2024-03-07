(ns aoc-clj.2016.day03-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day03 :as d02]))

(def d03-s00
  [[101 301 501]
   [102 302 502]
   [103 303 503]
   [201 401 601]
   [202 402 602]
   [203 403 603]])

(deftest test-valid-triangle?
  (testing "Correctly determines whether a triangle is valid"
    (is (= false (d02/valid-triangle? [5 10 25])))
    (is (= true  (d02/valid-triangle? [5 12 13])))))

(deftest group-by-columns
  (testing "Reorders the triangles to be read by columns instead of rows"
    (is (= (set [[101 102 103]
                 [201 202 203]
                 [301 302 303]
                 [401 402 403]
                 [501 502 503]
                 [601 602 603]])
           (set (d02/group-by-columns d03-s00))))))

(def day03-input (u/parse-puzzle-input d02/parse 2016 3))

(deftest part1-test
  (testing "Reproduces the answer for day03, part1"
    (is (= 869 (d02/part1 day03-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day03, part2"
    (is (= 1544 (d02/part2 day03-input)))))