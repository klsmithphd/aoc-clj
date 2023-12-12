(ns aoc-clj.2023.day11-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2023.day11 :as t]))

(def d11-s01-raw
  ["...#......"
   ".......#.."
   "#........."
   ".........."
   "......#..."
   ".#........"
   ".........#"
   ".........."
   ".......#.."
   "#...#....."])

(def d11-s01-raw-expanded
  ["....#........"
   ".........#..."
   "#............"
   "............."
   "............."
   "........#...."
   ".#..........."
   "............#"
   "............."
   "............."
   ".........#..."
   "#....#......."])

(def d11-s01          (t/parse d11-s01-raw))
(def d11-s01-expanded (t/parse d11-s01-raw-expanded))

(deftest empty-ids-test
  (testing "Finds the ids of empty rows and cols"
    (is (= {:rows #{3 7} :cols #{2 5 8}} (t/empty-ids d11-s01)))))

(deftest expanded-test
  (testing "Creates an expanded version of the galaxy map"
    (is (= d11-s01-expanded (t/expanded d11-s01)))))

(deftest galaxies-text
  (testing "Returns the locations of the galaxies in the grid"
    (is (= [[4 0] [9 1] [0 2] [8 5] [1 6] [12 7] [9 10] [0 11] [5 11]]
           (t/galaxies d11-s01-expanded)))))

(deftest galaxy-pair-distance-sum-test
  (testing "Returns the sum of the distance between the galaxies in the expanded grid"
    (is (= 374 (t/galaxy-pair-distance-sum d11-s01-expanded)))))

(def day11-input (u/parse-puzzle-input t/parse 2023 11))

(deftest day02-part1-soln
  (testing "Reproduces the answer for day02, part1"
    (is (= 9312968 (t/day11-part1-soln day11-input)))))

