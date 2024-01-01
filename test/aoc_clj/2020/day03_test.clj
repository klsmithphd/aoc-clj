(ns aoc-clj.2020.day03-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2020.day03 :as t]))

(def d03-s00
  ["..##......."
   "#...#...#.."
   ".#....#..#."
   "..#.#...#.#"
   ".#...##..#."
   "..#.##....."
   ".#.#.#....#"
   ".#........#"
   "#.##...#..."
   "#...##....#"
   ".#..#...#.#"])

(def sample-basemap (t/forest-basemap d03-s00))

(deftest items-along-slope
  (testing "Correctly determines the items along a slope"
    (is (= '(:space :space :tree :space :tree :tree :space :tree :tree :tree :tree)
           (t/items-along-slope sample-basemap [3 1])))))

(deftest trees-along-slopes
  (testing "Can find the trees in the example along the given slopes"
    (is (= '(2 7 3 4 2)
           (t/trees-along-slopes sample-basemap [[1 1] [3 1] [5 1] [7 1] [1 2]])))))

(def day03-input (u/parse-puzzle-input t/parse 2020 3))

(deftest day03-part1-soln
  (testing "Reproduces the answer for day03, part1"
    (is (= 191 (t/day03-part1-soln day03-input)))))

(deftest day03-part2-soln
  (testing "Reproduces the answer for day03, part2"
    (is (= 1478615040 (t/day03-part2-soln day03-input)))))