(ns aoc-clj.2022.day15-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2022.day15 :as t]))

(def d15-s00
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

(deftest parse-test
  (testing "Correctly parses the sample input"
    (is (= d15-s00
           [{:sensor [2 18],  :beacon [-2 15], :radius 7}
            {:sensor [9 16],  :beacon [10 16], :radius 1}
            {:sensor [13 2],  :beacon [15 3],  :radius 3}
            {:sensor [12 14], :beacon [10 16], :radius 4}
            {:sensor [10 20], :beacon [10 16], :radius 4}
            {:sensor [14 17], :beacon [10 16], :radius 5}
            {:sensor [8 7],   :beacon [2 10],  :radius 9}
            {:sensor [2 0],   :beacon [2 10],  :radius 10}
            {:sensor [0 11],  :beacon [2 10],  :radius 3}
            {:sensor [20 14], :beacon [25 17], :radius 8}
            {:sensor [17 20], :beacon [21 22], :radius 6}
            {:sensor [16 7],  :beacon [15 3],  :radius 5}
            {:sensor [14 3],  :beacon [15 3],  :radius 1}
            {:sensor [20 1],  :beacon [15 3],  :radius 7}]))))

(deftest no-beacon-points-in-line-test
  (testing "Finds the right number of points that can't contain a beacon
            at a given line"
    (is (= 26 (t/no-beacon-points-in-line d15-s00 10)))))

(deftest gap-position
  (testing "Finds the point where there's no sensor coverage"
    (is (= [14 11] (t/gap-position d15-s00)))))

(deftest tuning-frequency
  (testing "Computes the tuning frequency based on [x,y]"
    (is (= 56000011 (t/tuning-frequency [14 11])))))

(def day15-input (u/parse-puzzle-input t/parse 2022 15))

(deftest part1-test
  (testing "Reproduces the answer for day15, part1"
    (is (= 4907780 (t/part1 day15-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day15, part2"
    (is (= 13639962836448 (t/part2 day15-input)))))