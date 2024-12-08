(ns aoc-clj.2024.day08-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2024.day08 :as d08]))

(def d08-s00-raw
  ["............"
   "........0..."
   ".....0......"
   ".......0...."
   "....0......."
   "......A....."
   "............"
   "............"
   "........A..."
   ".........A.."
   "............"
   "............"])

(def d08-s00
  {:width    12
   :height   12
   :antennae
   {\0 #{[8 10] [5 9] [7 8] [4 7]}
    \A #{[9 2] [8 3] [6 6]}}})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d08-s00 (d08/parse d08-s00-raw)))))

(deftest pair-antinodes-test
  (testing "Returns the antinode locations for a pair of antennae"
    (is (= #{[3 8] [6 2]}
           (set (d08/pair-antinodes [[5 4] [4 6]]))))

    (is (= #{[0 7] [12 4]}
           (set (d08/pair-antinodes [[4 6] [8 5]]))))))

(deftest antenna-antinodes-test
  (testing "Returns the antinodes within the map bounds for an antenna type"
    (is (= #{[0 4] [3 5] [1 6] [6 6] [9 7] [2 8] [10 9] [3 10] [6 11] [11 11]}
           (d08/antenna-antinodes :part1 12 12
                                  [\0 #{[8 10] [5 9] [7 8] [4 7]}])))

    (is (= #{[10 0] [10 1] [7 4] [4 9] [3 10]}
           (d08/antenna-antinodes :part1 12 12
                                  [\A #{[9 2] [8 3] [6 6]}])))

    ;; This is the three T-frequency antenna example
    (is (= #{[0 9] [1 7] [2 5] [3 3] [4 1] [3 8] [5 9] [6 7] [9 6]}
           (d08/antenna-antinodes :part2 10 10
                                  [\T #{[0 9] [1 7] [3 8]}])))))

(deftest all-antinodes-test
  (testing "Returns the set of unique antinodes in the map bounds for all
            antennae"
    (is (= #{[0 4] [3 5] [1 6] [6 6] [9 7] [2 8] [10 9]
             [3 10] [6 11] [11 11] [10 0] [10 1] [7 4] [4 9]}
           (d08/all-antinodes :part1 d08-s00)))

    (is (= #{[0 11] [1 11] [6 11] [11 11]
             [1 10] [3 10] [8 10]
             [2 9] [4 9] [5 9] [10 9]
             [2 8] [3 8] [7 8]
             [4 7] [9 7]
             [1 6] [5 6] [6 6] [11 6]
             [3 5] [6 5]
             [0 4] [5 4] [7 4]
             [2 3] [8 3]
             [4 2] [9 2]
             [1 1] [10 1]
             [3 0] [10 0] [11 0]}
           (d08/all-antinodes :part2 d08-s00)))))

(def day08-input (u/parse-puzzle-input d08/parse 2024 8))

(deftest part1-test
  (testing "Reproduces the answer for day08, part1"
    (is (= 276 (d08/part1 day08-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day08, part2"
    (is (= 991 (d08/part2 day08-input)))))