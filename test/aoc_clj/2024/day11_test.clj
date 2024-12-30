(ns aoc-clj.2024.day11-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2024.day11 :as d11]))

(def d11-s00-raw ["0 1 10 99 999"])

(def d11-s00 [0 1 10 99 999])
(def d11-s01 [125 17])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d11-s00 (d11/parse d11-s00-raw)))))

(deftest blink-test
  (testing "Updates the count of stones after a blink"
    (is (= {1 2 2024 1 0 1 9 2 2021976 1}
           (d11/blink (frequencies d11-s00))))

    (is (= {253000 1 1 1 7 1}
           (d11/blink (frequencies d11-s01))))

    (is (= {253 1 0 1 2024 1 14168 1}
           (d11/blink {253000 1 1 1 7 1})))))

(deftest stones-after-n-blinks-test
  (testing "The number of stones after n blinks"
    (is (= 22    (d11/stones-after-n-blinks 6 d11-s01)))
    (is (= 55312 (d11/stones-after-n-blinks 25 d11-s01)))))

(def day11-input (u/parse-puzzle-input d11/parse 2024 11))

(deftest part1-test
  (testing "Reproduces the answer for day11, part1"
    (is (= 186996 (d11/part1 day11-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day11, part2"
    (is (= 221683913164898 (d11/part2 day11-input)))))