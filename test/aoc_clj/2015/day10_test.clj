(ns aoc-clj.2015.day10-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day10 :as d10]))

(deftest look-and-say
  (testing "Returns next string given input"
    (is (= "11" (d10/look-and-say "1")))
    (is (= "21" (d10/look-and-say "11")))
    (is (= "1211" (d10/look-and-say "21")))
    (is (= "111221" (d10/look-and-say "1211")))
    (is (= "312211" (d10/look-and-say "111221")))))

(def day10-input (u/parse-puzzle-input d10/parse 2015 10))

(deftest part1-test
  (testing "Reproduces the answer for day10, part1"
    (is (= 252594 (d10/part1 day10-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day10, part2"
    (is (= 3579328 (d10/part2 day10-input)))))