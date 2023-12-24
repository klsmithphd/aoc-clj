(ns aoc-clj.2023.day24-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2023.day24 :as t]))

(def d24-s01-raw
  ["19, 13, 30 @ -2,  1, -2"
   "18, 19, 22 @ -1, -1, -2"
   "20, 25, 34 @ -2, -2, -4"
   "12, 31, 28 @ -1, -2, -1"
   "20, 19, 15 @  1, -5, -3"])

(def d24-s01
  [[[19 13 30] [-2  1 -2]]
   [[18 19 22] [-1 -1 -2]]
   [[20 25 34] [-2 -2 -4]]
   [[12 31 28] [-1 -2 -1]]
   [[20 19 15] [1 -5 -3]]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d24-s01 (t/parse d24-s01-raw)))))
