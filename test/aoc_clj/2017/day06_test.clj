(ns aoc-clj.2017.day06-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day06 :as d06]))

(def d06-s00-raw
  ["0    2    7    0"])

(def d06-s00
  [0 2 7 0])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d06-s00 (d06/parse d06-s00-raw)))))