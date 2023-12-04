(ns aoc-clj.2023.day04-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2023.day04 :as t]))


(def day04_s01_raw
  ["Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53"
   "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19"
   "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1"
   "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83"
   "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36"
   "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"])

(def day04_s01
  [{:winning #{41 48 83 86 17} :card #{83 86  6 31 17  9 48 53}}
   {:winning #{13 32 20 16 61} :card #{61 30 68 82 17 32 24 19}}
   {:winning #{1 21 53 59 44}  :card #{69 82 63 72 16 21 14  1}}
   {:winning #{41 92 73 84 69} :card #{59 84 76 51 58  5 54 83}}
   {:winning #{87 83 26 28 32} :card #{88 30 70 12 93 22 82 36}}
   {:winning #{31 18 13 56 72} :card #{74 77 10 23 35 67 36 11}}])

(deftest parse-test
  (testing "Can parse the input correctly"
    (is (= day04_s01 (t/parse day04_s01_raw)))))

(deftest points-test
  (testing "Scores the card correctly with the number of points"
    (is (= [8 2 2 1 0 0] (map t/points day04_s01)))))

(deftest points-sum-test
  (testing "Computes the sum of the points of each card"
    (is (= 13 (t/points-sum day04_s01)))))

(def day04-input (u/parse-puzzle-input t/parse 2023 4))

(deftest day04-part1-soln
  (testing "Reproduces the answer for day04, part1"
    (is (= 27059 (t/day04-part1-soln day04-input)))))