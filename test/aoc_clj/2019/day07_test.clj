(ns aoc-clj.2019.day07-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2019.day07 :as t]))

(def p1 [3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0])
(def s1 [4 3 2 1 0])
(def m1 43210)

(def p2 [3,23,3,24,1002,24,10,24,1002,23,-1,23,
         101,5,23,23,1,24,23,23,4,23,99,0,0])
(def s2 [0 1 2 3 4])
(def m2 54321)

(def p3 [3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,
         1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0])
(def s3 [1 0 4 3 2])
(def m3 65210)

(def p4 [3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,
         27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5])
(def s4 [9 8 7 6 5])
(def m4 139629729)

(def p5 [3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,
         -5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,
         53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10])
(def s5 [9 7 8 5 6])
(def m5 18216)

(deftest amplifier-chain-test
  (testing "Tests that a chain of five amplifiers produces the right thrust given sample phases"
    (is (= m1 (t/amplifier-chain p1 s1)))
    (is (= m2 (t/amplifier-chain p2 s2)))
    (is (= m3 (t/amplifier-chain p3 s3)))))

(deftest highest-amplifier-chain-output-test
  (testing "Finds the best output value for a given intcode program"
    (is (= m1 (t/max-amplifier-chain-output p1)))
    (is (= m2 (t/max-amplifier-chain-output p2)))
    (is (= m3 (t/max-amplifier-chain-output p3)))))

(deftest amplifier-loop-test
  (testing "Tests that a loop of five amplifiers produces the right thrust given sample phases"
    (is (= m4 (t/amplifier-loop p4 s4)))
    (is (= m5 (t/amplifier-loop p5 s5)))))

(deftest highest-amplifier-loop-output-test
  (testing "Finds the best output value for a given intcode program"
    (is (= m4 (t/max-amplifier-loop-output p4)))
    (is (= m5 (t/max-amplifier-loop-output p5)))))

(def day07-input (u/parse-puzzle-input t/parse 2019 7))

(deftest part1-test
  (testing "Can reproduce the answer for part1"
    (is (= 567045 (t/part1 day07-input)))))

(deftest part2-test
  (testing "Can reproduce the answer for part2"
    (is (= 39016654 (t/part2 day07-input)))))