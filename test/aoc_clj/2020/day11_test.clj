(ns aoc-clj.2020.day11-test
  (:require [clojure.string :as str]
            [clojure.test :refer [deftest testing is]]
            [aoc-clj.2020.day11 :as t]))

(def day11-sample
  (t/ferry-seatmap
   (str/split
    "L.LL.LL.LL
LLLLLLL.LL
L.L.L..L..
LLLL.LL.LL
L.LL.LL.LL
L.LLLLL.LL
..L.L.....
LLLLLLLLLL
L.LLLLLL.L
L.LLLLL.LL" #"\n")))

(deftest part1-sample
  (testing "Reproduces the answer for part1 on the sample input"
    (is (= 37 (t/occupied-seats-when-static day11-sample 4 t/adjacency)))))

(deftest part2-sample
  (testing "Reproduces the answer for part2 on the sample input"
    (is (= 26 (t/occupied-seats-when-static day11-sample 5 t/visibility)))))

;; FIXME: 2020.day11 too slow
;; https://github.com/Ken-2scientists/aoc-clj/issues/12
(deftest ^:slow day11-part1-soln
  (testing "Reproduces the answer for day11, part1"
    (is (= 2222 (t/day11-part1-soln)))))

(deftest ^:slow day11-part2-soln
  (testing "Reproduces the answer for day11, part2"
    (is (= 2032 (t/day11-part2-soln)))))