(ns aoc-clj.2018.day08-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2018.day08 :as d08]))

(def d08-s00-raw
  ["2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"])

(def d08-s00
  [2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d08-s00 (d08/parse d08-s00-raw)))))