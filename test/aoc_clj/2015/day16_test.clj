(ns aoc-clj.2015.day16-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day16 :as d16]))

(def d16-s00-raw
  ["Sue 1: children: 1, cars: 8, vizslas: 7"
   "Sue 2: akitas: 10, perfumes: 10, children: 5"
   "Sue 3: cars: 5, pomeranians: 4, vizslas: 1"
   "Sue 4: goldfish: 5, children: 8, perfumes: 3"
   "Sue 5: vizslas: 2, akitas: 7, perfumes: 6"])

(def d16-s00
  {1 {:children 1 :cars 8 :vizslas 7}
   2 {:akitas 10  :perfumes 10 :children 5}
   3 {:cars 5 :pomeranians 4 :vizslas 1}
   4 {:goldfish 5 :children 8 :perfumes 3}
   5 {:vizslas 2 :akitas 7 :perfumes 6}})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d16-s00 (d16/parse d16-s00-raw)))))

(def day16-input (u/parse-puzzle-input d16/parse 2015 16))

(deftest part1-test
  (testing "Reproduces the answer for day16, part1"
    (is (= 213 (d16/part1 day16-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day16, part2"
    (is (= 323 (d16/part2 day16-input)))))