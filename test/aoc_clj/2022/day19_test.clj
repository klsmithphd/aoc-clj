(ns aoc-clj.2022.day19-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2022.day19 :as t]))

(def d19-s01
  (t/parse
   ["Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian."
    "Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian."]))

(deftest max-geodes-test
  (testing "Scenario generates the max possible number of geodes"
    (is (= 9 (t/max-geodes (get d19-s01 1) 24)))
    (is (= 12 (t/max-geodes (get d19-s01 2) 24)))))

(deftest quality-level-test
  (testing "Computes the quality level of the blueprint"
    (is (= 9 (t/quality-level (first d19-s01) 24)))
    (is (= 24 (t/quality-level (second d19-s01) 24)))))

(deftest quality-level-sum-test
  (testing "Computes the sum of all the quality levels of all the blueprints"
    (is (= 33 (t/quality-level-sum d19-s01 24)))))

(deftest day19-part1-soln
  (testing "Reproduces the answer for day19, part1"
    (is (= 1395 (t/day19-part1-soln)))))

;; (deftest day19-part2-soln
;;   (testing "Reproduces the answer for day19, part2"
;;     (is (= 0 (t/day19-part2-soln)))))