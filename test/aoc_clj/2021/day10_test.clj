(ns aoc-clj.2021.day10-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2021.day10 :as t]))

(def d10-s00
  ["[({(<(())[]>[[{[]{<()<>>"
   "[(()[<>])]({[<{<<[]>>("
   "{([(<{}[<>[]}>{[]{[(<()>"
   "(((({<>}<{<{<>}{[]{[]{}"
   "[[<[([]))<([[{}[[()]]]"
   "[{[{({}]{}}([{[{{{}}([]"
   "{<[[]]>}<{[{[{[]{()[[[]"
   "[<(<(<(<{}))><([]([]()"
   "<{([([[(<>()){}]>(<<{{"
   "<{([{{}}[<[[[<>{}]]]>[]]"])

(deftest syntax-error-score
  (testing "Sum of illegal char scores in sample"
    (is (= 26397 (t/syntax-error-score d10-s00)))))

(deftest completion-scores
  (testing "Computes completion scores for sample data"
    (is (= '(288957 5566 1480781 995444 294)
           (t/completion-scores d10-s00)))))

(deftest middle-completion-score
  (testing "Finds the median completion score for sample data"
    (is (= 288957 (t/middle-completion-score d10-s00)))))

(def day10-input (u/parse-puzzle-input t/parse 2021 10))

(deftest day10-part1-soln
  (testing "Reproduces the answer for day10, part1"
    (is (= 394647 (t/day10-part1-soln day10-input)))))

(deftest day10-part2-soln
  (testing "Reproduces the answer for day10, part2"
    (is (= 2380061249 (t/day10-part2-soln day10-input)))))