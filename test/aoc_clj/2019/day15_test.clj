(ns aoc-clj.2019.day15-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2019.day15 :as t]))

(def day15-input (u/parse-puzzle-input t/parse 2019 15))

(deftest part1-test
  (testing "Can reproduce the answer for part1"
    (is (= 280 (t/part1 day15-input)))))

(deftest part2-test
  (testing "Can reproduce the answer for part1"
    (is (= 400 (t/part2 day15-input)))))