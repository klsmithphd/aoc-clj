(ns aoc-clj.2019.day21-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2019.day21 :as t]))

(def day21-input (u/parse-puzzle-input t/parse 2019 21))

(deftest day21-part1-soln-test
  (testing "Can reproduce the answer for part1"
    (is (= 19352864 (t/day21-part1-soln day21-input)))))

(deftest day21-part2-soln-test
  (testing "Can reproduce the answer for part2"
    (is (= 1142488337 (t/day21-part2-soln day21-input)))))