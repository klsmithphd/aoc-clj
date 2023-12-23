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

(def d14-s01-cycle1
  [".....#...."
   "....#...O#"
   "...OO##..."
   ".OO#......"
   ".....OOO#."
   ".O#...O#.#"
   "....O#...."
   "......OOOO"
   "#...O###.."
   "#..OO#...."])

(def d14-s01-cycle2
  [".....#...."
   "....#...O#"
   ".....##..."
   "..O#......"
   ".....OOO#."
   ".O#...O#.#"
   "....O#...O"
   ".......OOO"
   "#..OO###.."
   "#.OOO#...O"])

(def d14-s01-cycle3
  [".....#...."
   "....#...O#"
   ".....##..."
   "..O#......"
   ".....OOO#."
   ".O#...O#.#"
   "....O#...O"
   ".......OOO"
   "#...O###.O"
   "#.OOO#...O"])

(deftest roll-north-test
  (testing "Modifies the input to have the round rocks roll north"
    (is (= d14-s01-north (map #(apply str %) (t/roll-north-solo d14-s01))))))

(deftest spin-cycle-test
  (testing "Runs the input data through a spin cycle (N-W-S-E)"
    (is (= d14-s01-cycle1 (map #(apply str %)
                               (t/spin-cycle d14-s01))))
    (is (= d14-s01-cycle2 (map #(apply str %)
                               (t/spin-cycle (t/spin-cycle d14-s01)))))
    (is (= d14-s01-cycle3 (map #(apply str %)
                               (t/spin-cycle (t/spin-cycle (t/spin-cycle d14-s01))))))))

(deftest total-load-test
  (testing "Computes the total load of the rocks"
    (is (= 136 (t/total-load d14-s01-north)))))

(def day14-input (u/parse-puzzle-input t/parse 2023 14))

(deftest day14-part1-soln
  (testing "Reproduces the answer for day14, part1"
    (is (= 55172 (t/day14-part1-soln day14-input)))))
