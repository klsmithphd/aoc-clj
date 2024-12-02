(ns aoc-clj.2024.day02-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2024.day02 :as d02]))

(def d02-s00-raw
  ["7 6 4 2 1"
   "1 2 7 8 9"
   "9 7 6 2 1"
   "1 3 2 4 5"
   "8 6 4 4 1"
   "1 3 6 7 9"])

(def d02-s00
  [[7 6 4 2 1]
   [1 2 7 8 9]
   [9 7 6 2 1]
   [1 3 2 4 5]
   [8 6 4 4 1]
   [1 3 6 7 9]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d02-s00 (d02/parse d02-s00-raw)))))