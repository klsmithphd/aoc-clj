(ns aoc-clj.2017.day12-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2017.day12 :as d12]))


(def d12-s00-raw
  ["0 <-> 2"
   "1 <-> 1"
   "2 <-> 0, 3, 4"
   "3 <-> 2, 4"
   "4 <-> 2, 3, 6"
   "5 <-> 6"
   "6 <-> 4, 5"])

(def d12-s00
  {0 [2]
   1 [1]
   2 [0 3 4]
   3 [2 4]
   4 [2 3 6]
   5 [6]
   6 [4 5]})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d12-s00 (d12/parse d12-s00-raw)))))

