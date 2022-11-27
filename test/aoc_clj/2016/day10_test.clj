(ns aoc-clj.2016.day10-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2016.day10 :as t]))

(def day10-sample
  (t/initialize
   ["value 5 goes to bot 2"
    "bot 2 gives low to bot 1 and high to bot 0"
    "value 3 goes to bot 1"
    "bot 1 gives low to output 1 and high to bot 0"
    "bot 0 gives low to output 2 and high to output 0"
    "value 2 goes to bot 2"]))

(deftest bot-that-compares
  (testing "Find the bots that will compare the values in the sample data"
    (is (= 2 (t/bot-that-compares day10-sample #{2 5})))
    (is (= 1 (t/bot-that-compares day10-sample #{2 3})))
    (is (= 0 (t/bot-that-compares day10-sample #{3 5})))))


(deftest day10-part1-soln
  (testing "Reproduces the answer for day10, part1"
    (is (= 93 (t/day10-part1-soln)))))

(deftest day10-part2-soln
  (testing "Reproduces the answer for day10, part2"
    (is (= 47101 (t/day10-part2-soln)))))