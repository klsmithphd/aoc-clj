(ns aoc-clj.2022.day24-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2022.day24 :as t]))

(def d24-s01
  (t/parse
   ["#.#####"
    "#.....#"
    "#>....#"
    "#.....#"
    "#...v.#"
    "#.....#"
    "#####.#"]))

(def d24-s02
  (t/parse
   ["#.######"
    "#>>.<^<#"
    "#.<..<<#"
    "#>v.><>#"
    "#<^v^^>#"
    "######.#"]))

(deftest blizzard-sim-test
  (testing "Evolves the map one step at a time, moving all blizzards to their
            new locations"
    (let [sim (t/blizzard-sim d24-s01)]
      ;; Initial state:
      (is (= #{[1 2] [4 4]} (sim 0)))
      ;; Bottommost blizzard moves down one, leftmost blizzard moves right
      (is (= #{[2 2] [4 5]} (sim 1)))
      ;; Bottommost blizzard wraps around to top, leftmost blizzard moves right
      (is (= #{[3 2] [4 1]} (sim 2)))
      ;; Blizzards now occupy same space
      (is (= #{[4 2]}       (sim 3)))
      ;; Horizontal blizzard reaches right wall, vertical blizzard moves down
      (is (= #{[5 2] [4 3]} (sim 4)))
      ;; Horizontal blizzard wraps around to left wall, same as initial state
      (is (= #{[1 2] [4 4]} (sim 5))))))

(deftest next-possible-states-test
  (testing "Given a state of the blizzards and elves at a given position, 
            return the set of next possible states"
    ;; At t=0, the only possible move at the beginning is to stay put at the
    ;; start or to move down to [1 1]
    (is (= [[1 [1 0]] [1 [1 1]]]
           (t/next-possible-states (t/augment d24-s02) [0 [1 0]])))
    ;; At t=1, at [1 1], the elves can stay at [1 1] or move down to [1 2]
    (is (= [[2 [1 1]] [2 [1 2]] [2 [1 0]]]
           (t/next-possible-states (t/augment d24-s02) [1 [1 1]])))
    ;; At t=2, if the elves stayed at [1 1], the only option is now [1 2]
    (is (= [[3 [1 2]] [3 [1 0]]]
           (t/next-possible-states (t/augment d24-s02) [2 [1 1]])))
    ;; At t=2, if the elves were at [1 2], they can only stay put
    (is (= [[3 [1 2]]]
           (t/next-possible-states (t/augment d24-s02) [2 [1 2]])))
    ;; At t=3, at [1 2], elves can only move back up to [1 1]
    (is (= [[4 [1 1]]]
           (t/next-possible-states (t/augment d24-s02) [3 [1 2]])))
    ;; At t=4, at [1 1], elves can only move right to [2 1]
    (is (= [[5 [2 1]] [5 [1 0]]]
           (t/next-possible-states (t/augment d24-s02) [4 [1 1]])))
    ;; At t=5, at [2 1], elves can only move right to [3 1]
    (is (= [[6 [3 1]]]
           (t/next-possible-states (t/augment d24-s02) [5 [2 1]])))
    ;; At t=6, at [3 1], elves can only move down to [3 2]
    (is (= [[7 [3 2]]]
           (t/next-possible-states (t/augment d24-s02) [6 [3 1]])))))

(deftest path-to-exit-test
  (testing "Returns a shortest path that the elves can take, exploring the
            evolving blizzard map until getting one step away from exiting 
            the maze"
    (is (= [[1 0] ;; From t=  0, move down to...
            [1 1] ;; From t=  1, move down to...
            [1 2] ;; From t=  2, wait...
            [1 2] ;; From t=  3, move up to...
            [1 1] ;; From t=  4, move right to...
            [2 1] ;; From t=  5, move right to...
            [3 1] ;; From t=  6, move down to...
            [3 2] ;; From t=  6, move left to...
            [2 2] ;; From t=  8, move up to...
            [2 1] ;; From t=  9, move right to...
            [3 1] ;; From t= 10, wait...
            [3 1] ;; From t= 11, move down to...
            [3 2] ;; From t= 12, move down to...
            [3 3] ;; From t= 13, move right to...
            [4 3] ;; From t= 14, move right to...
            [5 3] ;; From t= 15, move right to...
            [6 3] ;; From t= 16, move down to...
            [6 4] ;; This is one move away from the destination 
            ]
           (map second (t/path-to-exit d24-s02 0))))))

(deftest shortest-time-to-exit-test
  (testing "Computes the shortest amount of time required to navigate
            the evolving blizzard maze"
    (is (= 18 (t/shortest-time-to-exit d24-s02)))))

(deftest shortest-roundtrip-to-exit-test
  (testing "Computes the shortest amount of time required to navigate
            the evolving blizzard maze from the start to the exit,
            back to the start, and back to the exit"
    (is (= 54 (t/shortest-roundtrip-to-exit d24-s02)))))

(deftest day24-part1-soln
  (testing "Reproduces the answer for day24, part1"
    (is (= 286 (t/day24-part1-soln)))))

(deftest day24-part2-soln
  (testing "Reproduces the answer for day24, part2"
    (is (= 820 (t/day24-part2-soln)))))