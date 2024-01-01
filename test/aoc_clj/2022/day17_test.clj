(ns aoc-clj.2022.day17-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2022.day17 :as t]))

(def d17-s00 ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>")

(def step0 #{})
(def step1 (into step0 [[2 1] [3 1] [4 1] [5 1]]))
(def step2 (into step1 [[3 2] [2 3] [3 3] [4 3] [3 4]]))
(def step3 (into step2 [[0 4] [1 4] [2 4] [2 5] [2 6]]))
(def step4 (into step3 [[4 4] [4 5] [4 6] [4 7]]))
(def step5 (into step4 [[4 8] [5 8] [4 9] [5 9]]))

(deftest deposit-shape-test
  (testing "Lowers a new rock into place, as moved around by the jets"
    (is (= step1 (:rocks (t/simulate d17-s00 1))))
    (is (= step2 (:rocks (t/simulate d17-s00 2))))
    (is (= step3 (:rocks (t/simulate d17-s00 3))))
    (is (= step4 (:rocks (t/simulate d17-s00 4))))
    (is (= step5 (:rocks (t/simulate d17-s00 5))))))

(deftest tower-height-after-n-test
  (testing "Computes the height after a number of rocks have been deposited"
    (is (= 3068 (t/tower-height-after-n d17-s00 2022)))
    (is (= 1514285714288 (t/tower-height-after-n d17-s00 1000000000000)))))

(def day17-input (u/parse-puzzle-input t/parse 2022 17))

(deftest day17-part1-soln
  (testing "Reproduces the answer for day17, part1"
    (is (= 3171 (t/day17-part1-soln day17-input)))))

(deftest day17-part2-soln
  (testing "Reproduces the answer for day17, part2"
    (is (= 1586627906921 (t/day17-part2-soln day17-input)))))