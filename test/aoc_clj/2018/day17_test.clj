(ns aoc-clj.2018.day17-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2018.day17 :as d17]))

(def d17-s00-raw
  ["x=495, y=2..7"
   "y=7, x=495..501"
   "x=501, y=3..7"
   "x=498, y=2..4"
   "x=506, y=1..2"
   "x=498, y=10..13"
   "x=504, y=10..13"
   "y=13, x=498..504"])

(def d17-s00
  [[:v 495 2 7]
   [:h 7 495 501]
   [:v 501 3 7]
   [:v 498 2 4]
   [:v 506 1 2]
   [:v 498 10 13]
   [:v 504 10 13]
   [:h 13 498 504]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d17-s00 (d17/parse d17-s00-raw)))))