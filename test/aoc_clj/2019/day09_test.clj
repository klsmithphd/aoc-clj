(ns aoc-clj.2019.day09-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2019.day09 :as t]))

(def day09-input (u/parse-puzzle-input t/parse 2019 9))

(deftest part1-test
  (testing "Can reproduce the answer for part1"
    (is (= 3742852857 (t/part1 day09-input)))))

(deftest part2-test
  (testing "Can reproduce the answer for part2"
    (is (= 73439 (t/part2 day09-input)))))