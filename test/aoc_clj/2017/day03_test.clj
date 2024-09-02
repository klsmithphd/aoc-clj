(ns aoc-clj.2017.day03-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2017.day03 :as d03]))

(deftest spiral-position-test
  (testing "Finds the coordinate position of numbers laid out in a spiral"
    (is (= [0 0] (d03/spiral-position 1)))
    (is (= [0 1] (d03/spiral-position 4)))
    (is (= [1 -1] (d03/spiral-position 9)))
    (is (= [-1 2] (d03/spiral-position 16)))
    (is (= [2 -2] (d03/spiral-position 25)))))