(ns aoc-clj.2022.day02-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2022.day02 :as t]))

(def d02-s01
  ["A Y"
   "B X"
   "C Z"])
(def d02-s01-1 (mapv t/parse-line-1 d02-s01))
(def d02-s01-2 (mapv t/parse-line-2 d02-s01))

(deftest parse-test
  (testing "Parses letter codes to plays"
    (is (= [[:rock :paper] [:paper :rock] [:scissors :scissors]]
           d02-s01-1))
    (is (= [[:rock :draw] [:paper :player2-lose] [:scissors :player2-win]]
           d02-s01-2))))

(deftest round-score
  (testing "Computes the score for each round"
    (is (= [8 1 6] (mapv t/round-score-1 d02-s01-1)))
    (is (= [4 1 7] (mapv t/round-score-2 d02-s01-2)))))

(deftest day02-part1-soln
  (testing "Reproduces the answer for day02, part1"
    (is (= 11906 (t/day02-part1-soln)))))

(deftest day02-part2-soln
  (testing "Reproduces the answer for day02, part2"
    (is (= 11186 (t/day02-part2-soln)))))