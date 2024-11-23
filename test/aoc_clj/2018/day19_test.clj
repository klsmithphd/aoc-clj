(ns aoc-clj.2018.day19-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2018.day19 :as d19]))

(def d19-s00-raw
  ["#ip 0"
   "seti 5 0 1"
   "seti 6 0 2"
   "addi 0 1 0"
   "addr 1 2 3"
   "setr 1 0 0"
   "seti 8 0 4"
   "seti 9 0 5"])

(def d19-s00
  {:ip 0
   :insts [[:seti 5 0 1]
           [:seti 6 0 2]
           [:addi 0 1 0]
           [:addr 1 2 3]
           [:setr 1 0 0]
           [:seti 8 0 4]
           [:seti 9 0 5]]})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d19-s00 (d19/parse d19-s00-raw)))))