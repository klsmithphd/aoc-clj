(ns aoc-clj.2022.day02-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2022.day02 :as t]))

(def d02-s01
  (mapv
   t/parse-line
   ["A Y"
    "B X"
    "C Z"]))

(deftest parse-test
  (testing "Parses letter codes to plays"
    (is (= [[:rock :paper] [:paper :rock] [:scissors :scissors]]
           d02-s01))))

(deftest round-score
  (testing "Computes the score for each round"
    (is (= [8 1 6] (mapv t/round-score d02-s01)))))

;; (deftest day02-part1-soln
;;   (testing "Reproduces the answer for day02, part1"
;;     (is (= 0 (t/day02-part1-soln)))))

;; (deftest day02-part2-soln
;;   (testing "Reproduces the answer for day02, part2"
;;     (is (= 0 (t/day02-part2-soln)))))