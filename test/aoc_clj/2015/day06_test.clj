(ns aoc-clj.2015.day06-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day06 :as d06]))

(def d06-s00-raw ["turn on 0,0 through 999,999"
                  "toggle 0,0 through 999,0"
                  "turn off 499,499 through 500,500"])

(def d06-s00
  [{:cmd :on     :start [0 0]     :end [999 999]}
   {:cmd :toggle :start [0 0]     :end [999 0]}
   {:cmd :off    :start [499 499] :end [500 500]}])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d06-s00 (d06/parse d06-s00-raw)))))

(def day06-input (u/parse-puzzle-input d06/parse 2015 6))

(deftest part1-test
  (testing "Reproduces the answer for day06, part1"
    (is (= 377891 (d06/part1 day06-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day06, part2"
    (is (= 14110788 (d06/part2 day06-input)))))