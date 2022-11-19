(ns aoc-clj.2015.day15-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2015.day15 :as t]))

(def day15-sample
  (t/parse
   ["Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8"
    "Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3"]))

(deftest score-test
  (testing "Computes the score correctly"
    (is (= 62842880 (t/score day15-sample [44 56])))
    (is (= 57600000 (t/score day15-sample [40 60])))))

(deftest find-max-score-test
  (testing "Finds the right combination to maximize the score"
    (is (= '(44 56) (t/find-max-score t/score day15-sample)))
    (is (= '(40 60) (t/find-max-score t/score-with-500cal day15-sample)))))

(deftest all-options-test
  (testing "Can generate all combinations of n vars that sum to a total"
    (is (= (t/all-options 2 2) '((2 0) (1 1) (0 2))))
    (is (= (t/all-options 2 3) '((2 0 0) (1 1 0) (1 0 1) (0 2 0) (0 1 1) (0 0 2))))
    (is (= (t/all-options 2 4) '((2 0 0 0) (1 1 0 0) (1 0 1 0)
                                           (1 0 0 1) (0 2 0 0) (0 1 1 0) (0 1 0 1)
                                           (0 0 2 0) (0 0 1 1) (0 0 0 2))))))

(deftest day15-part1-soln
  (testing "Reproduces the answer for day15, part1"
    (is (= 21367368 (t/day15-part1-soln)))))

(deftest day15-part2-soln
  (testing "Reproduces the answer for day15, part2"
    (is (= 1766400 (t/day15-part2-soln)))))