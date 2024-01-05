(ns aoc-clj.2015.day16-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day16 :as t]))

(def day16-input (u/parse-puzzle-input t/parse 2015 16))

(deftest part1-test
  (testing "Reproduces the answer for day16, part1"
    (is (= 213 (t/part1 day16-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day16, part2"
    (is (= 323 (t/part2 day16-input)))))