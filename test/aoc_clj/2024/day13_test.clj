(ns aoc-clj.2024.day13-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2024.day13 :as d13]))

(def d13-s00-raw
  ["Button A: X+94, Y+34"
   "Button B: X+22, Y+67"
   "Prize: X=8400, Y=5400"
   ""
   "Button A: X+26, Y+66"
   "Button B: X+67, Y+21"
   "Prize: X=12748, Y=12176"
   ""
   "Button A: X+17, Y+86"
   "Button B: X+84, Y+37"
   "Prize: X=7870, Y=6450"
   ""
   "Button A: X+69, Y+23"
   "Button B: X+27, Y+71"
   "Prize: X=18641, Y=10279"])

(def d13-s00
  [[94 34 22 67 8400 5400]
   [26 66 67 21 12748 12176]
   [17 86 84 37 7870 6450]
   [69 23 27 71 18641 10279]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d13-s00 (d13/parse d13-s00-raw)))))