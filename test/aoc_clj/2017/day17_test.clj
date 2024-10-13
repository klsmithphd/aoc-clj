(ns aoc-clj.2017.day17-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day17 :as d17]))

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= 307 (d17/parse ["307"])))))

(deftest spin-step-test
  (testing "Performs each sequential step of the spinlock logic"
    (is (= [[0 1] [1 1]]         (d17/spin-step 3 [[0] [0 0]])))
    (is (= [[0 2 1] [1 2]]       (d17/spin-step 3 [[0 1] [1 1]])))
    (is (= [[0 2 3 1] [2 3]]     (d17/spin-step 3 [[0 2 1] [1 2]])))
    (is (= [[0 2 4 3 1] [2 4]]   (d17/spin-step 3 [[0 2 3 1] [2 3]])))
    (is (= [[0 5 2 4 3 1] [1 5]] (d17/spin-step 3 [[0 2 4 3 1] [2 4]])))))

(deftest final-buffer-test
  (testing "Returns the buffer after n steps"
    (is (= [0 1]                 (d17/final-buffer 3 1)))
    (is (= [0 5 2 4 3 1]         (d17/final-buffer 3 5)))
    (is (= [0 9 5 7 2 4 3 8 6 1] (d17/final-buffer 3 9)))))

(deftest val-after-2017-test
  (testing "Finds the value one past the 2017 target value"
    (is (= 638 (d17/val-after-2017 3)))))

(deftest val-after-zero-test
  (testing "Finds the value in the position to the right of zero after x steps"
    (is (= 1 (d17/val-after-zero 3 1)))
    (is (= 2 (d17/val-after-zero 3 2)))
    (is (= 5 (d17/val-after-zero 3 8)))
    (is (= 9 (d17/val-after-zero 3 9)))
    (is (= 9 (d17/val-after-zero 3 11)))
    (is (= 12 (d17/val-after-zero 3 12)))))

(def day17-input (u/parse-puzzle-input d17/parse 2017 17))

(deftest part1-test
  (testing "Reproduces the answer for day17, part1"
    (is (= 1244 (d17/part1 day17-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day17, part2"
    (is (= 11162912 (d17/part2 day17-input)))))