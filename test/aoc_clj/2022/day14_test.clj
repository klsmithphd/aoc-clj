(ns aoc-clj.2022.day14-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2022.day14 :as t]))

(def d14-s01
  (t/parse
   ["498,4 -> 498,6 -> 496,6"
    "503,4 -> 502,4 -> 502,9 -> 494,9"]))

(deftest move-test
  (testing "Sand moves one tick based on space"
    (is (= [0 1] (t/move {} [0 0])))
    (is (= [-1 1] (t/move {[0 1] :rock} [0 0])))
    (is (= [1 1]  (t/move {[0 1] :rock
                           [-1 1] :rock} [0 0])))
    (is (nil? (t/move {[0 1] :rock
                       [-1 1] :rock
                       [1 1] :rock} [0 0])))))

(deftest deposit-sand-grain-test
  (testing "Updates the grid with a newly deposited sand grain"
    (is (= [500 8]
           (second (t/deposit-sand-grain [d14-s01 nil]))))
    (is (= [499 8]
           (second
            (nth (iterate t/deposit-sand-grain [d14-s01 nil]) 2))))
    (is (= [501 8]
           (second
            (nth (iterate t/deposit-sand-grain [d14-s01 nil]) 3))))
    (is (= [500 7]
           (second
            (nth (iterate t/deposit-sand-grain [d14-s01 nil]) 4))))
    (is (= [498 8]
           (second
            (nth (iterate t/deposit-sand-grain [d14-s01 nil]) 5))))
    (is (= [500 2]
           (second
            (nth (iterate t/deposit-sand-grain [d14-s01 nil]) 22))))
    (is (= [497 5]
           (second
            (nth (iterate t/deposit-sand-grain [d14-s01 nil]) 23))))
    (is (= [495 8]
           (second
            (nth (iterate t/deposit-sand-grain [d14-s01 nil]) 24))))))

(deftest sand-until-continuous-flow-test
  (testing "Finds the amount of sand that sticks before continuous flow"
    (is (= 24 (t/sand-until-continuous-flow d14-s01)))))

(deftest day14-part1-soln
  (testing "Reproduces the answer for day14, part1"
    (is (= 913 (t/day14-part1-soln)))))

;; FIXME: The implementation is too slow to test in a reasonable time
;; https://github.com/Ken-2scientists/aoc-clj/issues/27
(deftest ^:slow day14-part2-soln
  (testing "Reproduces the answer for day14, part2"
    (is (= 30762 (t/day14-part2-soln)))))