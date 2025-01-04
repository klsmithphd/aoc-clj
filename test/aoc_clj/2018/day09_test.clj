(ns aoc-clj.2018.day09-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2018.day09 :as d09]))

(def d09-s00-raw ["10 players; last marble is worth 1618 points"])

(def d09-s00 [10 1618])
(def d09-s01 [13 7999])
(def d09-s02 [17 1104])
(def d09-s03 [21 6111])
(def d09-s04 [30 5807])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d09-s00 (d09/parse d09-s00-raw)))))

(deftest play-test
  (testing "Plays the game up to the limit specified"
    (is (= [1 0]
           (seq (:marbles (d09/play [9 1])))))

    (is (= [2 1 0]
           (seq (:marbles (d09/play [9 2])))))

    (is (= [3 0 2 1]
           (seq (:marbles (d09/play [9 3])))))

    (is (= [22 11 1 12 6 13 3 14 7 15 0 16 8 17 4 18 9 19 2 20 10 21 5]
           (seq (:marbles (d09/play [9 22])))))

    (is (= [19 2 20 10 21 5 22 11 1 12 6 13 3 14 7 15 0 16 8 17 4 18]
           (seq (:marbles (d09/play [9 23])))))))

(deftest high-score-test
  (testing "Reports out the correct high score after playing the game"
    (is (= 8317   (d09/high-score d09-s00)))
    (is (= 146373 (d09/high-score d09-s01)))
    (is (= 2764   (d09/high-score d09-s02)))
    (is (= 54718  (d09/high-score d09-s03)))
    (is (= 37305  (d09/high-score d09-s04)))))

(def day09-input (u/parse-puzzle-input d09/parse 2018 9))

(deftest part1-test
  (testing "Reproduces the answer for day09, part1"
    (is (= 404611 (d09/part1 day09-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day09, part2"
    (is (= 3350093681 (d09/part2 day09-input)))))