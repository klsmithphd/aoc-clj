(ns aoc-clj.2017.day13-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2017.day13 :as d13]))

(def d13-s00-raw
  ["0: 3"
   "1: 2"
   "4: 4"
   "6: 4"])

(def d13-s00
  {0 3
   1 2
   4 4
   6 4})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d13-s00 (d13/parse d13-s00-raw)))))