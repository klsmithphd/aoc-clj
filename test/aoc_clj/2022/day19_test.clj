(ns aoc-clj.2022.day19-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2022.day19 :as t]))

(def d19-s01
  (t/parse
   ["Blueprint 1: Each ore robot costs 4 ore. 
     Each clay robot costs 2 ore. 
     Each obsidian robot costs 3 ore and 14 clay. 
     Each geode robot costs 2 ore and 7 obsidian."
    "Blueprint 2: Each ore robot costs 2 ore. 
     Each clay robot costs 3 ore. 
     Each obsidian robot costs 3 ore and 8 clay. 
     Each geode robot costs 3 ore and 12 obsidian."]))

(deftest parse-test
  (testing "Correctly parses the sample input. Each vector indicates
            how much raw materials it costs to construct a robot of the
            given type. The order is always ore, clay, obsidian, geode"
    (is (= d19-s01
           {1 [[4 0 0 0] [2 0 0 0] [3 14 0 0] [2 0 7 0]]
            2 [[2 0 0 0] [3 0 0 0] [3 8 0 0]  [3 0 12 0]]}))))

(deftest max-geodes-test
  (testing "Scenario generates the max possible number of geodes"
    (is (= 9  (t/max-geodes (get d19-s01 1) 24)))
    (is (= 12 (t/max-geodes (get d19-s01 2) 24)))
    (is (= 56 (t/max-geodes (get d19-s01 1) 32)))
    (is (= 62 (t/max-geodes (get d19-s01 2) 32)))))

(deftest quality-level-test
  (testing "Computes the quality level of the blueprint"
    (is (= 9  (t/quality-level (first d19-s01))))
    (is (= 24 (t/quality-level (second d19-s01))))))

(deftest quality-level-sum-test
  (testing "Computes the sum of all the quality levels of all the blueprints"
    (is (= 33 (t/quality-level-sum d19-s01)))))

(def day19-input (u/parse-puzzle-input t/parse 2022 19))

;; FIXME: Solution is too slow
;; https://github.com/Ken-2scientists/aoc-clj/issues/33
(deftest ^:slow part1
  (testing "Reproduces the answer for day19, part1"
    (is (= 1395 (t/part1 day19-input)))))

(deftest ^:slow part2
  (testing "Reproduces the answer for day19, part2"
    (is (= 2700 (t/part2 day19-input)))))