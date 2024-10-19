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

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d20-s00 (d20/parse d20-s00-raw)))))

(deftest closest-to-origin-test
  (testing "Finds the particle that stays the closest to the origin"
    (is (= 0 (d20/closest-to-origin d20-s00)))))

(def day20-input (u/parse-puzzle-input d20/parse 2017 20))

(deftest part1-test
  (testing "Reproduces the answer for day20, part1"
    (is (= 364 (d20/part1 day20-input)))))