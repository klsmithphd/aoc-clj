(ns aoc-clj.2016.day23-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day23 :as d23]))

(def day23-input (u/parse-puzzle-input d23/parse 2016 23))

(deftest part1-test
  (testing "Reproduces the answer for day23, part1"
    (is (= 10890 (d23/part1 day23-input)))))

;; FIXME - Too Slow - https://github.com/klsmithphd/aoc-clj/issues/88
(deftest ^:slow part2-test
  (testing "Reproduces the answer for day23, part2"
    (is (= 10890 (d23/part2 day23-input)))))