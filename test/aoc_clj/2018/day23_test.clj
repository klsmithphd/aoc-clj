(ns aoc-clj.2018.day23-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2018.day23 :as d23]))

(def d23-s00-raw
  ["pos=<0,0,0>, r=4"
   "pos=<1,0,0>, r=1"
   "pos=<4,0,0>, r=3"
   "pos=<0,2,0>, r=1"
   "pos=<0,5,0>, r=3"
   "pos=<0,0,3>, r=1"
   "pos=<1,1,1>, r=1"
   "pos=<1,1,2>, r=1"
   "pos=<1,3,1>, r=1"])

(def d23-s00
  [[[0 0 0] 4]
   [[1 0 0] 1]
   [[4 0 0] 3]
   [[0 2 0] 1]
   [[0 5 0] 3]
   [[0 0 3] 1]
   [[1 1 1] 1]
   [[1 1 2] 1]
   [[1 3 1] 1]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d23-s00 (d23/parse d23-s00-raw)))))
