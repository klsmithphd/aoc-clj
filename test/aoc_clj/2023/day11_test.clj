(ns aoc-clj.2023.day11-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.util.interface :as u]
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
    (is (= [[0 3] [1 7] [2 0] [4 6] [5 1] [6 9] [8 7] [9 0] [9 4]]
           (t/galaxies d11-s00)))
    (is (= [[0 4] [1 9] [2 0] [5 8] [6 1] [7 12] [10 9] [11 0] [11 5]]
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

(deftest part1-test
  (testing "Reproduces the answer for day11, part1"
    (is (= 9312968 (t/part1 day11-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day11, part2"
    (is (= 597714117556 (t/part2 day11-input)))))

