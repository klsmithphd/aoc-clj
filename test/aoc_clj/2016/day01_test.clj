(ns aoc-clj.2016.day01-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2016.day01 :as t]))

(def day01-sample1 (t/parse "R2, L3"))
(def day01-sample2 (t/parse "R2, R2, R2"))
(def day01-sample3 (t/parse "R5, L5, R5, R3"))
(def day01-sample4 (t/parse "R8, R4, R4, R8"))

(deftest move-test
  (testing "Moves to the correct location on sample data"
    (is (= [2 3] (:pos (t/move day01-sample1))))
    (is (= [0 -2] (:pos (t/move day01-sample2))))
    (is (= [10 2] (:pos (t/move day01-sample3))))))

(deftest distance-test
  (testing "Moves to the correct location on sample data"
    (is (= 5  (t/distance day01-sample1)))
    (is (= 2 (t/distance day01-sample2)))
    (is (= 12  (t/distance day01-sample3)))))

(deftest first-location-visited-twice-test
  (testing "Finds the first location visited twice"
    (is (= [4 0] (t/first-duplicate (:visited (t/all-visited day01-sample4))))))
  (testing "Finds distance to the first location visited twice"
    (is (= 4 (t/distance-to-first-duplicate day01-sample4)))))

(deftest day01-part1-soln
  (testing "Reproduces the answer for day01, part1"
    (is (= 241 (t/day01-part1-soln)))))

(deftest day01-part2-soln
  (testing "Reproduces the answer for day01, part2"
    (is (= 116 (t/day01-part2-soln)))))