(ns aoc-clj.2022.day09-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2022.day09 :as t]))

(def d09-s01
  (t/parse
   ["R 4"
    "U 4"
    "L 3"
    "D 1"
    "R 4"
    "D 1"
    "L 5"
    "R 2"]))

(deftest distinct-tail-positions-test
  (testing "Counts all the unique positions the tail of the rope occupied"
    (is (= 13 (t/distinct-tail-positions d09-s01)))))

(deftest day09-part1-soln
  (testing "Reproduces the answer for day09, part1"
    (is (= 5874 (t/day09-part1-soln)))))

;; (deftest day09-part2-soln
;;   (testing "Reproduces the answer for day09, part2"
;;     (is (= 0 (t/day09-part2-soln)))))