(ns aoc-clj.2023.day11-test
  (:require [clojure.test :refer [deftest testing is]]
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