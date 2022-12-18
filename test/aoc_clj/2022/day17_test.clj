(ns aoc-clj.2022.day17-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2022.day17 :as t]))

(def d16-s01 ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>")

(def step0 {})
(def step1 (merge step0 {[2 1] :rock [3 1] :rock [4 1] :rock [5 1] :rock}))
(def step2 (merge step1 {[3 2] :rock [2 3] :rock [3 3] :rock [4 3] :rock [3 4] :rock}))
(def step3 (merge step2 {[0 4] :rock [1 4] :rock [2 4] :rock [2 5] :rock [2 6] :rock}))
(def step4 (merge step3 {[4 4] :rock [4 5] :rock [4 6] :rock [4 7] :rock}))
(def step5 (merge step4 {[4 8] :rock [5 8] :rock [4 9] :rock [5 9] :rock}))

(deftest deposit-shape-test
  (testing "Lowers a new rock into place, as moved around by the jets"
    (is (= step1
           (first (t/deposit-shape [step0 (drop 0 t/shapes) d16-s01]))))
    (is (= step2
           (first (t/deposit-shape [step1 (drop 1 t/shapes) (drop 4 d16-s01)]))))
    (is (= step3
           (first (t/deposit-shape [step2 (drop 2 t/shapes) (drop 8 d16-s01)]))))
    (is (= step4
           (first (t/deposit-shape [step3 (drop 3 t/shapes) (drop 13 d16-s01)]))))
    (is (= step5
           (first (t/deposit-shape [step4 (drop 4 t/shapes) (drop 20 d16-s01)]))))))

(deftest tower-height-after-n-test
  (testing "Computes the height after a number of rocks have been deposited"
    (is (= 3068 (t/tower-height-after-n d16-s01 2022)))))

;; (deftest day17-part1-soln
;;   (testing "Reproduces the answer for day17, part1"
;;     (is (= 0 (t/day17-part1-soln)))))

;; (deftest day17-part2-soln
;;   (testing "Reproduces the answer for day17, part2"
;;     (is (= 0 (t/day17-part2-soln)))))