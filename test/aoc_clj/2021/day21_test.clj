(ns aoc-clj.2021.day21-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2021.day21 :as t]))

(def d21-s00-raw
  ["Player 1 starting position: 4"
   "Player 2 starting position: 8"])

(def d21-s00 [3 7])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d21-s00 (t/parse d21-s00-raw)))))

(deftest roll-until-win
  (testing "Determines when game ends for sample positions"
    (is (= {:roll 331 :player 1 :spaces [9 2] :score [1000 745]}
           (select-keys (t/play-until-win d21-s00)
                        [:roll :player :spaces :score])))))

(deftest loser-score-times-die-rolls
  (testing "Computes the product of the loser score and number of die rolls"
    (is (= 739785 (t/loser-score-times-die-rolls (t/play-until-win d21-s00))))))

(deftest dirac-max-win-count
  (testing "Computes the number of universes the winner wins in"
    (is (= 444356092776315 (apply max (t/win-counts {[[0 0] d21-s00] 1} 0))))))

(def day21-input (u/parse-puzzle-input t/parse 2021 21))

(deftest part1-test
  (testing "Reproduces the answer for day21, part1"
    (is (= 707784 (t/part1 day21-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day21, part2"
    (is (= 157595953724471 (t/part2 day21-input)))))
