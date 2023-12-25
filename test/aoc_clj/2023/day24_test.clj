(ns aoc-clj.2023.day24-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2023.day24 :as t]))

(def d24-s01-raw
  ["19, 13, 30 @ -2,  1, -2"
   "18, 19, 22 @ -1, -1, -2"
   "20, 25, 34 @ -2, -2, -4"
   "12, 31, 28 @ -1, -2, -1"
   "20, 19, 15 @  1, -5, -3"])

(def d24-s01
  [[[19 13 30] [-2  1 -2]]
   [[18 19 22] [-1 -1 -2]]
   [[20 25 34] [-2 -2 -4]]
   [[12 31 28] [-1 -2 -1]]
   [[20 19 15] [1 -5 -3]]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d24-s01 (t/parse d24-s01-raw)))))

(deftest hailstone-intersect-xy-test
  (testing "Tests the comparisons of where hailstones will intersect"
    (is (= [43/3 46/3] (t/hailstone-intersect-xy [(nth d24-s01 0) (nth d24-s01 1)])))
    (is (= [35/3 50/3] (t/hailstone-intersect-xy [(nth d24-s01 0) (nth d24-s01 2)])))
    (is (= [31/5 97/5] (t/hailstone-intersect-xy [(nth d24-s01 0) (nth d24-s01 3)])))
    (is (= :past       (t/hailstone-intersect-xy [(nth d24-s01 0) (nth d24-s01 4)])))
    (is (= :parallel   (t/hailstone-intersect-xy [(nth d24-s01 1) (nth d24-s01 2)])))
    (is (= [-6 -5]     (t/hailstone-intersect-xy [(nth d24-s01 1) (nth d24-s01 3)])))
    (is (= :past       (t/hailstone-intersect-xy [(nth d24-s01 1) (nth d24-s01 4)])))
    (is (= [-2 3]      (t/hailstone-intersect-xy [(nth d24-s01 2) (nth d24-s01 3)])))
    (is (= :past       (t/hailstone-intersect-xy [(nth d24-s01 2) (nth d24-s01 4)])))
    (is (= :past       (t/hailstone-intersect-xy [(nth d24-s01 3) (nth d24-s01 4)])))))

(deftest intersections-within-area-test
  (testing "Counts the number of hailstone intersections within a prescribed area"
    (is (= 2 (t/intersections-within-area [7 27] d24-s01)))))

(def day24-input (u/parse-puzzle-input t/parse 2023 24))

;; (t/parallel-hailstones-3d d24-s01)
;; (t/parallel-hailstones-3d day24-input)

(deftest day24-part1-soln
  (testing "Reproduces the answer for day24, part1"
    (is (= 16172 (t/day24-part1-soln day24-input)))))
