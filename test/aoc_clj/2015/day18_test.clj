(ns aoc-clj.2015.day18-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.grid.vecgrid :as vg]
            [aoc-clj.2015.day18 :as d18]))

(def d18-s00-raw
  [".#.#.#"
   "...##."
   "#....#"
   "..#..."
   "#.#..#"
   "####.."])

(def d18-s00
  (vg/->VecGrid2D
   [[:off :on  :off :on  :off :on]
    [:off :off :off :on  :on  :off]
    [:on  :off :off :off :off :on]
    [:off :off :on  :off :off :off]
    [:on  :off :on  :off :off :on]
    [:on  :on  :on  :on  :off :off]]))

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

(deftest step-test
  (testing "Correctly applies the Game of Life rules to update to next step"
    (is (= d18-s00-step1 (d18/step d18-s00)))
    (is (= d18-s00-step2 (d18/step d18-s00-step1)))
    (is (= d18-s00-step3 (d18/step d18-s00-step2)))
    (is (= d18-s00-step4 (d18/step d18-s00-step3)))))

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

(deftest lights-on-at-step-n-test
  (testing "Computes the lights on after a number of steps in sample"
    (is (= 4 (d18/lights-on-at-step-n 4 d18-s00)))))

(deftest lights-on-at-step-n-with-corners-tes
  (testing "Computes the lights on after a number of steps in sample with corners stuck on"
    (is (= 17 (d18/lights-on-at-step-n true 5 (d18/corners-on d18-s00))))))

(def day18-input (u/parse-puzzle-input d18/parse 2015 18))

;; FIXME: 2015.day18 solution is too slow
;; https://github.com/Ken-2scientists/aoc-clj/issues/4
(deftest ^:slow part1
  (testing "Reproduces the answer for day18, part1"
    (is (= 1061 (d18/part1 day18-input)))))

(deftest ^:slow part2
  (testing "Reproduces the answer for day18, part2"
    (is (= 1006 (d18/part2 day18-input)))))