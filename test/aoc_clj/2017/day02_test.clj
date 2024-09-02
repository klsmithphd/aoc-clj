(ns aoc-clj.2017.day02-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2017.day02 :as d02]))

(def d02-s00-raw
  ["5 1 9 5"
   "7 5 3"
   "2 4 6 8"])

(def d02-s00
  [[5 1 9 5]
   [7 5 3]
   [2 4 6 8]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d02-s00 (d02/parse d02-s00-raw)))))