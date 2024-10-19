(ns aoc-clj.2017.day19-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day19 :as d19]))

(def d19-s00-raw
  ["     |          "
   "     |  +--+    "
   "     A  |  C    "
   " F---|----E|--+ "
   "     |  |  |  D "
   "     +B-+  +--+ "])

(def d19-s00 (d19/parse d19-s00-raw))

(deftest start-test
  (testing "Finds the starting position in the grid"
    (is (= [5 0] (d19/start d19-s00)))))

(deftest next-cell-test
  (testing "Identifies the next cell on the path"
    (is (= {:pos [5 1] :heading :n}
           (d19/next-cell d19-s00 {:pos [5 0] :heading :n})))
    (is (= {:pos [5 2] :heading :n}
           (d19/next-cell d19-s00 {:pos [5 1] :heading :n})))
    (is (= {:pos [5 3] :heading :n}
           (d19/next-cell d19-s00 {:pos [5 2] :heading :n})))
    (is (= {:pos [5 4] :heading :n}
           (d19/next-cell d19-s00 {:pos [5 3] :heading :n})))
    (is (= {:pos [5 5] :heading :n}
           (d19/next-cell d19-s00 {:pos [5 4] :heading :n})))
    (is (= {:pos [6 5] :heading :e}
           (d19/next-cell d19-s00 {:pos [5 5] :heading :n})))
    (is (= {:pos [7 5] :heading :e}
           (d19/next-cell d19-s00 {:pos [6 5] :heading :e})))
    (is (= {:pos [8 5] :heading :e}
           (d19/next-cell d19-s00 {:pos [7 5] :heading :e})))
    (is (= {:pos [8 4] :heading :s}
           (d19/next-cell d19-s00 {:pos [8 5] :heading :e})))))

(deftest path-test
  (testing "Finds the path through the diagram"
    (is (= [[5 0] [5 1] [5 2] [5 3] [5 4] [5 5]
            [6 5] [7 5]
            [8 5] [8 4] [8 3] [8 2]
            [8 1] [9 1] [10 1]
            [11 1] [11 2] [11 3] [11 4]
            [11 5] [12 5] [13 5]
            [14 5] [14 4]
            [14 3] [13 3] [12 3] [11 3] [10 3] [9 3] [8 3] [7 3] [6 3] [5 3] [4 3] [3 3] [2 3] [1 3]]
           (d19/path d19-s00)))))

(deftest letters-along-path-test
  (testing "Finds the letters along the path through the diagram"
    (is (= "ABCDEF" (d19/letters-along-path d19-s00)))))

(deftest step-count-test
  (testing "How many steps along the path"
    (is (= 38 (d19/step-count d19-s00)))))

(def day19-input (u/parse-puzzle-input d19/parse 2017 19))

(deftest part1-test
  (testing "Reproduces the answer for day19, part1"
    (is (= "GINOWKYXH" (d19/part1 day19-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day19, part2"
    (is (= 16636 (d19/part2 day19-input)))))