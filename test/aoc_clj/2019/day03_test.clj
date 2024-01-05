(ns aoc-clj.2019.day03-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2019.day03 :as t]))

(def path1-segs (t/parse-segments ["R8","U5","L5","D3"]))
(def path1-path
  [[0 0] [1 0] [2 0] [3 0] [4 0] [5 0] [6 0] [7 0] [8 0]
   [8 1] [8 2] [8 3] [8 4] [8 5]
   [7 5] [6 5] [5 5] [4 5] [3 5]
   [3 4] [3 3] [3 2]])

(def path2-segs (t/parse-segments ["U7","R6","D4","L4"]))
(def path2-path
  [[0 0] [0 1] [0 2] [0 3] [0 4] [0 5] [0 6] [0 7]
   [1 7] [2 7] [3 7] [4 7] [5 7] [6 7]
   [6 6] [6 5] [6 4] [6 3]
   [5 3] [4 3] [3 3] [2 3]])

(deftest wire-path-test
  (testing "Convert instructions into the wire path"
    (is (= path1-path (t/wire-path path1-segs)))
    (is (= path2-path (t/wire-path path2-segs)))))

(def input1 [path1-segs path2-segs])
(def dist1 6)
(def steps1 30)

(def input2 (mapv t/parse-segments
                  [["R75","D30","R83","U83","L12","D49","R71","U7","L72"]
                   ["U62","R66","U55","R34","D71","R55","D58","R83"]]))
(def dist2 159)
(def steps2 610)

(def input3 (mapv t/parse-segments
                  [["R98","U47","R26","D63","R33","U87","L62","D20","R33","U53","R51"]
                   ["U98","R91","D20","R16","D67","R40","U7","R15","U6","R7"]]))
(def dist3 135)
(def steps3 410)

(deftest part1-test
  (testing "Find closest intersection of the wire paths"
    (is (= dist1 (t/closest-intersection-dist input1)))
    (is (= dist2 (t/closest-intersection-dist input2)))
    (is (= dist3 (t/closest-intersection-dist input3)))))

(deftest part2-test
  (testing "Find fewest number of steps to first intersection of the wire paths"
    (is (= steps1 (t/shortest-steps-to-intersection input1)))
    (is (= steps2 (t/shortest-steps-to-intersection input2)))
    (is (= steps3 (t/shortest-steps-to-intersection input3)))))

(def day03-input (u/parse-puzzle-input t/parse 2019 3))

(deftest part1-test
  (testing "Can reproduce the answer for part1"
    (is (= 375 (t/part1 day03-input)))))

(deftest part2-test
  (testing "Can reproduce the answer for part2"
    (is (= 14746 (t/part2 day03-input)))))