(ns aoc-clj.2022.day15-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2022.day15 :as t]))

(def d15-s01
  (t/parse
   ["Sensor at x=2, y=18: closest beacon is at x=-2, y=15"
    "Sensor at x=9, y=16: closest beacon is at x=10, y=16"
    "Sensor at x=13, y=2: closest beacon is at x=15, y=3"
    "Sensor at x=12, y=14: closest beacon is at x=10, y=16"
    "Sensor at x=10, y=20: closest beacon is at x=10, y=16"
    "Sensor at x=14, y=17: closest beacon is at x=10, y=16"
    "Sensor at x=8, y=7: closest beacon is at x=2, y=10"
    "Sensor at x=2, y=0: closest beacon is at x=2, y=10"
    "Sensor at x=0, y=11: closest beacon is at x=2, y=10"
    "Sensor at x=20, y=14: closest beacon is at x=25, y=17"
    "Sensor at x=17, y=20: closest beacon is at x=21, y=22"
    "Sensor at x=16, y=7: closest beacon is at x=15, y=3"
    "Sensor at x=14, y=3: closest beacon is at x=15, y=3"
    "Sensor at x=20, y=1: closest beacon is at x=15, y=3"]))

(deftest no-beacon-points-in-line-test
  (testing "Finds the right number of points that can't contain a beacon
            at a given line"
    (is (= 26 (count (t/no-beacon-points-in-line d15-s01 10))))))

(deftest day15-part1-soln
  (testing "Reproduces the answer for day15, part1"
    (is (= 4907780 (t/day15-part1-soln)))))

;; (deftest day15-part2-soln
;;   (testing "Reproduces the answer for day15, part2"
;;     (is (= 0 (t/day15-part2-soln)))))