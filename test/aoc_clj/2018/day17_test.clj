(ns aoc-clj.2018.day17-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2018.day17 :as d17]))

(def d17-s00-raw
  ["x=495, y=2..7"
   "y=7, x=495..501"
   "x=501, y=3..7"
   "x=498, y=2..4"
   "x=506, y=1..2"
   "x=498, y=10..13"
   "x=504, y=10..13"
   "y=13, x=498..504"])

(def d17-s00
  [[:v 495 2 7]
   [:h 7 495 501]
   [:v 501 3 7]
   [:v 498 2 4]
   [:v 506 1 2]
   [:v 498 10 13]
   [:v 504 10 13]
   [:h 13 498 504]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d17-s00 (d17/parse d17-s00-raw)))))

(deftest clay-line-test
  (testing "Expands the coordinates into all clay cells for a given line"
    (is (= [[495 2] [495 3] [495 4] [495 5] [495 6] [495 7]]
           (d17/clay-line (nth d17-s00 0))))
    (is (= [[495 7] [496 7] [497 7] [498 7] [499 7] [500 7] [501 7]]
           (d17/clay-line (nth d17-s00 1))))))

(deftest clay-map-test
  (testing "Expands all the clay veins into the set of clay cells"
    (is (= {1  [506]
            2  [495 498 506]
            3  [495 498 501]
            4  [495 498 501]
            5  [495 501]
            6  [495 501]
            7  [495 496 497 498 499 500 501]
            10 [498 504]
            11 [498 504]
            12 [498 504]
            13 [498 499 500 501 502 503 504]}
           (d17/clay-map d17-s00)))))

(deftest all-clay-test
  (testing "Expands the clay map into the set of clay cells"
    (is (= #{[495 2] [495 3] [495 4] [495 5] [495 6] [495 7]
             [496 7] [497 7] [498 7] [499 7] [500 7] [501 7]
             [501 3] [501 4] [501 5] [501 6]
             [498 2] [498 3] [498 4]
             [506 1] [506 2]
             [498 10] [498 11] [498 12] [498 13]
             [504 10] [504 11] [504 12] [504 13]
             [499 13] [500 13] [501 13] [502 13] [503 13]}
           (d17/all-clay (d17/clay-map d17-s00))))))

(deftest lowest-test
  (testing "Finds the lowest point in the scan"
    (is (= 13 (d17/lowest (d17/clay-map d17-s00))))))