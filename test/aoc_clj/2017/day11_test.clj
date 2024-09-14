(ns aoc-clj.2017.day11-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day11 :as d11]))

(def d11-s00-raw
  ["ne,ne,ne"])

(def d11-s00 ["ne" "ne" "ne"])
(def d11-s01 ["ne" "ne" "sw" "sw"])
(def d11-s02 ["ne" "ne" "s" "s"])
(def d11-s03 ["se" "sw" "se" "sw" "sw"])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d11-s00 (d11/parse d11-s00-raw)))))

(deftest hex-distance-test
  (testing "Computes the distance to the end position"
    (is (= 3 (d11/hex-distance d11-s00)))
    (is (= 0 (d11/hex-distance d11-s01)))
    (is (= 2 (d11/hex-distance d11-s02)))
    (is (= 3 (d11/hex-distance d11-s03)))))

(def day11-input (u/parse-puzzle-input d11/parse 2017 11))

(deftest part1-test
  (testing "Reproduces the answer for day11, part1"
    (is (= 761 (d11/part1 day11-input)))))
