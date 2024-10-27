(ns aoc-clj.2017.day24-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2017.day24 :as d24]))

(def d24-s00-raw
  ["0/2"
   "2/2"
   "2/3"
   "3/4"
   "3/5"
   "0/1"
   "10/1"
   "9/10"])

(def d24-s00
  [[0 2]
   [2 2]
   [2 3]
   [3 4]
   [3 5]
   [0 1]
   [10 1]
   [9 10]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d24-s00 (d24/parse d24-s00-raw)))))