(ns aoc-clj.2022.day14-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2022.day14 :as t]))

(def d14-s01
  (t/parse
   ["498,4 -> 498,6 -> 496,6"
    "503,4 -> 502,4 -> 502,9 -> 494,9"]))

(deftest move-test
  (testing "Sand moves one tick based on space"
    (is (= [0 1] (t/move {} [0 0])))
    (is (= [-1 1] (t/move {[0 1] :rock} [0 0])))
    (is (= [1 1]  (t/move {[0 1] :rock
                           [-1 1] :rock} [0 0])))
    (is (nil? (t/move {[0 1] :rock
                       [-1 1] :rock
                       [1 1] :rock} [0 0])))))

(deftest deposit-sand-grain-test
  (testing "Updates the grid with a newly deposited sand grain"
    (is (= {[500 8] :sand} 
           (select-keys (t/deposit-sand-grain d14-s01) [[500 8]])))))

;; (deftest day14-part1-soln
;;   (testing "Reproduces the answer for day14, part1"
;;     (is (= 0 (t/day14-part1-soln)))))

;; (deftest day14-part2-soln
;;   (testing "Reproduces the answer for day14, part2"
;;     (is (= 0 (t/day14-part2-soln)))))