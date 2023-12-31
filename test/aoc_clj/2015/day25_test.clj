(ns aoc-clj.2015.day25-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day25 :as t]))

(deftest code-num-test
  (testing "Correctly computes the code number for a given row col"
    (is (= 1  (t/code-number 1 1)))
    (is (= 2  (t/code-number 2 1)))
    (is (= 3  (t/code-number 1 2)))
    (is (= 4  (t/code-number 3 1)))
    (is (= 5  (t/code-number 2 2)))
    (is (= 6  (t/code-number 1 3)))
    (is (= 12 (t/code-number 4 2)))
    (is (= 15 (t/code-number 1 5)))))

(deftest code-sequence-test
  (testing "Computes the code sequence correctly"
    (is (= [20151125 31916031 18749137 16080970 21629792 17289845 24592653 8057251 16929656 30943339]
           (take 10 (iterate t/next-code t/first-code))))))

(deftest code-at-position-test
  (testing "Computes the code at given row/col position"
    (is (= 20151125 (t/code 1 1)))
    (is (= 33071741 (t/code 6 1)))
    (is (= 33511524 (t/code 1 6)))
    (is (= 27995004 (t/code 6 6)))))

(def day25-input (u/parse-puzzle-input t/parse 2015 25))

(deftest day25-part1-soln
  (testing "Reproduces the answer for day25, part1"
    (is (= 9132360 (t/day25-part1-soln day25-input)))))