(ns aoc-clj.2019.day02-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2019.day02 :as t]))

(def day02-input (u/parse-puzzle-input t/parse 2019 2))

(deftest day02-part1-soln-test
  (testing "Can reproduce the answer for part1"
    (is (= 3085697 (t/day02-part1-soln day02-input)))))

(deftest day02-part2-soln-test
  (testing "Can reproduce the answer for part2"
    (is (= 9425 (t/day02-part2-soln day02-input)))))