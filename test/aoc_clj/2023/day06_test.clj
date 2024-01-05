(ns aoc-clj.2023.day06-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2023.day06 :as t]))

(def d06-s00-raw ["Time:      7  15   30"
                  "Distance:  9  40  200"])

(def d06-s00
  [{:time 7  :dist 9}
   {:time 15 :dist 40}
   {:time 30 :dist 200}])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d06-s00 (t/parse d06-s00-raw)))))

(deftest distance-options-test
  (testing "Computes the distances possible for various amounts of time 
            holding down the button"
    (is (= [6 10 12 12 10 6] (t/distance-options (nth d06-s00 0))))))

(deftest winning-options-count-test
  (testing "Computes how many ways there are to win a race"
    (is (= 4 (t/winning-options-count (nth d06-s00 0))))
    (is (= 8 (t/winning-options-count (nth d06-s00 1))))
    (is (= 9 (t/winning-options-count (nth d06-s00 2))))

    (is (= 71503 (t/winning-options-count (t/long-race d06-s00))))))

(deftest win-count-multiplied-test
  (testing "Computes the product of the ways to win each race"
    (is (= 288 (t/win-count-multiplied d06-s00)))))

(deftest long-races-test
  (testing "Reinterprets the input as one long race"
    (is (= {:time 71530 :dist 940200} (t/long-race d06-s00)))))

(def day06-input (u/parse-puzzle-input t/parse 2023 6))

(deftest part1-test
  (testing "Reproduces the answer for day06, part1"
    (is (= 2344708 (t/part1 day06-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day06, part2"
    (is (= 30125202 (t/part2 day06-input)))))
