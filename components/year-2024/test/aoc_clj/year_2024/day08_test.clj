(ns aoc-clj.year-2024.day08-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.util.interface :as u]
            [aoc-clj.year-2024.day08 :as d08]))

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
   {\0 #{[1 8] [2 5] [3 7] [4 4]}
    \A #{[9 9] [8 8] [5 6]}}})

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
    (is (= #{[3 2] [0 11] [5 6] [7 0] [4 9] [1 3] [6 3] [0 6] [5 1] [2 10]}
           (d08/antenna-antinodes :part1 12 12
                                  [\0 #{[1 8] [2 5] [3 7] [4 4]}])))

    (is (= #{[7 7] [10 10] [1 3] [2 4] [11 10]}
           (d08/antenna-antinodes :part1 12 12
                                  [\A #{[9 9] [8 8] [5 6]}])))

    ;; This is the three T-frequency antenna example
    (is (= #{[0 0] [2 1] [4 2] [6 3] [8 4] [1 3] [0 5] [2 6] [3 9]}
           (d08/antenna-antinodes :part2 10 10
                                  [\T #{[0 0] [2 1] [1 3]}])))))

(deftest all-antinodes-test
  (testing "Returns the set of unique antinodes in the map bounds for all
            antennae"
    (is (= #{[7 0] [6 3] [5 1] [5 6] [4 9] [3 2] [2 10]
             [1 3] [0 6] [0 11] [11 10] [10 10] [7 7] [2 4]}
           (d08/all-antinodes :part1 d08-s00)))

    (is (= #{[0 0] [0 1] [0 6] [0 11]
             [1 1] [1 3] [1 8]
             [2 2] [2 4] [2 5] [2 10]
             [3 2] [3 3] [3 7]
             [4 4] [4 9]
             [5 1] [5 5] [5 6] [5 11]
             [6 3] [6 6]
             [7 0] [7 5] [7 7]
             [8 2] [8 8]
             [9 4] [9 9]
             [10 1] [10 10]
             [11 3] [11 10] [11 11]}
           (d08/all-antinodes :part2 d08-s00)))))

(def day08-input (u/parse-puzzle-input d08/parse 2024 8))

(deftest part1-test
  (testing "Reproduces the answer for day08, part1"
    (is (= 276 (d08/part1 day08-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day08, part2"
    (is (= 991 (d08/part2 day08-input)))))