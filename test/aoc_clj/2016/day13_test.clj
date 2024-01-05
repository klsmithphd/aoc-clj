(ns aoc-clj.2016.day13-test
  (:require [aoc-clj.2016.day13 :as t]
            [aoc-clj.utils.grid :as grid]
            ;; [aoc-clj.utils.core :as u]
            [clojure.test :refer [deftest is testing]]))

(def d13-s1
  ".#.####.##
..#..#...#
#....##...
###.#.###.
.##..#..#.
..##....#.
#...##.###")

(deftest layout-test
  (testing "Wall logic creates the same grid as the sample"
    (is (= d13-s1
           (grid/Grid2D->ascii
            t/charmap
            (t/construct-grid 10 10 7) :down true)))))

;; (def day13-input (u/parse-puzzle-input t/parse 2016 13))

;; (deftest part1-test
;;   (testing "Reproduces the answer for day13, part1"
;;     (is (= 0 (t/part1)))))

;; (deftest part2-test
;;   (testing "Reproduces the answer for day13, part2"
;;     (is (= 0 (t/part2)))))