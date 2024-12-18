(ns aoc-clj.2024.day18-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2024.day18 :as d18]))

(def d18-s00-raw
  ["5,4"
   "4,2"
   "4,5"
   "3,0"
   "2,1"
   "6,3"
   "2,4"
   "1,5"
   "0,6"
   "3,3"
   "2,6"
   "5,1"
   "1,2"
   "5,5"
   "2,5"
   "6,5"
   "1,4"
   "0,4"
   "6,4"
   "1,1"
   "6,1"
   "1,0"
   "0,5"
   "1,6"
   "2,0"])

(def d18-s00
  [[5 4]
   [4 2]
   [4 5]
   [3 0]
   [2 1]
   [6 3]
   [2 4]
   [1 5]
   [0 6]
   [3 3]
   [2 6]
   [5 1]
   [1 2]
   [5 5]
   [2 5]
   [6 5]
   [1 4]
   [0 4]
   [6 4]
   [1 1]
   [6 1]
   [1 0]
   [0 5]
   [1 6]
   [2 0]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d18-s00 (d18/parse d18-s00-raw)))))