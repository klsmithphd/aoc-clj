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

(deftest step-test
  (testing "Evolves the map one step at a time, moving all blizzards to their
            new locations"
    ;; Bottommost blizzard moves down one, leftmost blizzard moves right
    (is (= {:time 1, :x-bound 5, :y-bound 5, :blizzards '([[4 5] :d] [[2 2] :r])}
           (t/step d24-s01)))
    ;; Bottommost blizzard wraps around to top, leftmost blizzard moves right
    (is (= {:time 2, :x-bound 5, :y-bound 5, :blizzards '([[4 1] :d] [[3 2] :r])}
           (t/step (t/step d24-s01))))
    ;; Blizzards now occupy same space
    (is (= {:time 3, :x-bound 5, :y-bound 5, :blizzards '([[4 2] :d] [[4 2] :r])}
           (t/step (t/step (t/step d24-s01)))))
    ;; Horizontal blizzard reaches right wall, vertical blizzard moves down
    (is (= {:time 4, :x-bound 5, :y-bound 5, :blizzards '([[4 3] :d] [[5 2] :r])}
           (t/step (t/step (t/step (t/step d24-s01))))))
    ;; Horizontal blizzard wraps around to left wall
    (is (= {:time 5, :x-bound 5, :y-bound 5, :blizzards '([[4 4] :d] [[1 2] :r])}
           (t/step (t/step (t/step (t/step (t/step d24-s01)))))))))

(deftest next-possible-states-test
  (testing "Given a state of the blizzards and elves at a given position, 
            return the set of next possible states"
    ;; At t=0, the only possible move at the beginning is to move down to [1 1]
    (is (= '([1 1])
           (map :pos (t/next-possible-states (t/init-state d24-s02)))))
    ;; At t=1, at [1 1], the elves can stay at [1 1] or move down to [1 2]
    (is (= '([1 1] [1 2])
           (map :pos (t/next-possible-states
                      (assoc
                       (-> d24-s02 t/step)
                       :pos [1 1])))))
    ;; At t=2, if the elves stayed at [1 1], the only option is now [1 2]
    (is (= '([1 2])
           (map :pos (t/next-possible-states
                      (assoc
                       (-> d24-s02 t/step t/step)
                       :pos [1 1])))))
    ;; At t=2, if the elves were at [1 2], they can only stay put
    (is (= '([1 2])
           (map :pos (t/next-possible-states
                      (assoc
                       (-> d24-s02 t/step t/step)
                       :pos [1 2])))))
    ;; At t=3, at [1 2], elves can only move back up to [1 1]
    (is (= '([1 1])
           (map :pos (t/next-possible-states
                      (assoc
                       (-> d24-s02 t/step t/step t/step)
                       :pos [1 2])))))
    ;; At t=4, at [1 1], elves can only move right to [2 1]
    (is (= '([2 1])
           (map :pos (t/next-possible-states
                      (assoc
                       (-> d24-s02 t/step t/step t/step t/step)
                       :pos [1 1])))))
    ;; At t=5, at [2 1], elves can only move right to [3 1]
    (is (= '([3 1])
           (map :pos (t/next-possible-states
                      (assoc
                       (-> d24-s02 t/step t/step t/step t/step t/step)
                       :pos [2 1])))))
    ;; At t=6, at [3 1], elves can only move down to [3 2]
    (is (= '([3 2])
           (map :pos (t/next-possible-states
                      (assoc
                       (-> d24-s02 t/step t/step t/step t/step t/step t/step)
                       :pos [3 1])))))))

(deftest explore-until-destination-test
  (testing "Returns a shortest path that the elves can take, exploring the
            evolving blizzard map until getting one step away from exiting 
            the maze"
    (is (= [[1 0] ;; From t=  0, move down to...
            [1 1] ;; From t=  1, wait...
            [1 1] ;; From t=  2, move down to...
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
           (map :pos (t/explore-until-destination d24-s02))))))

(deftest fewest-minutes-to-avoid-blizzards
  (testing "Computes the shortest amount of time required to navigate
            the evolving blizzard maze"
    (is (= 18 (t/shortest-time-to-navigate-blizzards d24-s02)))))

;; (deftest day24-part1-soln
;;   (testing "Reproduces the answer for day24, part1"
;;     (is (= 0 (t/day24-part1-soln)))))

;; (deftest day24-part2-soln
;;   (testing "Reproduces the answer for day24, part2"
;;     (is (= 0 (t/day24-part2-soln)))))