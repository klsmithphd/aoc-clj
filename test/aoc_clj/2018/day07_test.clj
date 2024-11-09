(ns aoc-clj.2018.day07-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2018.day07 :as d07]))

(def d07-s00-raw
  ["Step C must be finished before step A can begin."
   "Step C must be finished before step F can begin."
   "Step A must be finished before step B can begin."
   "Step A must be finished before step D can begin."
   "Step B must be finished before step E can begin."
   "Step D must be finished before step E can begin."
   "Step F must be finished before step E can begin."])

(def d07-s00
  {"A" #{"C"}
   "B" #{"A"}
   "C" #{}
   "D" #{"A"}
   "E" #{"B" "D" "F"}
   "F" #{"C"}})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d07-s00 (d07/parse d07-s00-raw)))))