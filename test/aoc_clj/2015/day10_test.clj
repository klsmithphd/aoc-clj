(ns aoc-clj.2015.day10-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day10 :as t]))

(deftest look-and-say
  (testing "Returns next string given input"
    (is (= "11" (t/look-and-say "1")))
    (is (= "21" (t/look-and-say "11")))
    (is (= "1211" (t/look-and-say "21")))
    (is (= "111221" (t/look-and-say "1211")))))

(def day10-input (u/parse-puzzle-input t/parse 2015 10))

(deftest part1-test
  (testing "Reproduces the answer for day10, part1"
    (is (= 252594 (t/part1 day10-input)))))

;; FIXME: 2015.day10 solution is slow
;; https://github.com/Ken-2scientists/aoc-clj/issues/5
(deftest ^:slow part2
  (testing "Reproduces the answer for day10, part2"
    (is (= 3579328 (t/part2 day10-input)))))