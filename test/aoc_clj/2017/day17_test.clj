(ns aoc-clj.2017.day17-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day17 :as d17]))

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= 307 (d17/parse ["307"])))))

(deftest spin-step-test
  (testing "Performs each sequential step of the spinlock logic"
    (is (= [[0 1] 1 2] (d17/spin-step 3 [[0] 0 1])))
    (is (= [[0 2 1] 1 3] (d17/spin-step 3 [[0 1] 1 2])))
    (is (= [[0 2 3 1] 2 4] (d17/spin-step 3 [[0 2 1] 1 3])))
    (is (= [[0 2 4 3 1] 2 5] (d17/spin-step 3 [[0 2 3 1] 2 4])))
    (is (= [[0 5 2 4 3 1] 1 6] (d17/spin-step 3 [[0 2 4 3 1] 2 5])))))

(deftest val-after-target
  (testing "Finds the value one past the 2017 target value"
    (is (= 638 (d17/val-after-target d17/part1-limit d17/part1-limit 3)))))

(deftest val-after-zero-test
  (testing "Finds the value in the position to the right of zero after x steps"
    (is (= 1 (d17/val-after-zero 1 3)))
    (is (= 2 (d17/val-after-zero 2 3)))
    (is (= 5 (d17/val-after-zero 8 3)))
    (is (= 9 (d17/val-after-zero 9 3)))
    (is (= 9 (d17/val-after-zero 11 3)))
    (is (= 12 (d17/val-after-zero 12 3)))))

(def day17-input (u/parse-puzzle-input d17/parse 2017 17))

(deftest part1-test
  (testing "Reproduces the answer for day17, part1"
    (is (= 1244 (d17/part1 day17-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day17, part2"
    (is (= 11162912 (d17/part2 day17-input)))))