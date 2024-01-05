(ns aoc-clj.2019.day11-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2019.day11 :as t]))

(def day11-input (u/parse-puzzle-input t/parse 2019 11))

(deftest part1-test
  (testing "Can reproduce the answer for part1"
    (is (= 2539 (t/part1 day11-input)))))

(deftest part2-test
  (testing "Can reproduce the answer for part2"
    (is (= "ZLEBKJRA" (t/part2 day11-input)))))