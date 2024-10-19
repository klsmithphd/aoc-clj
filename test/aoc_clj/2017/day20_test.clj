(ns aoc-clj.2017.day20-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2017.day20 :as d20]))

(def d20-s00-raw
  ["p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>"
   "p=< 4,0,0>, v=< 0,0,0>, a=<-2,0,0>"])

(def d20-s00
  [{:p [3 0 0] :v [2 0 0] :a [-1 0 0]}
   {:p [4 0 0] :v [0 0 0] :a [-2 0 0]}])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d20-s00 (d20/parse d20-s00-raw)))))