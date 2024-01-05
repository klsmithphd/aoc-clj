(ns aoc-clj.2018.day03-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2018.day03 :as t]))

(def day03-input (u/parse-puzzle-input t/parse 2018 3))

(deftest part1-test
  (testing "Reproduces the answer for day03, part1"
    (is (= 116489 (t/part1 day03-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day03, part2"
    (is (= "#1260" (t/part2 day03-input)))))