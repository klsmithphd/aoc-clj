(ns aoc-clj.2023.day16-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.grid.vecgrid :as vg]
            [aoc-clj.2023.day16 :as t]))

(def d16-s00-raw
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

(def d16-s00
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
    (is (= d16-s00 (t/parse d16-s00-raw)))))

(deftest next-beams-test
  (testing "Determines the next position and heading of a light beam"
    (is (= #{[[1 0] :R]}             (t/next-beams d16-s00 [[0 0] :R])))
    (is (= #{[[1 1] :D]}             (t/next-beams d16-s00 [[1 0] :R])))
    (is (= #{[[0 7] :L] [[2 7] :R]}  (t/next-beams d16-s00 [[1 7] :D])))
    (is (= #{[[4 7] :R]}             (t/next-beams d16-s00 [[3 7] :R])))
    (is (= #{[[4 6] :U]}             (t/next-beams d16-s00 [[4 7] :R])))
    (is (= #{[[5 6] :R]}             (t/next-beams d16-s00 [[4 6] :U])))))

(deftest energized-count-test
  (testing "Counts the number of energized cells"
    (is (= 46 (t/energized-count d16-s00 [[0 0] :R])))
    (is (= 51 (t/energized-count d16-s00 [[3 0] :D])))))

(deftest max-energization-test
  (testing "Finds the maximum possible number of energized cells"
    (is (= 51 (t/max-energization d16-s00)))))

(def day16-input (u/parse-puzzle-input t/parse 2023 16))

(deftest part1-test
  (testing "Reproduces the answer for day16, part1"
    (is (= 8034 (t/part1 day16-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day16, part2"
    (is (= 8225 (t/part2 day16-input)))))
