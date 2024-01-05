(ns aoc-clj.2020.day12-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2020.day12 :as t]))

(def day12-sample
  (t/parse ["F10" "N3" "F7" "R90" "F11"]))

(deftest part1-sample
  (testing "Reproduces the answer for part1 on the sample input"
    (is (= 25 (t/final-distance day12-sample t/exec-cmd)))))

(deftest part2-sample
  (testing "Reproduces the answer for part2 on the sample input"
    (is (= 286 (t/final-distance day12-sample t/exec-cmd2)))))

(def day12-input (u/parse-puzzle-input t/parse 2020 12))

(deftest part1-test
  (testing "Reproduces the answer for day12, part1"
    (is (= 1533 (t/part1 day12-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day12, part2"
    (is (= 25235 (t/part2 day12-input)))))