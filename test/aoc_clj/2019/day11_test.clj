(ns aoc-clj.2019.day11-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2019.day11 :as t]))

(def day11-input (u/parse-puzzle-input t/parse 2019 11))

(deftest day11-part1-soln-test
  (testing "Can reproduce the answer for part1"
    (is (= 2539 (t/day11-part1-soln day11-input)))))

(deftest day11-part2-soln-test
  (testing "Can reproduce the answer for part2"
    (is (= "ZLEBKJRA" (t/day11-part2-soln day11-input)))))