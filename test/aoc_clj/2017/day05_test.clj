(ns aoc-clj.2017.day05-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2017.day05 :as d05]))

(def d05-s00-raw
  ["0"
   "3"
   "0"
   "1"
   "-3"])

(def d05-s00
  [0 3 0 1 -3])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d05-s00 (d05/parse d05-s00-raw)))))