(ns aoc-clj.2015.day18-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day18 :as d18]))

(def d18-s00-raw
  [".#.#.#"
   "...##."
   "#....#"
   "..#..."
   "#.#..#"
   "####.."])

(def d18-s00
  [6, #{[1 0] [3 0] [5 0]
        [3 1] [4 1]
        [0 2] [5 2]
        [2 3]
        [0 4] [2 4] [5 4]
        [0 5] [1 5] [2 5] [3 5]}])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d18-s00 (d18/parse d18-s00-raw)))))

(def d18-s00-step1
  (d18/parse
   ["..##.."
    "..##.#"
    "...##."
    "......"
    "#....."
    "#.##.."]))

(def d18-s00-step2
  (d18/parse
   ["..###."
    "......"
    "..###."
    "......"
    ".#...."
    ".#...."]))

(def d18-s00-step3
  (d18/parse
   ["...#.."
    "......"
    "...#.."
    "..##.."
    "......"
    "......"]))

(def d18-s00-step4
  (d18/parse
   ["......"
    "......"
    "..##.."
    "..##.."
    "......"
    "......"]))

(def on-neighbors-test
  (testing "Counts the number of neighbors in an on state"
    (is (= 1 (d18/on-neighbors (second d18-s00) [0 0])))
    (is (= 4 (d18/on-neighbors (second d18-s00) [4 0])))
    (is (= 2 (d18/on-neighbors (second d18-s00) [5 3])))))

(deftest step-test
  (testing "Correctly applies the Game of Life rules to update to next step"
    (is (= d18-s00-step1 (d18/step d18-s00)))
    (is (= d18-s00-step2 (d18/step d18-s00-step1)))
    (is (= d18-s00-step3 (d18/step d18-s00-step2)))
    (is (= d18-s00-step4 (d18/step d18-s00-step3)))))

(deftest lights-at-n-test
  (testing "Computes the lights on after a number of steps in sample"
    (is (= 11 (d18/lights-at-n d18-s00 1)))
    (is (= 8 (d18/lights-at-n d18-s00 2)))
    (is (= 4 (d18/lights-at-n d18-s00 3)))
    (is (= 4 (d18/lights-at-n d18-s00 4)))))

(def d18-s00-corners
  (d18/parse
   ["##.#.#"
    "...##."
    "#....#"
    "..#..."
    "#.#..#"
    "####.#"]))

(deftest corners-on-test
  (is (= d18-s00-corners (d18/corners-on d18-s00))))

(def day18-input (u/parse-puzzle-input d18/parse 2015 18))

(deftest part1
  (testing "Reproduces the answer for day18, part1"
    (is (= 1061 (d18/part1 day18-input)))))

(deftest part2
  (testing "Reproduces the answer for day18, part2"
    (is (= 1006 (d18/part2 day18-input)))))