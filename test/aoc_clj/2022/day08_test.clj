(ns aoc-clj.2022.day08-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2022.day08 :as t]))

(def d08-s01
  (t/parse
   ["30373"
    "25512"
    "65332"
    "33549"
    "35390"]))

(deftest parse-test
  (testing "Correctly parses the sample input"
    (is (= d08-s01
           [[3 0 3 7 3]
            [2 5 5 1 2]
            [6 5 3 3 2]
            [3 3 5 4 9]
            [3 5 3 9 0]]))))

(deftest visible?-test
  (testing "Determines which trees are visible in a given direction"
    (is (= [1 0 0 1 0] (t/visible? (nth d08-s01 0))))
    (is (= [1 1 0 0 0] (t/visible? (nth d08-s01 1))))
    (is (= [1 0 0 0 0] (t/visible? (nth d08-s01 2))))
    (is (= [1 0 1 0 1] (t/visible? (nth d08-s01 3))))
    (is (= [1 1 0 1 0] (t/visible? (nth d08-s01 4))))))

(deftest row-visibility-test
  (testing "Determines which trees are visible from either edge"
    (is (= [1 0 0 1 1] (t/row-visibility (nth d08-s01 0))))
    (is (= [1 1 1 0 1] (t/row-visibility (nth d08-s01 1))))
    (is (= [1 1 0 1 1] (t/row-visibility (nth d08-s01 2))))
    (is (= [1 0 1 0 1] (t/row-visibility (nth d08-s01 3))))
    (is (= [1 1 0 1 1] (t/row-visibility (nth d08-s01 4))))))

(deftest view-distance-test
  (testing "Determines the view distance product in the horizontal direction"
    (is (= (* 0 2) (t/view-distance (nth d08-s01 0) 0)))
    (is (= (* 1 1) (t/view-distance (nth d08-s01 0) 1)))
    (is (= (* 2 1) (t/view-distance (nth d08-s01 0) 2)))
    (is (= (* 3 1) (t/view-distance (nth d08-s01 0) 3)))
    (is (= (* 1 0) (t/view-distance (nth d08-s01 0) 4)))

    (is (= (* 0 1) (t/view-distance (nth d08-s01 1) 0)))
    (is (= (* 1 1) (t/view-distance (nth d08-s01 1) 1)))
    (is (= (* 1 2) (t/view-distance (nth d08-s01 1) 2)))
    (is (= (* 1 1) (t/view-distance (nth d08-s01 1) 3)))
    (is (= (* 2 0) (t/view-distance (nth d08-s01 1) 4)))))

(deftest visible-trees-test
  (testing "Counts the number of visible trees in the sample data"
    (is (= 21 (t/visible-trees d08-s01)))))

(deftest max-scenic-score-test
  (testing "Finds the maximum scenic score in the sample data"
    (is (= 8 (t/max-scenic-score d08-s01)))))

(deftest day08-part1-soln
  (testing "Reproduces the answer for day08, part1"
    (is (= 1538 (t/day08-part1-soln)))))

(deftest day08-part2-soln
  (testing "Reproduces the answer for day08, part2"
    (is (= 496125 (t/day08-part2-soln)))))