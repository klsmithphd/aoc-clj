(ns aoc-clj.2023.day22-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2023.day22 :as t]))

(def d22-s01-raw
  ["1,0,1~1,2,1"
   "0,0,2~2,0,2"
   "0,2,3~2,2,3"
   "0,0,4~0,2,4"
   "2,0,5~2,2,5"
   "0,1,6~2,1,6"
   "1,1,8~1,1,9"])

(def d22-s02-raw
  ["0,0,1~0,1,1"
   "1,1,1~1,1,1"
   "0,0,2~0,0,2"
   "0,1,2~1,1,2"])

(def d22-s01
  [[[1 0 1] [1 2 1]]
   [[0 0 2] [2 0 2]]
   [[0 2 3] [2 2 3]]
   [[0 0 4] [0 2 4]]
   [[2 0 5] [2 2 5]]
   [[0 1 6] [2 1 6]]
   [[1 1 8] [1 1 9]]])

(def d22-s01-z-rep
  [{1 #{[1 0] [1 1] [1 2]}}
   {2 #{[0 0] [1 0] [2 0]}}
   {3 #{[0 2] [1 2] [2 2]}}
   {4 #{[0 0] [0 1] [0 2]}}
   {5 #{[2 0] [2 1] [2 2]}}
   {6 #{[0 1] [1 1] [2 1]}}
   {8 #{[1 1]}
    9 #{[1 1]}}])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d22-s01 (t/parse d22-s01-raw)))))

(deftest lowest-z-test
  (testing "Computes the lowest z position of a brick"
    (is (= [1 2 3 4 5 6 8] (map t/lowest-z d22-s01-z-rep)))))

(deftest brick-z-rep-test
  (testing "Converts the brick expression into occupied cells at each z-level"
    (is (= d22-s01-z-rep (map t/brick-z-rep d22-s01)))))

(deftest place-brick-test
  (testing "Places each brick in correct location in sample data"
    (is (= {:z-index {1 #{[1 0] [1 1] [1 2]}}
            :bricks [{1 #{[1 0] [1 1] [1 2]}}]}
           (t/place-brick {:z-index {} :bricks []} (nth d22-s01-z-rep 0))))

    (is (= {:z-index {1 #{[1 0] [1 1] [1 2]}
                      2 #{[0 0] [1 0] [2 0]}}
            :bricks [{1 #{[1 0] [1 1] [1 2]}}
                     {2 #{[0 0] [1 0] [2 0]}}]}
           (t/place-brick {:z-index {1 #{[1 0] [1 1] [1 2]}}
                           :bricks [{1 #{[1 0] [1 1] [1 2]}}]}
                          (nth d22-s01-z-rep 1))))

    (is (= {:z-index {1 #{[1 0] [1 1] [1 2]}
                      2 #{[0 0] [1 0] [2 0] [0 2] [1 2] [2 2]}}
            :bricks [{1 #{[1 0] [1 1] [1 2]}}
                     {2 #{[0 0] [1 0] [2 0]}}
                     {2 #{[0 2] [1 2] [2 2]}}]}
           (t/place-brick {:z-index {1 #{[1 0] [1 1] [1 2]}
                                     2 #{[0 0] [1 0] [2 0]}}
                           :bricks [{1 #{[1 0] [1 1] [1 2]}}
                                    {2 #{[0 0] [1 0] [2 0]}}]}
                          (nth d22-s01-z-rep 2))))))

(def d22-s01-placed-bricks (t/place-bricks d22-s01-z-rep))
(deftest place-bricks-test
  (testing "Correctly determines resting place/coords of each brick"
    (is (= [{1 #{[1 0] [1 1] [1 2]}}
            {2 #{[0 0] [1 0] [2 0]}}
            {2 #{[0 2] [1 2] [2 2]}}
            {3 #{[0 0] [0 1] [0 2]}}
            {3 #{[2 0] [2 1] [2 2]}}
            {4 #{[0 1] [1 1] [2 1]}}
            {5 #{[1 1]}
             6 #{[1 1]}}]
           (:bricks d22-s01-placed-bricks)))))

(deftest distintegratable?-test
  (testing "Determines whether a brick can be distintegrated
            without affecting any bricks above it"
    (is (= [false true true true true false true]
           (map #(t/disintegratable? d22-s01-placed-bricks %)
                (:bricks d22-s01-placed-bricks))))))

(deftest disintegratable-count-test
  (testing "Counts how many placed bricks can be disintegrated"
    (is (= 5 (t/disintegratable-count d22-s01)))
    (is (= 3 (t/disintegratable-count (t/parse d22-s02-raw))))))

(deftest disintegration-chain-test
  (testing "Constructs the chain of dependencies among the bricks"
    (is (= {0 [1 2] ;; A supports B and C
            1 [3 4] ;; B supports D and E
            2 [3 4] ;; C supports D and E
            3 [5]   ;; D supports F
            4 [5]   ;; E supports F
            5 [6]   ;; F supports G
            6 []}   ;; G supports nothing
           (t/supports-graph d22-s01)))))


(deftest bricks-to-fall-test
  (testing "Computes the number of bricks that will fall when
            the non-disintegratable bricks are removed"
    (is (= 7 (t/bricks-to-fall d22-s01)))))

(def day22-input (u/parse-puzzle-input t/parse 2023 22))

(deftest day22-part1-soln
  (testing "Reproduces the answer for day22, part1"
    (is (= 418 (t/day22-part1-soln day22-input)))))

(deftest day22-part2-soln
  (testing "Reproduces the answer for day22, part2"
    (is (= 70702 (t/day22-part2-soln day22-input)))))