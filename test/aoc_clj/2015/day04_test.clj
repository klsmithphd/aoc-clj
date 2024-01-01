(ns aoc-clj.2015.day04-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day04 :as t]))

;; FIXME: 2015.day04 solution is too slow to unit test
;; https://github.com/Ken-2scientists/aoc-clj/issues/1
;; SUPER SLOW!
(deftest ^:slow first-with-five-zeros
  (testing "Finds the earliest number to result in md5 hash starting with five zeros"
    (is (= 609043  (t/first-to-start-with-five-zeros "abcdef")))
    (is (= 1048970 (t/first-to-start-with-five-zeros "pqrstuv")))))

(def day04-input (u/parse-puzzle-input t/parse 2015 4))

(deftest ^:slow day04-part1-soln
  (testing "Reproduces the answer for day04, part1"
    (is (= 282749 (t/day04-part1-soln day04-input)))))

;; ULTRA SLOW!
(deftest ^:slow day04-part2-soln
  (testing "Reproduces the answer for day04, part2"
    (is (= 9962624 (t/day04-part2-soln day04-input)))))