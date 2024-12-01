(ns aoc-clj.2018.day21-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2018.day21 :as d21]))

(def day21-input (u/parse-puzzle-input d21/parse 2018 21))

(deftest equiv-program-test
  (testing "Confirms that my equivalent program returns
            the same sequence as the opcode program"
    (is (= (take 10 (d21/only-halt-points d21/init-regs day21-input))
           (take 10 (rest (iterate d21/equiv-program 0)))))))

(deftest part1-test
  (testing "Reproduces the answer for day21, part1"
    (is (= 10846352 (d21/part1 day21-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day21, part2"
    (is (= 5244670 (d21/part2 day21-input)))))