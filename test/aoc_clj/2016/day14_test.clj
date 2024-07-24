(ns aoc-clj.2016.day14-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day14 :as d14]))

(deftest last-pad-key-test
  (testing "Finds the index that produces the 64 one-time pad key"
    (is (= 22728 (d14/last-pad-key "abc")))))

(def day14-input (u/parse-puzzle-input d14/parse 2016 14))

(deftest part1-test
  (testing "Reproduces the answer for day13, part1"
    (is (= 15168 (d14/part1 day14-input)))))
