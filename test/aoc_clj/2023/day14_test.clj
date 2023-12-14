(ns aoc-clj.2023.day14-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2023.day14 :as t]))

(def d14-s01
  ["O....#...."
   "O.OO#....#"
   ".....##..."
   "OO.#O....O"
   ".O.....O#."
   "O.#..O.#.#"
   "..O..#O..O"
   ".......O.."
   "#....###.."
   "#OO..#...."])

(def d14-s01-north
  ["OOOO.#.O.."
   "OO..#....#"
   "OO..O##..O"
   "O..#.OO..."
   "........#."
   "..#....#.#"
   "..O..#.O.O"
   "..O......."
   "#....###.."
   "#....#...."])

(deftest roll-north-test
  (testing "Modifies the input to have the round rocks roll north"
    (is (= d14-s01-north (t/roll-north d14-s01)))))

(deftest total-load-test
  (testing "Computes the total load of the rocks"
    (is (= 136 (t/total-load d14-s01-north)))))

(def day14-input (u/parse-puzzle-input t/parse 2023 14))

(deftest day14-part1-soln
  (testing "Reproduces the answer for day14, part1"
    (is (= 55172 (t/day14-part1-soln day14-input)))))
