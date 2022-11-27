(ns aoc-clj.2019.day19-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2019.day19 :as t]))

(deftest day19-part1-test
  (testing "Can reproduce the solution for part1"
    (is (= 150 (t/day19-part1-soln)))))

(deftest day19-part2-test
  (testing "Can reproduce the solution for part2"
    (is (= 12201460 (t/day19-part2-soln)))))