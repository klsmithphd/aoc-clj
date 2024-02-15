(ns aoc-clj.2015.day23-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day23 :as d23]))

(def d23-s00-raw
  ["inc a"
   "jio a, +2"
   "tpl a"
   "inc a"])

(def d23-s00
  [{:inst :inc :arg1 :a}
   {:inst :jio :arg1 :a :arg2 2}
   {:inst :tpl :arg1 :a}
   {:inst :inc :arg1 :a}])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d23-s00 (d23/parse d23-s00-raw)))))

(deftest run-program-test
  (testing "Runs on the simple input"
    (is (= {:a 2 :b 0 :next-inst 4} (d23/run-program d23-s00 d23/init-state)))))

(def day23-input (u/parse-puzzle-input d23/parse 2015 23))

(deftest part1-test
  (testing "Reproduces the answer for day23, part1"
    (is (= 184 (d23/part1 day23-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day23, part2"
    (is (= 231 (d23/part2 day23-input)))))