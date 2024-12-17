(ns aoc-clj.2024.day17-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2024.day17 :as d17]))

(def d17-s00-raw
  ["Register A: 729"
   "Register B: 0"
   "Register C: 0"
   ""
   "Program: 0,1,5,4,3,0"])

(def d17-s00
  {:regs [729 0 0]
   :prog [0 1 5 4 3 0]})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d17-s00 (d17/parse d17-s00-raw)))))