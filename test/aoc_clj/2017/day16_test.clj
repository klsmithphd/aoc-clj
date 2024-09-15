(ns aoc-clj.2017.day16-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2017.day16 :as d16]))

(def d16-s00-raw ["s1,x3/4,pe/b"])
(def d16-s00
  [[:spin 1]
   [:exchange [3 4]]
   [:partner ["e" "b"]]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d16-s00 (d16/parse d16-s00-raw)))))