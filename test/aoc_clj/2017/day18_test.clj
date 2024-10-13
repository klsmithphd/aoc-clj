(ns aoc-clj.2017.day18-test
  (:require [clojure.test :refer [deftest testing is]]
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

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d18-s00 (d18/parse d18-s00-raw)))))