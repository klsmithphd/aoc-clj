(ns aoc-clj.2023.day14-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.grid.vecgrid :as vg]
            [aoc-clj.2023.day14 :as t]))

(def d14-s01-raw
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

(def d14-s01
  (vg/->VecGrid2D
   [[:r :s :s :s :s :w :s :s :s :s]
    [:r :s :r :r :w :s :s :s :s :w]
    [:s :s :s :s :s :w :w :s :s :s]
    [:r :r :s :w :r :s :s :s :s :r]
    [:s :r :s :s :s :s :s :r :w :s]
    [:r :s :w :s :s :r :s :w :s :w]
    [:s :s :r :s :s :w :r :s :s :r]
    [:s :s :s :s :s :s :s :r :s :s]
    [:w :s :s :s :s :w :w :w :s :s]
    [:w :r :r :s :s :w :s :s :s :s]]))

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d14-s01 (t/parse d14-s01-raw)))))

(def d14-s01-north
  (t/parse
   ["OOOO.#.O.."
    "OO..#....#"
    "OO..O##..O"
    "O..#.OO..."
    "........#."
    "..#....#.#"
    "..O..#.O.O"
    "..O......."
    "#....###.."
    "#....#...."]))

(def d14-s01-cycle1
  (t/parse
   [".....#...."
    "....#...O#"
    "...OO##..."
    ".OO#......"
    ".....OOO#."
    ".O#...O#.#"
    "....O#...."
    "......OOOO"
    "#...O###.."
    "#..OO#...."]))

(def d14-s01-cycle2
  (t/parse
   [".....#...."
    "....#...O#"
    ".....##..."
    "..O#......"
    ".....OOO#."
    ".O#...O#.#"
    "....O#...O"
    ".......OOO"
    "#..OO###.."
    "#.OOO#...O"]))

(def d14-s01-cycle3
  (t/parse
   [".....#...."
    "....#...O#"
    ".....##..."
    "..O#......"
    ".....OOO#."
    ".O#...O#.#"
    "....O#...O"
    ".......OOO"
    "#...O###.O"
    "#.OOO#...O"]))

(deftest roll-north-test
  (testing "Modifies the input to have the round rocks roll north"
    (is (= d14-s01-north (t/roll-north-solo d14-s01)))))

(deftest spin-cycle-test
  (testing "Runs the input data through a spin cycle (N-W-S-E)"
    (is (= d14-s01-cycle1 (-> d14-s01 t/spin-cycle)))
    (is (= d14-s01-cycle2 (-> d14-s01 t/spin-cycle t/spin-cycle)))
    (is (= d14-s01-cycle3 (-> d14-s01 t/spin-cycle t/spin-cycle t/spin-cycle)))))

(deftest total-load-test
  (testing "Computes the total load of the rocks"
    (is (= 136 (t/total-load (t/roll-north-solo d14-s01))))))

(deftest round-rock-positions-test
  (testing "Returns the positions of the round rocks"
    (is (= #{[8 1] [3 2] [4 2] [1 3] [2 3] [5 4] [6 4] [7 4] [1 5]
             [6 5] [4 6] [6 7] [7 7] [8 7] [9 7] [4 8] [3 9] [4 9]}
           (t/round-rock-positions d14-s01-cycle1)))
    (is (= #{[8 1] [2 3] [7 4] [5 4] [6 4] [6 5] [1 5] [4 6] [9 6]
             [7 7] [8 7] [9 7] [3 8] [4 8] [2 9] [3 9] [4 9] [9 9]}
           (t/round-rock-positions d14-s01-cycle2)))))

(deftest spin-cycle-params-test
  (testing "Determines when the spin cycle begins repeating itself"
    (is (= [3 7] (t/spin-cycle-params d14-s01)))))

(deftest total-load-after-n-spin-cycles-test
  (testing "Determines the total load after n spin cycles"
    (is (= 64 (t/total-load-after-n-spin-cycles d14-s01 1000000000)))))

(def day14-input (u/parse-puzzle-input t/parse 2023 14))

(deftest day14-part1-soln
  (testing "Reproduces the answer for day14, part1"
    (is (= 55172 (t/day14-part1-soln day14-input)))))

(deftest day14-part2-soln
  (testing "Reproduces the answer for day14, part2"
    (is (= 106390 (t/day14-part2-soln day14-input)))))