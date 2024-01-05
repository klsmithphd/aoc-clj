(ns aoc-clj.2019.day24-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2019.day24 :as t]))


(def d24-s00       (t/parse ["....#" "#..#." "#..##" "..#.." "#...."]))
(def d24-s00-step1 (t/parse ["#..#." "####." "###.#" "##.##" ".##.."]))
(def d24-s00-step2 (t/parse ["#####" "....#" "....#" "...#." "#.###"]))
(def d24-s00-step3 (t/parse ["#...." "####." "...##" "#.##." ".##.#"]))
(def d24-s00-step4 (t/parse ["####." "....#" "##..#" "....." "##..."]))

(def d24-s01 (t/parse ["....." "....." "....." "#...." ".#..."]))

(deftest conway-step-test
  (testing "Can properly execute the rules for Conway's game of life"
    (is (= d24-s00-step1 (t/conway-step-2d d24-s00)))
    (is (= d24-s00-step2 (t/conway-step-2d d24-s00-step1)))
    (is (= d24-s00-step3 (t/conway-step-2d d24-s00-step2)))
    (is (= d24-s00-step4 (t/conway-step-2d d24-s00-step3)))))

(deftest biodiversity-test
  (testing "Can properly compute biodiversity"
    (is (= 2129920 (t/biodiversity d24-s01)))))

(deftest neighbors3d-coords-test
  (testing "Can correctly determine the right neighbors in 3d recursive space"
    (is (= [[3 2 1] [2 3 1] [3 4 1] [4 3 1]]
           (t/neighbors3d-coords [3 3 1])))
    (is (= [[1 0 0] [0 1 0] [1 2 0] [2 1 0]]
           (t/neighbors3d-coords [1 1 0])))
    (is (= [[3 -1 0] [2 0 0] [3 1 0] [4 0 0] [2 1 1]]
           (t/neighbors3d-coords [3 0 0])))
    (is (= [[4 -1 0] [3 0 0] [4 1 0] [5 0 0] [2 1 1] [3 2 1]]
           (t/neighbors3d-coords [4 0 0])))
    (is (= [[3 1 1] [2 2 1] [3 3 1] [4 2 1] [4 0 0] [4 1 0] [4 2 0] [4 3 0] [4 4 0]]
           (t/neighbors3d-coords [3 2 1])))))

(def d24-s02         (t/parse ["....#" "#..#." "#.?##" "..#.." "#...."]))
(def d24-s02-level-5 (t/parse ["..#.." ".#.#." "..?.#" ".#.#." "..#.."]))
(def d24-s02-level-4 (t/parse ["...#." "...##" "..?.." "...##" "...#."]))
(def d24-s02-level-3 (t/parse ["#.#.." ".#..." "..?.." ".#..." "#.#.."]))
(def d24-s02-level-2 (t/parse [".#.##" "....#" "..?.#" "...##" ".###."]))
(def d24-s02-level-1 (t/parse ["#..##" "...##" "..?.." "...#." ".####"]))
(def d24-s02-level0  (t/parse [".#..." ".#.##" ".#?.." "....." "....."]))
(def d24-s02-level+1 (t/parse [".##.." "#..##" "..?.#" "##.##" "#####"]))
(def d24-s02-level+2 (t/parse ["###.." "##.#." "#.?.." ".#.##" "#.#.."]))
(def d24-s02-level+3 (t/parse ["..###" "....." "#.?.." "#...." "#...#"]))
(def d24-s02-level+4 (t/parse [".###." "#..#." "#.?.." "##.#." "....."]))
(def d24-s02-level+5 (t/parse ["####." "#..#." "#.?#." "####." "....."]))

(deftest simulate-test
  (testing "Simulation works correctly in 3d"
    (let [sim (t/simulate d24-s02 10)]
      (is (= d24-s02-level-5 (t/space3d-level sim 5)))
      (is (= d24-s02-level-4 (t/space3d-level sim 4)))
      (is (= d24-s02-level-3 (t/space3d-level sim 3)))
      (is (= d24-s02-level-2 (t/space3d-level sim 2)))
      (is (= d24-s02-level-1 (t/space3d-level sim 1)))
      (is (= d24-s02-level0  (t/space3d-level sim 0)))
      (is (= d24-s02-level+1 (t/space3d-level sim -1)))
      (is (= d24-s02-level+2 (t/space3d-level sim -2)))
      (is (= d24-s02-level+3 (t/space3d-level sim -3)))
      (is (= d24-s02-level+4 (t/space3d-level sim -4)))
      (is (= d24-s02-level+5 (t/space3d-level sim -5)))
      (is (= 99 (t/bug-count sim))))))

(def day24-input (u/parse-puzzle-input t/parse 2019 24))

(deftest part1-test
  (testing "Can reproduce the answer for part1"
    (is (= 17863711 (t/part1 day24-input)))))

;; FIXME: 2019 Day 24 part 2 solutiion is a bit slow to test
;; https://github.com/Ken-2scientists/aoc-clj/issues/24
(deftest ^:slow part2-test
  (testing "Can reproduce the answer for part2"
    (is (= 1937 (t/part2 day24-input)))))