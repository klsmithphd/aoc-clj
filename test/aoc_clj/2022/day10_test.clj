(ns aoc-clj.2022.day10-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2022.day10 :as t]))

(def d10-01
  (t/parse
   ["noop"
    "addx 3"
    "addx -5"]))

(def d10-02
  (t/parse
   ["addx 15"
    "addx -11"
    "addx 6"
    "addx -3"
    "addx 5"
    "addx -1"
    "addx -8"
    "addx 13"
    "addx 4"
    "noop"
    "addx -1"
    "addx 5"
    "addx -1"
    "addx 5"
    "addx -1"
    "addx 5"
    "addx -1"
    "addx 5"
    "addx -1"
    "addx -35"
    "addx 1"
    "addx 24"
    "addx -19"
    "addx 1"
    "addx 16"
    "addx -11"
    "noop"
    "noop"
    "addx 21"
    "addx -15"
    "noop"
    "noop"
    "addx -3"
    "addx 9"
    "addx 1"
    "addx -3"
    "addx 8"
    "addx 1"
    "addx 5"
    "noop"
    "noop"
    "noop"
    "noop"
    "noop"
    "addx -36"
    "noop"
    "addx 1"
    "addx 7"
    "noop"
    "noop"
    "noop"
    "addx 2"
    "addx 6"
    "noop"
    "noop"
    "noop"
    "noop"
    "noop"
    "addx 1"
    "noop"
    "noop"
    "addx 7"
    "addx 1"
    "noop"
    "addx -13"
    "addx 13"
    "addx 7"
    "noop"
    "addx 1"
    "addx -33"
    "noop"
    "noop"
    "noop"
    "addx 2"
    "noop"
    "noop"
    "noop"
    "addx 8"
    "noop"
    "addx -1"
    "addx 2"
    "addx 1"
    "noop"
    "addx 17"
    "addx -9"
    "addx 1"
    "addx 1"
    "addx -3"
    "addx 11"
    "noop"
    "noop"
    "addx 1"
    "noop"
    "addx 1"
    "noop"
    "noop"
    "addx -13"
    "addx -19"
    "addx 1"
    "addx 3"
    "addx 26"
    "addx -30"
    "addx 12"
    "addx -1"
    "addx 3"
    "addx 1"
    "noop"
    "noop"
    "noop"
    "addx -9"
    "addx 18"
    "addx 1"
    "addx 2"
    "noop"
    "noop"
    "addx 9"
    "noop"
    "noop"
    "noop"
    "addx -1"
    "addx 2"
    "addx -37"
    "addx 1"
    "addx 3"
    "noop"
    "addx 15"
    "addx -21"
    "addx 22"
    "addx -6"
    "addx 1"
    "noop"
    "addx 2"
    "addx 1"
    "noop"
    "addx -10"
    "noop"
    "noop"
    "addx 20"
    "addx 1"
    "addx 2"
    "addx 2"
    "addx -6"
    "addx -11"
    "noop"
    "noop"
    "noop"]))

(def d10-02-screen
  "##..##..##..##..##..##..##..##..##..##..
###...###...###...###...###...###...###.
####....####....####....####....####....
#####.....#####.....#####.....#####.....
######......######......######......####
#######.......#######.......#######.....")

(deftest parse-test
  (testing "Correctly parses the sample input"
    (is (= d10-01
           [::t/noop 3 -5]))))

(deftest register-values-test
  (testing "Computes the register value over time given the instructions"
    (is (= [1 1 1 4 4 -1] (t/register-values d10-01)))))

(deftest sampled-signal-strength-sums-test
  (testing "Computes the sum of the signal strengths sampled every 40 ticks"
    (is (= 13140 (t/sampled-signal-strength-sums d10-02)))))

(deftest screen-test
  (testing "The screen lighting logic produces the test pattern"
    (is (= d10-02-screen (t/screen d10-02)))))

(deftest day10-part1-soln
  (testing "Reproduces the answer for day10, part1"
    (is (= 16020 (t/day10-part1-soln)))))

(deftest day10-part2-soln
  (testing "Reproduces the answer for day10, part2"
    (is (= "ECZUZALR" (t/day10-part2-soln)))))