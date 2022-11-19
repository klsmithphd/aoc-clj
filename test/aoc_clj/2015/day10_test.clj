(ns aoc-clj.2015.day10-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2015.day10 :as t]))

(deftest look-and-say
  (testing "Returns next string given input"
    (is (= "11" (t/look-and-say "1")))
    (is (= "21" (t/look-and-say "11")))
    (is (= "1211" (t/look-and-say "21")))
    (is (= "111221" (t/look-and-say "1211")))))

(deftest day10-part1-soln
  (testing "Reproduces the answer for day10, part1"
    (is (= 252594 (t/day10-part1-soln)))))

(deftest day10-part2-soln
  (testing "Reproduces the answer for day10, part2"
    (is (= 3579328 (t/day10-part2-soln)))))