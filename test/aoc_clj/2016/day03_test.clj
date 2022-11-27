(ns aoc-clj.2016.day03-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2016.day03 :as t]))

(def day03-sample
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
           (t/group-by-columns day03-sample)))))

(deftest day03-part1-soln
  (testing "Reproduces the answer for day03, part1"
    (is (= 869 (t/day03-part1-soln)))))

(deftest day03-part2-soln
  (testing "Reproduces the answer for day03, part2"
    (is (= 1544 (t/day03-part2-soln)))))