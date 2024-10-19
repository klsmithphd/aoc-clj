(ns aoc-clj.2017.day18-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day18 :as d18]))

(def d18-s00-raw
  ["set a 1"
   "add a 2"
   "mul a a"
   "mod a 5"
   "snd a"
   "set a 0"
   "rcv a"
   "jgz a -1"
   "set a 1"
   "jgz a -2"])

(def d18-s00
  [["set" ["a" 1]]
   ["add" ["a" 2]]
   ["mul" ["a" "a"]]
   ["mod" ["a" 5]]
   ["snd" ["a"]]
   ["set" ["a" 0]]
   ["rcv" ["a"]]
   ["jgz" ["a" -1]]
   ["set" ["a" 1]]
   ["jgz" ["a" -2]]])

(def d18-s01
  [["snd" [1]]
   ["snd" [2]]
   ["snd" ["p"]]
   ["rcv" ["a"]]
   ["rcv" ["b"]]
   ["rcv" ["c"]]
   ["rcv" ["d"]]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d18-s00 (d18/parse d18-s00-raw)))))

(deftest add-cmd-test
  (testing "`add X Y` increases register X by the value of Y"
    (is (= {"a" 10 :pos 1}
           (d18/add-cmd {"a" 0 :pos 0} ["a" 10])))
    (is (= {"a" 7  :pos 1}
           (d18/add-cmd {"a" 3 "b" 4 :pos 0} ["a" "b"])))))

(deftest jgz-cmd-test
  (testing "`jgz X Y` jumps with an offset of the value of Y, only if
            the value of X is greater than zero"
    (is (= {:pos 1}
           (d18/jgz-cmd {"x" 0 :pos 0} ["x" 10])))
    (is (= {:pos 10}
           (d18/jgz-cmd {"x" 1 :pos 0} ["x" 10])))))

(deftest mod-cmd-test
  (testing "`mod X Y` sets register X to the remainder dividing X by Y"
    (is (= {"a" 2 :pos 1}
           (d18/mod-cmd {"a" 5 :pos 0} ["a" 3])))
    (is (= {"a" 3 :pos 1}
           (d18/mod-cmd {"a" 7 "b" 4 :pos 0} ["a" "b"])))))

(deftest mul-cmd-test
  (testing "`mul X Y` sets register X to the product of X and Y"
    (is (= {"a" 15 :pos 1}
           (d18/mul-cmd {"a" 5 :pos 0} ["a" 3])))
    (is (= {"a" 28 :pos 1}
           (d18/mul-cmd {"a" 7 "b" 4 :pos 0} ["a" "b"])))
    (is (= {"a" 0 :pos 1}
           (d18/mul-cmd {:pos 0} ["a" 5])))))

(deftest set-cmd-test
  (testing "`set X Y` sets register X to the value of Y"
    (is (= {"a" 1 :pos 1}
           (d18/set-cmd {:pos 0} ["a" 1])))
    (is (= {"a" 10 :pos 1}
           (d18/set-cmd {"b" 10 :pos 0} ["a" "b"])))))

(deftest rcv-cmd-p1-test
  (testing "`rcv X` recovers the frequency of last sound played, but
            only when X is not zero"
    (is (= {:pos 1}
           (d18/rcv-cmd-p1 {"a" 0 :pos 0} ["a"])))
    (is (= {:pos -1}
           (d18/rcv-cmd-p1 {"a" 1 :pos 0 :snd 5} ["a"])))))

(deftest snd-cmd-p1-test
  (testing "`snd X Y` plays a sound with frequency equal to X"
    (is (= {:pos 1 :snd 5}
           (d18/snd-cmd-p1 {"a" 5 :pos 0} ["a"])))))

(deftest step-p1-test
  (testing "Advances the state by one step from the instructions"
    (is (= {:insts d18-s00 :pos 1 "a" 1}
           (d18/step-p1 {:insts d18-s00 :pos 0})))
    (is (= {:insts d18-s00 :pos 2 "a" 3}
           (d18/step-p1 {:insts d18-s00 :pos 1 "a" 1})))
    (is (= {:insts d18-s00 :pos 3 "a" 9}
           (d18/step-p1 {:insts d18-s00 :pos 2 "a" 3})))
    (is (= {:insts d18-s00 :pos 4 "a" 4}
           (d18/step-p1 {:insts d18-s00 :pos 3 "a" 9})))
    (is (= {:insts d18-s00 :pos 5 "a" 4 :snd 4}
           (d18/step-p1 {:insts d18-s00 :pos 4 "a" 4})))
    (is (= {:insts d18-s00 :pos 6 "a" 0 :snd 4}
           (d18/step-p1 {:insts d18-s00 :pos 5 "a" 4 :snd 4})))
    (is (= {:insts d18-s00 :pos 7 "a" 0 :snd 4}
           (d18/step-p1 {:insts d18-s00 :pos 6 "a" 0 :snd 4})))
    (is (= {:insts d18-s00 :pos 8 "a" 0 :snd 4}
           (d18/step-p1 {:insts d18-s00 :pos 7 "a" 0 :snd 4})))
    (is (= {:insts d18-s00 :pos 9 "a" 1 :snd 4}
           (d18/step-p1 {:insts d18-s00 :pos 8 "a" 0 :snd 4})))
    (is (= {:insts d18-s00 :pos 7 "a" 1 :snd 4}
           (d18/step-p1 {:insts d18-s00 :pos 9 "a" 1 :snd 4})))
    (is (= {:insts d18-s00 :pos 6 "a" 1 :snd 4}
           (d18/step-p1 {:insts d18-s00 :pos 7 "a" 1 :snd 4})))
    (is (= {:insts d18-s00 :pos -1 "a" 1 :snd 4}
           (d18/step-p1 {:insts d18-s00 :pos 6 "a" 1 :snd 4})))
    (is (= 4
           (d18/step-p1 {:insts d18-s00 :pos -1 "a" 1 :snd 4})))))

(deftest execute-p1-test
  (testing "Runs the instructions and returns the value recovered"
    (is (= 4 (d18/execute-p1 d18-s00)))))

(def day18-input (u/parse-puzzle-input d18/parse 2017 18))

(deftest part1-test
  (testing "Reproduces the answer for day18, part1"
    (is (= 9423 (d18/part1 day18-input)))))