(ns aoc-clj.2019.day17-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2019.day17 :as t]))

(def d17-s00
  (str
   "..#..........\n"
   "..#..........\n"
   "#######...###\n"
   "#.#...#...#.#\n"
   "#############\n"
   "..#...#...#..\n"
   "..#####...^..\n"))

(deftest intersection-test
  (testing "Can find the intersections of the scaffolding"
    (is (= [[2 2] [6 4] [2 4] [10 4]]
           (t/intersections (t/scaffold-map d17-s00))))))

(def day17-input (u/parse-puzzle-input t/parse 2019 17))

(deftest part1-test
  (testing "Can reproduce the answer for part1"
    (is (= 7584 (t/part1 day17-input)))))

(deftest part2-test
  (testing "Can reproduce the answer for part2"
    (is (= 1016738 (t/part2 day17-input)))))
