(ns aoc-clj.2017.day25-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day25 :as d25]))

(def d25-s00-raw
  ["In state A:"
   "  If the current value is 0:"
   "    - Write the value 1."
   "    - Move one slot to the right."
   "    - Continue with state B."
   "  If the current value is 1:"
   "    - Write the value 0."
   "    - Move one slot to the left."
   "    - Continue with state B."
   ""
   "In state B:"
   "  If the current value is 0:"
   "    - Write the value 1."
   "    - Move one slot to the left."
   "    - Continue with state A."
   "  If the current value is 1:"
   "    - Write the value 1."
   "    - Move one slot to the right."
   "    - Continue with state A."])

(def d25-s00
  {"A" {0 {:write 1 :move :right :state "B"}
        1 {:write 0 :move :left :state "B"}}
   "B" {0 {:write 1 :move :left :state "A"}
        1 {:write 1 :move :right :state "A"}}})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d25-s00 (d25/parse d25-s00-raw)))))
