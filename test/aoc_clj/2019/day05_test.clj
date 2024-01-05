(ns aoc-clj.2019.day05-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2019.day05 :as t]))

(def day05-input (u/parse-puzzle-input t/parse 2019 5))

(deftest part1-test
  (testing "Can reproduce the answer for part1"
    (is (= 12234644 (t/part1 day05-input)))))

(deftest part2-test
  (testing "Can reproduce the answer for part2"
    (is (= 3508186 (t/part2 day05-input)))))