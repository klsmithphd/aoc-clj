(ns aoc-clj.2018.day18-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.grid.vecgrid :as vg]
            [aoc-clj.2018.day18 :as d18]))

(def d18-s00-raw
  [".#.#...|#."
   ".....#|##|"
   ".|..|...#."
   "..|#.....#"
   "#.#|||#|#|"
   "...#.||..."
   ".|....|..."
   "||...#|.#|"
   "|.||||..|."
   "...#.|..|."])

(def d18-s00
  (vg/->VecGrid2D
   [[:o :l :o :l :o :o :o :t :l :o]
    [:o :o :o :o :o :l :t :l :l :t]
    [:o :t :o :o :t :o :o :o :l :o]
    [:o :o :t :l :o :o :o :o :o :l]
    [:l :o :l :t :t :t :l :t :l :t]
    [:o :o :o :l :o :t :t :o :o :o]
    [:o :t :o :o :o :o :t :o :o :o]
    [:t :t :o :o :o :l :t :o :l :t]
    [:t :o :t :t :t :t :o :o :t :o]
    [:o :o :o :l :o :t :o :o :t :o]]))

(def d18-s00-step1
  (vg/->VecGrid2D
   [[:o :o :o :o :o :o :o :l :l :o]
    [:o :o :o :o :o :o :t :l :l :l]
    [:o :t :o :o :t :o :o :o :l :o]
    [:o :o :t :l :t :t :o :o :o :l]
    [:o :o :l :l :t :t :o :t :l :t]
    [:o :o :o :l :t :t :t :t :o :o]
    [:t :t :o :o :o :t :t :t :o :o]
    [:t :t :t :t :t :o :t :t :o :t]
    [:t :t :t :t :t :t :t :t :t :t]
    [:o :o :o :o :t :t :o :o :t :o]]))

(def d18-s00-step10
  (vg/->VecGrid2D
   [[:o :t :t :l :l :o :o :o :o :o]
    [:t :t :l :l :l :o :o :o :o :o]
    [:t :t :l :l :o :o :o :o :o :o]
    [:t :l :l :o :o :o :o :o :l :l]
    [:t :l :l :o :o :o :o :o :l :l]
    [:t :l :l :o :o :o :o :l :l :t]
    [:t :t :l :l :o :l :l :l :l :t]
    [:t :t :l :l :l :l :l :t :t :t]
    [:t :t :t :t :l :t :t :t :t :t]
    [:t :t :t :t :t :t :t :t :t :t]]))

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d18-s00 (d18/parse d18-s00-raw)))))

(deftest step-test
  (testing "Updates the state by one step"
    (is (= d18-s00-step1 (d18/step d18-s00)))))

(deftest state-at-t-test
  (testing "Returns the state at a given time"
    (is (= d18-s00        (d18/state-at-t d18-s00 0)))
    (is (= d18-s00-step1  (d18/state-at-t d18-s00 1)))
    (is (= d18-s00-step10 (d18/state-at-t d18-s00 10)))))

(deftest resource-value-test
  (testing "Computes the product of lumberyard and tree acres"
    (is (= 1147 (d18/resource-value d18-s00-step10)))))

(def day18-input (u/parse-puzzle-input d18/parse 2018 18))

(deftest part1-test
  (testing "Reproduces the answer for day18, part1"
    (is (= 394420 (d18/part1 day18-input)))))
