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
    (is (= ["C"]     (d07/next-nodes d07-s00)))
    (is (= ["A" "F"] (d07/next-nodes {"A" #{}
                                      "B" #{"A"}
                                      "D" #{"A"}
                                      "E" #{"B" "D" "F"}
                                      "F" #{}})))))

(deftest step-test-time
  (testing "Amount of time it takes to complete a step"
    (is (= 61 (d07/step-time 60 "A")))
    (is (= 86 (d07/step-time 60 "Z")))
    (is (= 1  (d07/step-time 0 "A")))
    (is (= 26 (d07/step-time 0 "Z")))))

(deftest parallel-assembly-step-test
  (testing "Updates the state with one or more workers"
    ;; Single worker
    (is (= {:graph {"A" #{}
                    "B" #{"A"}
                    "D" #{"A"}
                    "E" #{"B" "D" "F"}
                    "F" #{}}
            :steps ["C"]
            :WIP {}
            :time 3}
           (d07/parallel-assembly-step
            0 1
            {:graph d07-s00 :steps [] :WIP {} :time 0})))

    (is (= {:graph {"B" #{}
                    "D" #{}
                    "E" #{"B" "D" "F"}
                    "F" #{}}
            :steps ["C" "A"]
            :WIP {}
            :time 4}
           (d07/parallel-assembly-step
            0 1
            {:graph {"A" #{}
                     "B" #{"A"}
                     "D" #{"A"}
                     "E" #{"B" "D" "F"}
                     "F" #{}}
             :steps ["C"]
             :WIP {}
             :time 3})))

    (is (= {:graph {"D" #{}
                    "E" #{"D" "F"}
                    "F" #{}}
            :steps ["C" "A" "B"]
            :WIP {}
            :time 6}
           (d07/parallel-assembly-step
            0 1
            {:graph {"B" #{}
                     "D" #{}
                     "E" #{"B" "D" "F"}
                     "F" #{}}
             :steps ["C" "A"]
             :WIP {}
             :time 4})))

    ;; Two workers
    (is (= {:time 3
            :graph {"A" #{}
                    "B" #{"A"}
                    "D" #{"A"}
                    "E" #{"B" "D" "F"}
                    "F" #{}}
            :steps ["C"]
            :WIP {}}
           (d07/parallel-assembly-step
            0 2
            {:graph d07-s00
             :time 0
             :steps []
             :WIP {}})))

    (is (= {:time 4
            :graph {"B" #{}
                    "D" #{}
                    "E" #{"B" "D" "F"}
                    "F" #{}}
            :steps ["C" "A"]
            :WIP {"F" 5}}
           (d07/parallel-assembly-step
            0 2
            {:time 3
             :graph {"A" #{}
                     "B" #{"A"}
                     "D" #{"A"}
                     "E" #{"B" "D" "F"}
                     "F" #{}}
             :steps ["C"]
             :WIP {}})))

    (is (= {:time 6
            :graph {"D" #{}
                    "E" #{"D" "F"}
                    "F" #{}}
            :steps ["C" "A" "B"]
            :WIP {"F" 3}}
           (d07/parallel-assembly-step
            0 2
            {:time 4
             :graph {"B" #{}
                     "D" #{}
                     "E" #{"B" "D" "F"}
                     "F" #{}}
             :steps ["C" "A"]
             :WIP {"F" 5}})))

    (is (= {:time 9
            :graph {"D" #{}
                    "E" #{"D"}}
            :steps ["C" "A" "B" "F"]
            :WIP {"D" 1}}
           (d07/parallel-assembly-step
            0 2
            {:time 6
             :graph {"D" #{}
                     "E" #{"D" "F"}
                     "F" #{}}
             :steps ["C" "A" "B"]
             :WIP {"F" 3}})))

    (is (= {:time 10
            :graph {"E" #{}}
            :steps ["C" "A" "B" "F" "D"]
            :WIP {}}
           (d07/parallel-assembly-step
            0 2
            {:time 9
             :graph {"D" #{}
                     "E" #{"D"}}
             :steps ["C" "A" "B" "F"]
             :WIP {"D" 1}})))

    (is (= {:time 15
            :graph {}
            :steps ["C" "A" "B" "F" "D" "E"]
            :WIP {}}
           (d07/parallel-assembly-step
            0 2
            {:time 10
             :graph {"E" #{}}
             :steps ["C" "A" "B" "F" "D"]
             :WIP {}})))))

(deftest assembly-steps-test
  (testing "Returns the assembly steps as a string"
    (is (= "CABDFE" (d07/assembly-steps 0 1 d07-s00)))
    (is (= "CABFDE" (d07/assembly-steps 0 2 d07-s00)))))

(deftest assembly-time-test
  (testing "Determines when the sleigh will be assembled"
    (is (= 21 (d07/assembly-time 0 1 d07-s00)))
    (is (= 15 (d07/assembly-time 0 2 d07-s00)))))

(def day07-input (u/parse-puzzle-input d07/parse 2018 7))

(deftest part1-test
  (testing "Reproduces the answer for day07, part1"
    (is (= "JMQZELVYXTIGPHFNSOADKWBRUC" (d07/part1 day07-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day07, part2"
    (is (= 1133 (d07/part2 day07-input)))))