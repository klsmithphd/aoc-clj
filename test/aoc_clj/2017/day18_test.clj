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
    (is (= {:regs {"a" 10} :pos 1}
           (d18/add-cmd {:regs {"a" 0} :pos 0} ["a" 10])))
    (is (= {:regs {"a" 7 "b" 4} :pos 1}
           (d18/add-cmd {:regs {"a" 3 "b" 4} :pos 0} ["a" "b"])))))

(deftest jgz-cmd-test
  (testing "`jgz X Y` jumps with an offset of the value of Y, only if
            the value of X is greater than zero"
    (is (= {:regs {"x" 0} :pos 1}
           (d18/jgz-cmd {:regs {"x" 0} :pos 0} ["x" 10])))
    (is (= {:regs {"x" 1} :pos 10}
           (d18/jgz-cmd {:regs {"x" 1} :pos 0} ["x" 10])))))

(deftest mod-cmd-test
  (testing "`mod X Y` sets register X to the remainder dividing X by Y"
    (is (= {:regs {"a" 2} :pos 1}
           (d18/mod-cmd {:regs {"a" 5} :pos 0} ["a" 3])))
    (is (= {:regs {"a" 3 "b" 4} :pos 1}
           (d18/mod-cmd {:regs {"a" 7 "b" 4} :pos 0} ["a" "b"])))))

(deftest mul-cmd-test
  (testing "`mul X Y` sets register X to the product of X and Y"
    (is (= {:regs {"a" 15} :pos 1}
           (d18/mul-cmd {:regs {"a" 5} :pos 0} ["a" 3])))
    (is (= {:regs {"a" 28 "b" 4} :pos 1}
           (d18/mul-cmd {:regs {"a" 7 "b" 4} :pos 0} ["a" "b"])))
    (is (= {:regs {"a" 0} :pos 1}
           (d18/mul-cmd {:regs {} :pos 0} ["a" 5])))))

(deftest set-cmd-test
  (testing "`set X Y` sets register X to the value of Y"
    (is (= {:regs {"a" 1} :pos 1}
           (d18/set-cmd {:regs {} :pos 0} ["a" 1])))
    (is (= {:regs {"a" 10 "b" 10} :pos 1}
           (d18/set-cmd {:regs {"b" 10} :pos 0} ["a" "b"])))))

(deftest part1-rcv-cmd-test
  (testing "`rcv X` recovers the frequency of last sound played, but
            only when X is not zero"
    (is (= {:regs {"a" 0} :pos 1}
           (d18/part1-rcv-cmd {:regs {"a" 0} :pos 0} ["a"])))
    (is (= {:regs {"a" 1} :pos -1 :snd 5}
           (d18/part1-rcv-cmd {:regs {"a" 1} :pos 0 :snd 5} ["a"])))))

(deftest part1-snd-cmd-test
  (testing "`snd X Y` plays a sound with frequency equal to X"
    (is (= {:regs {"a" 5} :pos 1 :snd 5}
           (d18/part1-snd-cmd {:regs {"a" 5} :pos 0} ["a"])))))

(deftest part1-step-test
  (testing "Advances the state by one step from the instructions"
    (is (= {:insts d18-s00 :pos 1 :regs {"a" 1}}
           (d18/part1-step {:insts d18-s00 :pos 0})))
    (is (= {:insts d18-s00 :pos 2 :regs {"a" 3}}
           (d18/part1-step {:insts d18-s00 :pos 1 :regs {"a" 1}})))
    (is (= {:insts d18-s00 :pos 3 :regs {"a" 9}}
           (d18/part1-step {:insts d18-s00 :pos 2 :regs {"a" 3}})))
    (is (= {:insts d18-s00 :pos 4 :regs {"a" 4}}
           (d18/part1-step {:insts d18-s00 :pos 3 :regs {"a" 9}})))
    (is (= {:insts d18-s00 :pos 5 :regs {"a" 4} :snd 4}
           (d18/part1-step {:insts d18-s00 :pos 4 :regs {"a" 4}})))
    (is (= {:insts d18-s00 :pos 6 :regs {"a" 0} :snd 4}
           (d18/part1-step {:insts d18-s00 :pos 5 :regs {"a" 4} :snd 4})))
    (is (= {:insts d18-s00 :pos 7 :regs {"a" 0} :snd 4}
           (d18/part1-step {:insts d18-s00 :pos 6 :regs {"a" 0} :snd 4})))
    (is (= {:insts d18-s00 :pos 8 :regs {"a" 0} :snd 4}
           (d18/part1-step {:insts d18-s00 :pos 7 :regs {"a" 0} :snd 4})))
    (is (= {:insts d18-s00 :pos 9 :regs {"a" 1} :snd 4}
           (d18/part1-step {:insts d18-s00 :pos 8 :regs {"a" 0} :snd 4})))
    (is (= {:insts d18-s00 :pos 7 :regs {"a" 1} :snd 4}
           (d18/part1-step {:insts d18-s00 :pos 9 :regs {"a" 1} :snd 4})))
    (is (= {:insts d18-s00 :pos 6 :regs {"a" 1} :snd 4}
           (d18/part1-step {:insts d18-s00 :pos 7 :regs {"a" 1} :snd 4})))
    (is (= {:insts d18-s00 :pos -1 :regs {"a" 1} :snd 4}
           (d18/part1-step {:insts d18-s00 :pos 6 :regs {"a" 1} :snd 4})))
    (is (= 4
           (d18/part1-step {:insts d18-s00 :pos -1 :regs {"a" 1} :snd 4})))))

(deftest part1-execute-test
  (testing "Runs the instructions and returns the value recovered"
    (is (= 4 (d18/part1-execute d18-s00)))))

(def day18-input (u/parse-puzzle-input d18/parse 2017 18))

(deftest part1-test
  (testing "Reproduces the answer for day18, part1"
    (is (= 9423 (d18/part1 day18-input)))))