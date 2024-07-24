(ns aoc-clj.2016.day13-test
  (:require [clojure.test :refer [deftest is testing]] 
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day13 :as t]))

(deftest shortest-path-length-test
  (testing "Finds the length of the shortest path in the sample data"
    (is (= 11 (t/shortest-path-length 10 [7 4])))))

(def day13-input (u/parse-puzzle-input t/parse 2016 13))

(deftest part1-test
  (testing "Reproduces the answer for day13, part1"
    (is (= 90 (t/part1 day13-input)))))

;; (deftest part2-test
;;   (testing "Reproduces the answer for day13, part2"
;;     (is (= 0 (t/part2)))))