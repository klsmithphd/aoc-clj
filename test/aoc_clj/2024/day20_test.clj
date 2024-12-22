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
      (is (= [4]     (d20/cheat-savings d20/p1-range fullpath [[1 11] 0])))
      (is (= [12]    (d20/cheat-savings d20/p1-range fullpath [[7 13] 12])))
      (is (= [20 36] (d20/cheat-savings d20/p1-range fullpath [[9 7] 20])))
      (is (= [64 40] (d20/cheat-savings d20/p1-range fullpath [[7 7] 18]))))))

(deftest cheats-more-than-test
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
           (frequencies (d20/cheats-more-than d20/p1-range 0 d20-s00))))

    (is (= {50 32
            52 31
            54 29
            56 39
            58 25
            60 23
            62 20
            64 19
            66 12
            68 14
            70 12
            72 22
            74 4
            76 3}
           (frequencies (d20/cheats-more-than d20/p2-range 50 d20-s00))))))

(def day20-input (u/parse-puzzle-input d20/parse 2024 20))

(deftest part1-test
  (testing "Reproduces the answer for day20, part1"
    (is (= 1402 (d20/part1 day20-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day20, part2"
    (is (= 1020244 (d20/part2 day20-input)))))