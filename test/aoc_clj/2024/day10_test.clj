(ns aoc-clj.2024.day10-test
  (:require [clojure.test :refer [deftest testing is]]
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

(deftest trailheads-test
  (testing "Returns the location of any of the trailheads"
    (is (= #{[0 0]} (d10/trailheads d10-s00)))
    (is (= #{[3 0]} (d10/trailheads d10-s01)))
    (is (= #{[3 0]} (d10/trailheads d10-s02)))
    (is (= #{[1 0] [5 6]} (d10/trailheads d10-s03)))
    (is (= #{[2 0] [4 0] [4 2] [6 4] [2 5] [5 5] [0 6] [6 6] [1 7]}
           (d10/trailheads d10-s04)))))