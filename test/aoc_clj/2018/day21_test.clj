(ns aoc-clj.2018.day21-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2018.day21 :as d21]))

(def day21-input (u/parse-puzzle-input d21/parse 2018 21))

(deftest part1-test
  (testing "Reproduces the answer for day21, part1"
    (is (= 10846352 (d21/part1 day21-input)))))