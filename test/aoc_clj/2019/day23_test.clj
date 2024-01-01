(ns aoc-clj.2019.day23-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2019.day23 :as t]))

(def day23-input (u/parse-puzzle-input t/parse 2019 23))

(deftest day23-part1-soln-test
  (testing "Can reproduce the answer for part1"
    (is (= 23886 (t/day23-part1-soln day23-input)))))

(deftest day21-part2-soln-test
  (testing "Can reproduce the answer for part2"
    (is (= 18333 (t/day23-part2-soln day23-input)))))