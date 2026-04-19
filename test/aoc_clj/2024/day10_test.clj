(ns aoc-clj.2024.day10-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2024.day10 :as d10]))

(def d10-s00
  (d10/parse
   ["0123"
    "1234"
    "8765"
    "9876"]))

(def d10-s01
  (d10/parse
   ["...0..."
    "...1..."
    "...2..."
    "6543456"
    "7.....7"
    "8.....8"
    "9.....9"]))

(def d10-s02
  (d10/parse
   ["..90..9"
    "...1.98"
    "...2..7"
    "6543456"
    "765.987"
    "876...."
    "987...."]))

(def d10-s03
  (d10/parse
   ["10..9.."
    "2...8.."
    "3...7.."
    "4567654"
    "...8..3"
    "...9..2"
    ".....01"]))

(def d10-s04
  (d10/parse
   ["89010123"
    "78121874"
    "87430965"
    "96549874"
    "45678903"
    "32019012"
    "01329801"
    "10456732"]))

(def d10-s05
  (d10/parse
   [".....0."
    "..4321."
    "..5..2."
    "..6543."
    "..7..4."
    "..8765."
    "..9...."]))

(def d10-s06
  (d10/parse
   ["012345"
    "123456"
    "234567"
    "345678"
    "4.6789"
    "56789."]))

(deftest trailheads-test
  (testing "Returns the location of any of the trailheads"
    (is (= #{[0 0]} (d10/trailheads d10-s00)))
    (is (= #{[0 3]} (d10/trailheads d10-s01)))
    (is (= #{[0 3]} (d10/trailheads d10-s02)))
    (is (= #{[0 1] [6 5]} (d10/trailheads d10-s03)))
    (is (= #{[0 2] [0 4] [2 4] [4 6] [5 2] [5 5] [6 0] [6 6] [7 1]}
           (d10/trailheads d10-s04)))))

(deftest score-test
  (testing "Returns the number of distinct summits reachable from a given trailhead"
    (is (= 1 (d10/score d10-s00 [0 0])))
    (is (= 2 (d10/score d10-s01 [0 3])))
    (is (= 4 (d10/score d10-s02 [0 3])))
    (is (= 1 (d10/score d10-s03 [0 1])))
    (is (= 2 (d10/score d10-s03 [6 5])))))

(deftest rating-test
  (testing "Returns the number of distinct hiking trails from a given trailhead"
    (is (= 3   (d10/rating d10-s05 [0 5])))
    (is (= 13  (d10/rating d10-s02 [0 3])))
    (is (= 227 (d10/rating d10-s06 [0 0])))))

(deftest trailhead-score-sum-test
  (testing "Returns the sum of the scores of all the trailheads"
    (is (= 36 (d10/score-sum :part1 d10-s04)))
    (is (= 81 (d10/score-sum :part2 d10-s04)))))

(def day10-input (u/parse-puzzle-input d10/parse 2024 10))

(deftest part1-test
  (testing "Reproduces the answer for day10, part1"
    (is (= 468 (d10/part1 day10-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day10, part2"
    (is (= 966 (d10/part2 day10-input)))))