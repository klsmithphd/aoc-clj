(ns aoc-clj.2019.day23-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2019.day23 :as t]))

(def day23-input (u/parse-puzzle-input t/parse 2019 23))

(deftest part1-test
  (testing "Can reproduce the answer for part1"
    (is (= 23886 (t/part1 day23-input)))))

(deftest part2-test
  (testing "Can reproduce the answer for part2"
    (is (= 18333 (t/part2 day23-input)))))