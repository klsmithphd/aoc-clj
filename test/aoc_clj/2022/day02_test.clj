(ns aoc-clj.2022.day02-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2022.day02 :as t]))

(def d02-s00
  (t/parse
   ["A Y"
    "B X"
    "C Z"]))

(deftest parse-test
  (testing "Correctly parses the sample input"
    (is (= d02-s00 [["A" "Y"] ["B" "X"] ["C" "Z"]]))))

(def d02-s00-1 (t/interpret-part1 d02-s00))
(def d02-s00-2 (t/interpret-part2 d02-s00))

(deftest interpret-test
  (testing "Interprets the letter codes according to the guidance"
    (is (= [[::t/rock     ::t/paper]
            [::t/paper    ::t/rock]
            [::t/scissors ::t/scissors]]
           d02-s00-1))
    (is (= [[::t/rock     ::t/draw]
            [::t/paper    ::t/lose]
            [::t/scissors ::t/win]]
           d02-s00-2))))

(deftest round-score
  (testing "Computes the score for each round"
    (is (= [8 1 6] (mapv t/score-part1 d02-s00-1)))
    (is (= [4 1 7] (mapv t/score-part2 d02-s00-2)))))

(def day02-input (u/parse-puzzle-input t/parse 2022 2))

(deftest day02-part1-soln
  (testing "Reproduces the answer for day02, part1"
    (is (= 11906 (t/day02-part1-soln day02-input)))))

(deftest day02-part2-soln
  (testing "Reproduces the answer for day02, part2"
    (is (= 11186 (t/day02-part2-soln day02-input)))))