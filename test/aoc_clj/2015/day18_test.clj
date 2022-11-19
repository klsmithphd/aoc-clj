(ns aoc-clj.2015.day18-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.grid.mapgrid :as mapgrid]
            [aoc-clj.2015.day18 :as t]))

(def day18-sample
  (mapgrid/ascii->MapGrid2D t/char-map
                            [".#.#.#"
                             "...##."
                             "#....#"
                             "..#..."
                             "#.#..#"
                             "####.."]))

(deftest lights-on-at-step-n-test
  (testing "Computes the lights on after a number of steps in sample"
    (is (= 4 (t/lights-on-at-step-n 4 day18-sample)))))

(deftest lights-on-at-step-n-with-corners-tes
  (testing "Computes the lights on after a number of steps in sample with corners stuck on"
    (is (= 17 (t/lights-on-at-step-n true 5 (t/corners-on day18-sample))))))

;; FIXME: 2015.day18 solution is too slow
;; https://github.com/Ken-2scientists/aoc-clj/issues/4
(deftest ^:slow day18-part1-soln
  (testing "Reproduces the answer for day18, part1"
    (is (= 1061 (t/day18-part1-soln)))))

(deftest ^:slow day18-part2-soln
  (testing "Reproduces the answer for day18, part2"
    (is (= 1006 (t/day18-part2-soln)))))