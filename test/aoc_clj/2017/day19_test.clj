(ns aoc-clj.2017.day19-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2017.day19 :as d19]))

(def d19-s00-raw
  ["     |          "
   "     |  +--+    "
   "     A  |  C    "
   " F---|----E|--+ "
   "     |  |  |  D "
   "     +B-+  +--+ "])

(def d19-s00 (d19/parse d19-s00-raw))

(deftest start-test
  (testing "Finds the starting position in the grid"
    (is (= [5 0] (d19/start d19-s00)))))