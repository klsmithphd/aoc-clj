(ns aoc-clj.2017.day08-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2017.day08 :as d08]))

(def d08-s00-raw
  ["b inc 5 if a > 1"
   "a inc 1 if b < 5"
   "c dec -10 if a >= 1"
   "c inc -20 if c == 10"])

(def d08-s01-raw
  ["x dec 315 if y != -910"])

(def d08-s00
  [["b" "inc" 5 "a" ">" 1]
   ["a" "inc" 1 "b" "<" 5]
   ["c" "dec" -10 "a" ">=" 1]
   ["c" "inc" -20 "c" "=" 10]])

(def d08-s01
  [["x" "dec" 315 "y" "not=" -910]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d08-s00 (d08/parse d08-s00-raw)))
    (is (= d08-s01 (d08/parse d08-s01-raw)))))

