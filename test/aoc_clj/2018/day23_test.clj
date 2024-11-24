(ns aoc-clj.2018.day23-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2018.day23 :as d23]))

(def d23-s00-raw
  ["pos=<0,0,0>, r=4"
   "pos=<1,0,0>, r=1"
   "pos=<4,0,0>, r=3"
   "pos=<0,2,0>, r=1"
   "pos=<0,5,0>, r=3"
   "pos=<0,0,3>, r=1"
   "pos=<1,1,1>, r=1"
   "pos=<1,1,2>, r=1"
   "pos=<1,3,1>, r=1"])

(def d23-s00
  [[[0 0 0] 4]
   [[1 0 0] 1]
   [[4 0 0] 3]
   [[0 2 0] 1]
   [[0 5 0] 3]
   [[0 0 3] 1]
   [[1 1 1] 1]
   [[1 1 2] 1]
   [[1 3 1] 1]])

(def d23-s01
  [[[10 12 12] 2]
   [[12 14 12] 2]
   [[16 12 12] 4]
   [[14 14 14] 6]
   [[50 50 50] 200]
   [[10 10 10] 5]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d23-s00 (d23/parse d23-s00-raw)))))

(deftest strongest-signal-test
  (testing "Finds the nanobot with the strongest signal"
    (is (= [[0 0 0] 4] (d23/strongest-signal d23-s00)))))

(deftest in-range?-test
  (testing "Determines whether a nanobot is in range of the provided one"
    (is (= true  (d23/in-range? [[0 0 0] 4] (nth d23-s00 0))))
    (is (= true  (d23/in-range? [[0 0 0] 4] (nth d23-s00 1))))
    (is (= true  (d23/in-range? [[0 0 0] 4] (nth d23-s00 2))))
    (is (= true  (d23/in-range? [[0 0 0] 4] (nth d23-s00 3))))
    (is (= false (d23/in-range? [[0 0 0] 4] (nth d23-s00 4))))
    (is (= true  (d23/in-range? [[0 0 0] 4] (nth d23-s00 5))))
    (is (= true  (d23/in-range? [[0 0 0] 4] (nth d23-s00 6))))
    (is (= true  (d23/in-range? [[0 0 0] 4] (nth d23-s00 7))))
    (is (= false (d23/in-range? [[0 0 0] 4] (nth d23-s00 8))))))

(deftest in-range-of-strongest-count-test
  (testing "Counts the number of nanobots in range of the strongest one"
    (is (= 7 (d23/in-range-of-strongest-count d23-s00)))))

(def day23-input (u/parse-puzzle-input d23/parse 2018 23))

(deftest part1-test
  (testing "Reproduces the answer for day23, part1"
    (is (= 393 (d23/part1 day23-input)))))