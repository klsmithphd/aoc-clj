(ns aoc-clj.utils.intervals-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.intervals :as ivs]))

(deftest fully-contained-test
  (testing "Finds the pairs where one is fully contained in the other"
    (is (false? (ivs/fully-contained? [2 4] [6 8])))
    (is (false? (ivs/fully-contained? [2 3] [4 5])))
    (is (false? (ivs/fully-contained? [5 7] [7 9])))
    (is (true?  (ivs/fully-contained? [2 8] [3 7])))
    (is (true?  (ivs/fully-contained? [6 6] [4 6])))
    (is (false? (ivs/fully-contained? [2 6] [4 8])))))

(deftest overlap-test
  (testing "Finds the pairs where one overlaps with the other"
    (is (false? (ivs/overlap? [2 4] [6 8])))
    (is (false? (ivs/overlap? [2 3] [4 5])))
    (is (true?  (ivs/overlap? [5 7] [7 9])))
    (is (true?  (ivs/overlap? [2 8] [3 7])))
    (is (true?  (ivs/overlap? [6 6] [4 6])))
    (is (true?  (ivs/overlap? [2 6] [4 8])))))

(deftest contained?-test
  (testing "Whether the point is contained within the given interval"
    (is (false? (ivs/contained? 0 [1 5])))
    (is (true?  (ivs/contained? 1 [1 5])))
    (is (true?  (ivs/contained? 4 [1 5])))
    (is (true?  (ivs/contained? 5 [1 5])))
    (is (false? (ivs/contained? 6 [1 5])))))

(deftest in-intervals?-test
  (testing "Whether the point is contained within any interval in 
            `intervals`"
    (is (false? (ivs/in-intervals? 0 [[1 3] [5 7]])))
    (is (true?  (ivs/in-intervals? 1 [[1 3] [5 7]])))
    (is (false? (ivs/in-intervals? 4 [[1 3] [5 7]])))
    (is (true?  (ivs/in-intervals? 7 [[1 3] [5 7]])))))

(deftest simplify-test
  (testing "Collapses overlapping intervals into a simpler representation"
    (is (= [[1 5]]
           (ivs/simplify [[1 3] [3 5]])))
    (is (= [[1 3] [4 5]]
           (ivs/simplify [[1 3] [4 5]])))
    (is (= [[1 5] [7 9] [10 14]]
           (ivs/simplify [[1 3] [3 5] [7 9] [10 12] [11 14]])))))