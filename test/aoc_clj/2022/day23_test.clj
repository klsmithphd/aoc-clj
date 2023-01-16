(ns aoc-clj.2022.day23-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2022.day23 :as t]))

(def d23-s01
  (t/parse
   ["....."
    "..##."
    "..#.."
    "....."
    "..##."
    "....."]))

(def d23-s01-1
  (t/parse
   ["..##."
    "....."
    "..#.."
    "...#."
    "..#.."
    "....."]))

(def d23-s01-2
  (t/parse
   ["....."
    "..##."
    ".#..."
    "....#"
    "....."
    "..#.."]))

(def d23-s01-3
  (t/parse
   ["..#.."
    "....#"
    "#...."
    "....#"
    "....."
    "..#.."]))

(def d23-s02
  (t/parse
   ["....#.."
    "..###.#"
    "#...#.#"
    ".#...##"
    "#.###.."
    "##.#.##"
    ".#..#.."]))

(deftest parse-test
  (testing "Correctly parses the sample input"
    (is (= d23-s01
           #{[2 3] [3 4] [2 4] [3 1] [2 1]}))))

(deftest round-test
  (testing "Returns the new elf locations after one round"
    (is (= d23-s01-1 (first (t/round [d23-s01 t/dir-order]))))
    (is (= d23-s01-2 (first (t/round [d23-s01-1 (u/rotate 1 t/dir-order)]))))
    (is (= d23-s01-3 (first (t/round [d23-s01-2 (u/rotate 2 t/dir-order)]))))))

(deftest empty-tiles-after-ten-rounds-test
  (testing "Computes the number of empty tiles in the tightest rectangular
            area around the elves after 10 rounds"
    (is (= 25  (t/empty-tiles-after-ten-rounds d23-s01)))
    (is (= 110 (t/empty-tiles-after-ten-rounds d23-s02)))))

(deftest rounds-until-static
  (testing "Computes the first round where elves will no longer move"
    (is (= 4 (t/rounds-until-static d23-s01)))
    (is (= 20 (t/rounds-until-static d23-s02)))))

(deftest day23-part1-soln
  (testing "Reproduces the answer for day23, part1"
    (is (= 4172 (t/day23-part1-soln)))))

;; TODO - Investigate whether there's a faster approach to part 2
;; https://github.com/Ken-2scientists/aoc-clj/issues/30
(deftest ^:slow day23-part2-soln
  (testing "Reproduces the answer for day23, part2"
    (is (= 942 (t/day23-part2-soln)))))