(ns aoc-clj.2015.day07-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day07 :as t]))

(def d07-s00-raw
  ["123 -> x"
   "456 -> y"
   "x AND y -> d"
   "x OR y -> e"
   "x LSHIFT 2 -> f"
   "y RSHIFT 2 -> g"
   "NOT x -> h"
   "NOT y -> i"
   ;; Added to the sample to reflect what's in the real puzzle data
   "f -> j"])

(def d07-s00
  {"x" {:op :assign :args 123}
   "y" {:op :assign :args 456}
   "d" {:op :and    :args ["x" "y"]}
   "e" {:op :or     :args ["x" "y"]}
   "f" {:op :lshift :args ["x" 2]}
   "g" {:op :rshift :args ["y" 2]}
   "h" {:op :not    :args ["x"]}
   "i" {:op :not    :args ["y"]}
   "j" {:op :assign :args "f"}})

(deftest parse-test
  (testing "Parser logic works correctly"
    (is (= d07-s00 (t/parse d07-s00-raw)))))

(deftest wire-val-test
  (testing "Computes the wire values correctly"
    (is (= (t/wire-val d07-s00 "d") 72))
    (is (= (t/wire-val d07-s00 "e") 507))
    (is (= (t/wire-val d07-s00 "f") 492))
    (is (= (t/wire-val d07-s00 "g") 114))
    (is (= (t/wire-val d07-s00 "h") 65412))
    (is (= (t/wire-val d07-s00 "i") 65079))
    (is (= (t/wire-val d07-s00 "x") 123))
    (is (= (t/wire-val d07-s00 "y") 456))
    (is (= (t/wire-val d07-s00 "j") 492))))

(def day07-input (u/parse-puzzle-input t/parse 2015 7))

(deftest part1-test
  (testing "Reproduces the answer for day07, part1"
    (is (= 3176 (t/part1 day07-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day07, part2"
    (is (= 14710 (t/part2 day07-input)))))