(ns aoc-clj.2019.day13-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2019.day13 :as t]))

(def day13-input (u/parse-puzzle-input t/parse 2019 13))

(deftest part1-test
  (testing "Can reproduce the answer for part1"
    (is (= 230 (t/part1 day13-input)))))

(deftest part2-test
  (testing "Can reproduce the answer for part2"
    (is (= 11140 (t/part2 day13-input)))))