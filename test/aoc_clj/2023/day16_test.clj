(ns aoc-clj.2023.day16-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.grid.vecgrid :as vg]
            [aoc-clj.2023.day16 :as t]))

(def d16-s01-raw
  [".|...\\...."
   "|.-.\\....."
   ".....|-..."
   "........|."
   ".........."
   ".........\\"
   "..../.\\\\.."
   ".-.-/..|.."
   ".|....-|.\\"
   "..//.|...."])

(def d16-s01
  (vg/->VecGrid2D
   [[:empty :spltv :empty :empty :empty :mrrr1 :empty :empty :empty :empty]
    [:spltv :empty :splth :empty :mrrr1 :empty :empty :empty :empty :empty]
    [:empty :empty :empty :empty :empty :spltv :splth :empty :empty :empty]
    [:empty :empty :empty :empty :empty :empty :empty :empty :spltv :empty]
    [:empty :empty :empty :empty :empty :empty :empty :empty :empty :empty]
    [:empty :empty :empty :empty :empty :empty :empty :empty :empty :mrrr1]
    [:empty :empty :empty :empty :mrrr2 :empty :mrrr1 :mrrr1 :empty :empty]
    [:empty :splth :empty :splth :mrrr2 :empty :empty :spltv :empty :empty]
    [:empty :spltv :empty :empty :empty :empty :splth :spltv :empty :mrrr1]
    [:empty :empty :mrrr2 :mrrr2 :empty :spltv :empty :empty :empty :empty]]))

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d16-s01 (t/parse d16-s01-raw)))))

(deftest next-beams-test
  (testing "Determines the next position and heading of a light beam"
    (is (= #{[[1 0] :R]}             (t/next-beams d16-s01 [[0 0] :R])))
    (is (= #{[[1 1] :D]}             (t/next-beams d16-s01 [[1 0] :R])))
    (is (= #{[[0 7] :L] [[2 7] :R]}  (t/next-beams d16-s01 [[1 7] :D])))
    (is (= #{[[4 7] :R]}             (t/next-beams d16-s01 [[3 7] :R])))
    (is (= #{[[4 6] :U]}             (t/next-beams d16-s01 [[4 7] :R])))
    (is (= #{[[5 6] :R]}             (t/next-beams d16-s01 [[4 6] :U])))))

(deftest energized-count-test
  (testing "Counts the number of energized cells"
    (is (= 46 (t/energized-count d16-s01)))))

(def day16-input (u/parse-puzzle-input t/parse 2023 16))

(deftest day16-part1-soln
  (testing "Reproduces the answer for day16, part1"
    (is (= 8034 (t/day16-part1-soln day16-input)))))

