(ns aoc-clj.2019.day25-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2019.day25 :as t]))

(deftest day25-part1-soln-test
  (testing "Can reproduce the answer for part1"
    (is (= 16410 (t/day25-part1-soln)))))