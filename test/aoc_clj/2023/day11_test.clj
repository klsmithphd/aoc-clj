(ns aoc-clj.2023.day11-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2023.day11 :as t]))

(def d11-s00-raw
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

(def d11-s00-raw-expanded
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

(def d11-s00          (t/parse d11-s00-raw))
(def d11-s00-expanded (t/parse d11-s00-raw-expanded))

(deftest voids-test
  (testing "Finds the ids of empty rows and cols"
    (is (= {:rows #{3 7} :cols #{2 5 8}} (t/voids d11-s00)))))

(deftest galaxies-text
  (testing "Returns the locations of the galaxies in the grid"
    (is (= [[3 0] [7 1] [0 2] [6 4] [1 5] [9 6] [7 8] [0 9] [4 9]]
           (t/galaxies d11-s00)))
    (is (= [[4 0] [9 1] [0 2] [8 5] [1 6] [12 7] [9 10] [0 11] [5 11]]
           (t/galaxies d11-s00-expanded)))))

(deftest expanded-coords-test
  (testing "Returns the locations of the galaxies in an expanded space"
    (is (= (t/galaxies d11-s00-expanded) (t/expanded-coords d11-s00 2)))))

(deftest pairwise-distance-sum-test
  (testing "Returns the sum of the distances between the galaxies"
    (is (= 374  (t/pairwise-distance-sum (t/expanded-coords d11-s00 2))))
    (is (= 1030 (t/pairwise-distance-sum (t/expanded-coords d11-s00 10))))
    (is (= 8410 (t/pairwise-distance-sum (t/expanded-coords d11-s00 100))))))

(def day11-input (u/parse-puzzle-input t/parse 2023 11))

(deftest day11-part1-soln
  (testing "Reproduces the answer for day11, part1"
    (is (= 9312968 (t/day11-part1-soln day11-input)))))

(deftest day11-part2-soln
  (testing "Reproduces the answer for day11, part2"
    (is (= 597714117556 (t/day11-part2-soln day11-input)))))

