(ns aoc-clj.2023.day21-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.grid.vecgrid :as vg]
            [aoc-clj.2023.day21 :as t]))

(def d21-s01-raw
  ["..........."
   ".....###.#."
   ".###.##..#."
   "..#.#...#.."
   "....#.#...."
   ".##..S####."
   ".##..#...#."
   ".......##.."
   ".##.#.####."
   ".##..##.##."
   "..........."])

(def d21-s01
  (vg/->VecGrid2D
   [[:plot :plot :plot :plot :plot :plot :plot :plot :plot :plot :plot]
    [:plot :plot :plot :plot :plot :rock :rock :rock :plot :rock :plot]
    [:plot :rock :rock :rock :plot :rock :rock :plot :plot :rock :plot]
    [:plot :plot :rock :plot :rock :plot :plot :plot :rock :plot :plot]
    [:plot :plot :plot :plot :rock :plot :rock :plot :plot :plot :plot]
    [:plot :rock :rock :plot :plot :start :rock :rock :rock :rock :plot]
    [:plot :rock :rock :plot :plot :rock :plot :plot :plot :rock :plot]
    [:plot :plot :plot :plot :plot :plot :plot :rock :rock :plot :plot]
    [:plot :rock :rock :plot :rock :plot :rock :rock :rock :rock :plot]
    [:plot :rock :rock :plot :plot :rock :rock :plot :rock :rock :plot]
    [:plot :plot :plot :plot :plot :plot :plot :plot :plot :plot :plot]]))

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d21-s01 (t/parse d21-s01-raw)))))

(deftest start-pos-test
  (testing "Returns the position of the starting tile"
    (is (= [5 5] (t/start-pos d21-s01)))))

(deftest all-possible-locations-test
  (testing "For the given set of starting positions, returns all possible
            move locations"
    (is (= #{[5 4] [4 5]}
           (t/all-possible-locations d21-s01 [[5 5]])))
    (is (= #{[5 3] [3 5] [5 5] [4 6]}
           (t/all-possible-locations d21-s01 #{[5 4] [4 5]})))
    (is (= #{[6 3] [3 4] [5 4] [4 5] [3 6] [4 7]}
           (t/all-possible-locations d21-s01 #{[5 3] [3 5] [5 5] [4 6]})))))

(deftest reachable-steps-test
  (testing "Returns the number of plot tiles reachable within exactly n steps"
    (is (= 1  (t/reachable-steps d21-s01 0)))
    (is (= 2  (t/reachable-steps d21-s01 1)))
    (is (= 4  (t/reachable-steps d21-s01 2)))
    (is (= 6  (t/reachable-steps d21-s01 3)))
    (is (= 16 (t/reachable-steps d21-s01 6)))))

(def day21-input (u/parse-puzzle-input t/parse 2023 21))

(deftest day21-part1-soln
  (testing "Reproduces the answer for day21, part1"
    (is (= 3764 (t/day21-part1-soln day21-input)))))