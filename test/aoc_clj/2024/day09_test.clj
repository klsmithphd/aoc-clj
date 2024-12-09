(ns aoc-clj.2024.day09-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2024.day09 :as d09]))

(def d09-s00-raw
  ["2333133121414131402"])

(def d09-s00
  [2 3 3 3 1 3 3 1 2 1 4 1 4 1 3 1 4 0 2])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d09-s00 (d09/parse d09-s00-raw)))))