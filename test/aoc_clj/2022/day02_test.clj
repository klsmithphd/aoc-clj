(ns aoc-clj.2022.day02-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2022.day02 :as t]))

(def d02-s01
  ["A Y"
   "B X"
   "C Z"])
(def d02-s01-1 (mapv t/parse-line-part1 d02-s01))
(def d02-s01-2 (mapv t/parse-line-part2 d02-s01))

(deftest parse-test
  (testing "Parses letter codes to plays"
    (is (= [[::t/rock ::t/paper]
            [::t/paper ::t/rock]
            [::t/scissors ::t/scissors]]
           d02-s01-1))
    (is (= [[::t/rock ::t/draw] [::t/paper ::t/lose] [::t/scissors ::t/win]]
           d02-s01-2))))

(deftest round-score
  (testing "Computes the score for each round"
    (is (= [8 1 6] (mapv t/score-part1 d02-s01-1)))
    (is (= [4 1 7] (mapv t/score-part2 d02-s01-2)))))

(deftest day02-part1-soln
  (testing "Reproduces the answer for day02, part1"
    (is (= 11906 (t/day02-part1-soln)))))

(deftest day02-part2-soln
  (testing "Reproduces the answer for day02, part2"
    (is (= 11186 (t/day02-part2-soln)))))