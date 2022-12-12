(ns aoc-clj.2022.day12-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2022.day12 :as t]))

(def d12-s01
  (t/parse
   ["Sabqponm"
    "abcryxxl"
    "accszExk"
    "acctuvwj"
    "abdefghi"]))

(deftest shortest-path-from-start-test
  (testing "Finds the shortest path from the start location"
    (is (= 31 (t/shortest-path-from-start d12-s01)))))

(deftest shortest-path-from-any-a-test
  (testing "Finds the shortest path from any position at altitude a"
    (is (= 29 (t/shortest-path-from-any-a d12-s01)))))

(deftest day12-part1-soln
  (testing "Reproduces the answer for day12, part1"
    (is (= 408 (t/day12-part1-soln)))))

(deftest day12-part2-soln
  (testing "Reproduces the answer for day12, part2"
    (is (= 399 (t/day12-part2-soln)))))