(ns aoc-clj.2023.day04-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2023.day04 :as t]))

(def d04-s00-raw
  ["Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53"
   "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19"
   "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1"
   "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83"
   "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36"
   "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"])

(def d04-s00
  [{:winning #{41 48 83 86 17} :card #{83 86  6 31 17  9 48 53}}
   {:winning #{13 32 20 16 61} :card #{61 30 68 82 17 32 24 19}}
   {:winning #{1 21 53 59 44}  :card #{69 82 63 72 16 21 14  1}}
   {:winning #{41 92 73 84 69} :card #{59 84 76 51 58  5 54 83}}
   {:winning #{87 83 26 28 32} :card #{88 30 70 12 93 22 82 36}}
   {:winning #{31 18 13 56 72} :card #{74 77 10 23 35 67 36 11}}])

(deftest parse-test
  (testing "Can parse the input correctly"
    (is (= d04-s00 (t/parse d04-s00-raw)))))

(deftest winning-matches-test
  (testing "Determines how many winning matches a card has"
    (is (= [4 2 2 1 0 0] (map t/winning-matches d04-s00)))))

(deftest points-test
  (testing "Scores the card correctly with the number of points"
    (is (= [8 2 2 1 0 0] (map t/points d04-s00)))))

(deftest points-sum-test
  (testing "Computes the sum of the points of each card"
    (is (= 13 (t/points-sum d04-s00)))))

(deftest update-card-count-test
  (testing "Determines how many of each of the lower cards there are based
            on the current number of matches"
    (is (= {:card-cnt [2 2 2 2 1] :total 1}
           (t/update-card-count {:card-cnt [1 1 1 1 1 1] :total 0} 4)))
    (is (= {:card-cnt [4 4 2 1] :total 3}
           (t/update-card-count {:card-cnt [2 2 2 2 1] :total 1} 2)))
    (is (= {:card-cnt [8 6 1] :total 7}
           (t/update-card-count {:card-cnt [4 4 2 1] :total 3} 2)))
    (is (= {:card-cnt [14 1] :total 15}
           (t/update-card-count {:card-cnt [8 6 1] :total 7} 1)))
    (is (= {:card-cnt [1] :total 29}
           (t/update-card-count {:card-cnt [14 1] :total 15} 0)))
    (is (= {:card-cnt [] :total 30}
           (t/update-card-count {:card-cnt [1] :total 29} 0)))))

(deftest total-cards-test
  (testing "Computes the total number of cards you have in part2"
    (is (= 30 (t/total-cards d04-s00)))))

(def day04-input (u/parse-puzzle-input t/parse 2023 4))

(deftest part1-test
  (testing "Reproduces the answer for day04, part1"
    (is (= 27059 (t/part1 day04-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day04, part2"
    (is (= 5744979 (t/part2 day04-input)))))