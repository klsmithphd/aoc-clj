(ns aoc-clj.2023.day01-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2023.day01 :as t]))

(def d01-s01
  ["1abc2"
   "pqr3stu8vwx"
   "a1b2c3d4e5f"
   "treb7uchet"])

(def d01-s02
  ["two1nine"
   "eightwothree"
   "abcone2threexyz"
   "xtwone3four"
   "4nineeightseven2"
   "zoneight234"
   "7pqrstsixteen"])

(def hard-cases
  ["sevenine"])

(t/digits2 (get d01-s02 0))

(deftest calibration-value-test
  (testing "Computes a calibration value (first and last digit)"
    (is (= [12 38 15 77]
           (mapv #(t/calibration-value t/digits %) d01-s01)))
    (is (= [29, 83, 13, 24, 42, 14, 76]
           (mapv #(t/calibration-value t/digits2 %) d01-s02)))
    (is (= [11 27 33 58 71 92 87 51 76 99 94 14 47]
           (mapv #(t/calibration-value t/digits2 %) (take 13 t/day01-input))))
    ;; (is (= [79]
    ;;        (mapv #(t/calibration-value t/digits2 %) hard-cases))) 
    ))

(deftest calibration-value-sum-test
  (testing "Computes the calibration value sum"
    (is (= 142 (t/calibration-value-sum t/digits d01-s01)))
    (is (= 281 (t/calibration-value-sum t/digits2 d01-s02)))))

(deftest day01-part1-soln
  (testing "Reproduces the answer for day01, part1"
    (is (= 55172 (t/day01-part1-soln)))))

;; (deftest day01-part2-soln
;;   (testing "Reproduces the answer for day01, part2"
;;     (is (= 54953 (t/day01-part2-soln)))))