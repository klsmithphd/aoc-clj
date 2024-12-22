(ns aoc-clj.2024.day20-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2024.day20 :as d20]))

(def d20-s00
  (d20/parse
   ["###############"
    "#...#...#.....#"
    "#.#.#.#.#.###.#"
    "#S#...#.#.#...#"
    "#######.#.#.###"
    "#######.#.#...#"
    "#######.#.###.#"
    "###..E#...#...#"
    "###.#######.###"
    "#...###...#...#"
    "#.#####.#.###.#"
    "#.#...#.#.#...#"
    "#.#.#.#.#.#.###"
    "#...#...#...###"
    "###############"]))

(deftest full-path-test
  (testing "Finds the full set of positions from start to finish"
    (is (= 85 (count (d20/full-path d20-s00))))))

(deftest cheat-savings-test
  (testing "Finds how much time can be saved by various cheats"
    (let [fullpath (d20/full-path d20-s00)]
      (is (= [4] (d20/cheat-savings fullpath [[1 11] 0])))
      (is (= [12] (d20/cheat-savings fullpath [[7 13] 12])))
      (is (= [20 36] (d20/cheat-savings fullpath [[9 7] 20])))
      (is (= [64 40] (d20/cheat-savings fullpath [[7 7] 18]))))))

(deftest cheat-freqs-test
  (testing "Computes a histogram of the possible cheat savings opportunities"
    (is (= {2 14
            4 14
            6 2
            8 4
            10 2
            12 3
            20 1
            36 1
            38 1
            40 1
            64 1}
           (d20/cheat-freqs d20-s00)))))

(def day20-input (u/parse-puzzle-input d20/parse 2024 20))

(deftest part1-test
  (testing "Reproduces the answer for day20, part1"
    (is (= 1402 (d20/part1 day20-input)))))