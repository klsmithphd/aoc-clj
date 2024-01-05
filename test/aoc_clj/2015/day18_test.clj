(ns aoc-clj.2015.day18-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.grid.mapgrid :as mapgrid]
            [aoc-clj.2015.day18 :as t]))

(def d18-s00
  (mapgrid/ascii->MapGrid2D t/char-map
                            [".#.#.#"
                             "...##."
                             "#....#"
                             "..#..."
                             "#.#..#"
                             "####.."]))

(deftest lights-on-at-step-n-test
  (testing "Computes the lights on after a number of steps in sample"
    (is (= 4 (t/lights-on-at-step-n 4 d18-s00)))))

(deftest lights-on-at-step-n-with-corners-tes
  (testing "Computes the lights on after a number of steps in sample with corners stuck on"
    (is (= 17 (t/lights-on-at-step-n true 5 (t/corners-on d18-s00))))))

(def day18-input (u/parse-puzzle-input t/parse 2015 18))

;; FIXME: 2015.day18 solution is too slow
;; https://github.com/Ken-2scientists/aoc-clj/issues/4
(deftest ^:slow part1
  (testing "Reproduces the answer for day18, part1"
    (is (= 1061 (t/part1 day18-input)))))

(deftest ^:slow part2
  (testing "Reproduces the answer for day18, part2"
    (is (= 1006 (t/part2 day18-input)))))