(ns aoc-clj.2016.day13-test
  (:require [aoc-clj.2016.day13 :as t]
            [aoc-clj.utils.grid :as grid]
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

;; (deftest day13-part1-soln
;;   (testing "Reproduces the answer for day13, part1"
;;     (is (= 0 (t/day13-part1-soln)))))

;; (deftest day13-part2-soln
;;   (testing "Reproduces the answer for day13, part2"
;;     (is (= 0 (t/day13-part2-soln)))))