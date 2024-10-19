(ns aoc-clj.2017.day20-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day20 :as d20]))

(def d20-s00-raw
  ["p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>"
   "p=< 4,0,0>, v=< 0,0,0>, a=<-2,0,0>"])

(def d20-s00
  [{:p [3 0 0] :v [2 0 0] :a [-1 0 0]}
   {:p [4 0 0] :v [0 0 0] :a [-2 0 0]}])

(def d20-s01
  [{:p [-6 0 0] :v [3 0 0] :a [0 0 0]}
   {:p [-4 0 0] :v [2 0 0] :a [0 0 0]}
   {:p [-2 0 0] :v [1 0 0] :a [0 0 0]}
   {:p [3 0 0] :v [-1 0 0] :a [0 0 0]}])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d20-s00 (d20/parse d20-s00-raw)))))

(deftest closest-to-origin-test
  (testing "Finds the particle that stays the closest to the origin"
    (is (= 0 (d20/closest-to-origin d20-s00)))))

(deftest tick-test
  (testing "Updates a particle's state one tick at a time"
    (is (= {:p [4 0 0] :v [1 0 0] :a [-1 0 0]}
           (d20/tick (nth d20-s00 0))))
    (is (= {:p [4 0 0] :v [0 0 0] :a [-1 0 0]}
           (d20/tick (d20/tick (nth d20-s00 0)))))
    (is (= {:p [3 0 0] :v [-1 0 0] :a [-1 0 0]}
           (d20/tick (d20/tick (d20/tick (nth d20-s00 0))))))
    (is (= {:p [1 0 0] :v [-2 0 0] :a [-1 0 0]}
           (d20/tick (d20/tick (d20/tick (d20/tick (nth d20-s00 0)))))))))

(deftest update-without-collisions-test
  (testing "Simulates particle motion one step and culls collisions"
    (is (= [{:p [-3 0 0] :v [3 0 0] :a [0 0 0]}
            {:p [-2 0 0] :v [2 0 0] :a [0 0 0]}
            {:p [-1 0 0] :v [1 0 0] :a [0 0 0]}
            {:p [2 0 0] :v [-1 0 0] :a [0 0 0]}]
           (d20/update-without-collisions d20-s01)))

    (is (= [{:p [1 0 0] :v [-1 0 0] :a [0 0 0]}]
           (d20/update-without-collisions
            (d20/update-without-collisions d20-s01))))))

(deftest final-count-test
  (testing "Counts how many particles remain after collisions have been resolved"
    (is (= 1 (d20/final-count 100000 d20-s01)))))

(def day20-input (u/parse-puzzle-input d20/parse 2017 20))

(deftest part1-test
  (testing "Reproduces the answer for day20, part1"
    (is (= 364 (d20/part1 day20-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day20, part1"
    (is (= 420 (d20/part2 day20-input)))))