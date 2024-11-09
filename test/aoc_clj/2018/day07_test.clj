(ns aoc-clj.2018.day07-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
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

(deftest next-node-test
  (testing "Selects the correct next step in the assembly"
    (is (= "C" (d07/next-node d07-s00)))
    (is (= "A" (d07/next-node {"A" #{}
                               "B" #{"A"}
                               "D" #{"A"}
                               "E" #{"B" "D" "F"}
                               "F" #{}})))))

(deftest assembly-step-test
  (testing "Iterates through the assembly one step"
    (is (= {:graph {"A" #{}
                    "B" #{"A"}
                    "D" #{"A"}
                    "E" #{"B" "D" "F"}
                    "F" #{}}
            :steps ["C"]}
           (d07/assembly-step {:graph d07-s00 :steps []})))

    (is (= {:graph {"B" #{}
                    "D" #{}
                    "E" #{"B" "D" "F"}
                    "F" #{}}
            :steps ["C" "A"]}
           (d07/assembly-step
            {:graph  {"A" #{}
                      "B" #{"A"}
                      "D" #{"A"}
                      "E" #{"B" "D" "F"}
                      "F" #{}}
             :steps ["C"]})))

    (is (= {:graph {"D" #{}
                    "E" #{"D" "F"}
                    "F" #{}}
            :steps ["C" "A" "B"]}
           (d07/assembly-step
            {:graph  {"B" #{}
                      "D" #{}
                      "E" #{"B" "D" "F"}
                      "F" #{}}
             :steps ["C" "A"]})))))

(deftest assembly-test
  (testing "Returns the assembly steps as a string"
    (is (= "CABDFE" (d07/assembly d07-s00)))))

(def day07-input (u/parse-puzzle-input d07/parse 2018 7))

(deftest part1-test
  (testing "Reproduces the answer for day07, part1"
    (is (= "JMQZELVYXTIGPHFNSOADKWBRUC" (d07/part1 day07-input)))))