(ns aoc-clj.2022.day24-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2022.day24 :as t]))

(def d24-s00
  (t/parse
   ["#.#####"
    "#.....#"
    "#>....#"
    "#.....#"
    "#...v.#"
    "#.....#"
    "#####.#"]))

(def d24-s01
  (t/parse
   ["#.######"
    "#>>.<^<#"
    "#.<..<<#"
    "#>v.><>#"
    "#<^v^^>#"
    "######.#"]))

(deftest parse-test
  (testing "Correctly parses the sample input"
    (is (= (select-keys d24-s00 [:x-bound :y-bound])
           {:x-bound 5, :y-bound 5}))
    (is (= #{[[4 4] :d] [[2 1] :r]}
           (set (:blizzards d24-s00))))))

(deftest blizzard-sim-test
  (testing "Evolves the map one step at a time, moving all blizzards to their
            new locations"
    (let [sim (t/blizzard-sim d24-s00)]
      ;; Initial state:
      (is (= #{[2 1] [4 4]} (sim 0)))
      ;; Rightward blizzard moves right one, downward blizzard moves down
      (is (= #{[2 2] [5 4]} (sim 1)))
      ;; Downward blizzard wraps around to top, rightward blizzard moves right
      (is (= #{[2 3] [1 4]} (sim 2)))
      ;; Blizzards now occupy same space
      (is (= #{[2 4]}       (sim 3)))
      ;; Rightward blizzard moves right, downward blizzard moves down
      (is (= #{[2 5] [3 4]} (sim 4)))
      ;; Rightward blizzard wraps to left wall, same as initial state
      (is (= #{[2 1] [4 4]} (sim 5))))))

(deftest next-possible-states-test
  (testing "Given a state of the blizzards and elves at a given position,
            return the set of next possible states"
    ;; At t=0, the only possible move at the beginning is to stay put at the
    ;; start or to move down to [1 1]
    (is (= #{[1 [0 1]] [1 [1 1]]}
           (set (t/next-possible-states (t/augment d24-s01) [0 [0 1]]))))
    ;; At t=1, at [1 1], the elves can stay at [1 1] or move down to [2 1]
    (is (= #{[2 [1 1]] [2 [2 1]] [2 [0 1]]}
           (set (t/next-possible-states (t/augment d24-s01) [1 [1 1]]))))
    ;; At t=2, if the elves stayed at [1 1], the only option is now [2 1]
    (is (= #{[3 [2 1]] [3 [0 1]]}
           (set (t/next-possible-states (t/augment d24-s01) [2 [1 1]]))))
    ;; At t=2, if the elves were at [2 1], they can only stay put
    (is (= #{[3 [2 1]]}
           (set (t/next-possible-states (t/augment d24-s01) [2 [2 1]]))))
    ;; At t=3, at [2 1], elves can only move back up to [1 1]
    (is (= #{[4 [1 1]]}
           (set (t/next-possible-states (t/augment d24-s01) [3 [2 1]]))))
    ;; At t=4, at [1 1], elves can only move right to [1 2]
    (is (= #{[5 [1 2]] [5 [0 1]]}
           (set (t/next-possible-states (t/augment d24-s01) [4 [1 1]]))))
    ;; At t=5, at [1 2], elves can only move right to [1 3]
    (is (= #{[6 [1 3]]}
           (set (t/next-possible-states (t/augment d24-s01) [5 [1 2]]))))
    ;; At t=6, at [1 3], elves can only move down to [2 3]
    (is (= #{[7 [2 3]]}
           (set (t/next-possible-states (t/augment d24-s01) [6 [1 3]]))))))

(deftest path-to-exit-test
  (testing "Returns a shortest path that the elves can take, exploring the
            evolving blizzard map until getting one step away from exiting
            the maze"
    (let [path (t/path-to-exit d24-s01 0)
          positions (map second path)]
      (is (= 18 (count path)))
      (is (= [0 1] (first positions)))
      (is (= [4 6] (last positions))))))

(deftest shortest-time-to-exit-test
  (testing "Computes the shortest amount of time required to navigate
            the evolving blizzard maze"
    (is (= 18 (t/shortest-time-to-exit d24-s01)))))

(deftest shortest-roundtrip-to-exit-test
  (testing "Computes the shortest amount of time required to navigate
            the evolving blizzard maze from the start to the exit,
            back to the start, and back to the exit"
    (is (= 54 (t/shortest-roundtrip-to-exit d24-s01)))))

(def day24-input (u/parse-puzzle-input t/parse 2022 24))

(deftest part1-test
  (testing "Reproduces the answer for day24, part1"
    (is (= 286 (t/part1 day24-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day24, part2"
    (is (= 820 (t/part2 day24-input)))))