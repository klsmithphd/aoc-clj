(ns aoc-clj.2016.day08-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day08 :as d08]))

(def d08-s00-raw
  ["rect 3x2"
   "rotate column x=1 by 1"
   "rotate row y=0 by 4"
   "rotate column x=1 by 1"])

(def d08-s00
  [{:cmd :rect, :width 3, :height 2}
   {:cmd :rotate, :type :column, :pos 1, :amount 1}
   {:cmd :rotate, :type :row, :pos 0, :amount 4}
   {:cmd :rotate, :type :column, :pos 1, :amount 1}])

(def d08-s00-grid-0
  {[0 0] 0 [1 0] 0 [2 0] 0 [3 0] 0 [4 0] 0 [5 0] 0 [6 0] 0
   [0 1] 0 [1 1] 0 [2 1] 0 [3 1] 0 [4 1] 0 [5 1] 0 [6 1] 0
   [0 2] 0 [1 2] 0 [2 2] 0 [3 2] 0 [4 2] 0 [5 2] 0 [6 2] 0})

(def d08-s00-grid-1
  {[0 0] 1 [1 0] 1 [2 0] 1 [3 0] 0 [4 0] 0 [5 0] 0 [6 0] 0
   [0 1] 1 [1 1] 1 [2 1] 1 [3 1] 0 [4 1] 0 [5 1] 0 [6 1] 0
   [0 2] 0 [1 2] 0 [2 2] 0 [3 2] 0 [4 2] 0 [5 2] 0 [6 2] 0})

(def d08-s00-grid-2
  {[0 0] 1 [1 0] 0 [2 0] 1 [3 0] 0 [4 0] 0 [5 0] 0 [6 0] 0
   [0 1] 1 [1 1] 1 [2 1] 1 [3 1] 0 [4 1] 0 [5 1] 0 [6 1] 0
   [0 2] 0 [1 2] 1 [2 2] 0 [3 2] 0 [4 2] 0 [5 2] 0 [6 2] 0})

(def d08-s00-grid-3
  {[0 0] 0 [1 0] 0 [2 0] 0 [3 0] 0 [4 0] 1 [5 0] 0 [6 0] 1
   [0 1] 1 [1 1] 1 [2 1] 1 [3 1] 0 [4 1] 0 [5 1] 0 [6 1] 0
   [0 2] 0 [1 2] 1 [2 2] 0 [3 2] 0 [4 2] 0 [5 2] 0 [6 2] 0})

(def d08-s00-grid-4
  {[0 0] 0 [1 0] 1 [2 0] 0 [3 0] 0 [4 0] 1 [5 0] 0 [6 0] 1
   [0 1] 1 [1 1] 0 [2 1] 1 [3 1] 0 [4 1] 0 [5 1] 0 [6 1] 0
   [0 2] 0 [1 2] 1 [2 2] 0 [3 2] 0 [4 2] 0 [5 2] 0 [6 2] 0})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d08-s00 (d08/parse d08-s00-raw)))))

(deftest step-test
  (testing "Updates the grid with each instruction"
    (is (= d08-s00-grid-1 (d08/step d08-s00-grid-0 (nth d08-s00 0))))
    (is (= d08-s00-grid-2 (d08/step d08-s00-grid-1 (nth d08-s00 1))))
    (is (= d08-s00-grid-3 (d08/step d08-s00-grid-2 (nth d08-s00 2))))
    (is (= d08-s00-grid-4 (d08/step d08-s00-grid-3 (nth d08-s00 3))))))

(deftest final-state-test
  (testing "Can apply the instructions for the sample data"
    (is (= #{[1 0] [4 0] [6 0] [0 1] [2 1] [1 2]}
           (->>  (d08/final-state 7 3 d08-s00)
                 (filter #(pos? (val %)))
                 keys
                 set)))))

(def day08-input (u/parse-puzzle-input d08/parse 2016 8))

(deftest part1-test
  (testing "Reproduces the answer for day08, part1"
    (is (= 128 (d08/part1 day08-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day08, part2"
    (is (= "EOARGPHYAO" (d08/part2 day08-input)))))