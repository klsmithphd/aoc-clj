(ns aoc-clj.2017.day21-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day21 :as d21]))

(def d21-s00-raw
  ["../.# => ##./#../..."
   ".#./..#/### => #..#/..../..../#..#"])

(def d21-s00
  {[0 0 0 1]           [1 1 0 1 0 0 0 0 0]
   [0 1 0 0 0 1 1 1 1] [1 0 0 1 0 0 0 0 0 0 0 0 1 0 0 1]})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d21-s00 (d21/parse d21-s00-raw)))))

(deftest flip-h-test
  (testing "Flips the pattern horizontally"
    (is (= [2 1
            4 3]
           (d21/flip-h [1 2
                        3 4])))
    (is (= [3 2 1
            6 5 4
            9 8 7] (d21/flip-h [1 2 3
                                4 5 6
                                7 8 9])))))

(deftest flip-v-test
  (testing "Flips the pattern vertically"
    (is (= [3 4
            1 2]
           (d21/flip-v [1 2
                        3 4])))
    (is (= [7 8 9
            4 5 6
            1 2 3]
           (d21/flip-v [1 2 3
                        4 5 6
                        7 8 9])))))

(deftest rotate-test
  (testing "Rotates the pattern one quarter turn"
    (is (= [2 4
            1 3]
           (d21/rotate [1 2
                        3 4])))
    (is (= [3 6 9
            2 5 8
            1 4 7]
           (d21/rotate [1 2 3
                        4 5 6
                        7 8 9])))
    (is (= [1 2
            3 4] (d21/rotate (d21/rotate (d21/rotate (d21/rotate [1 2
                                                                  3 4]))))))))

(deftest equivalent-matches-test
  (testing "Returns a new map of all the equivalent rule-replacement patterns"
    (is (= {[0 0 0 1] [1 1 0 1 0 0 0 0 0]
            [0 0 1 0] [1 1 0 1 0 0 0 0 0]
            [0 1 0 0] [1 1 0 1 0 0 0 0 0]
            [1 0 0 0] [1 1 0 1 0 0 0 0 0]}
           (d21/equivalent-matches (first d21-s00))))))

(deftest full-rulebook-test
  (testing "Generates the full rulebook given the sparse representation"
    (is (= {[0 0 0 1]           [1 1 0 1 0 0 0 0 0]
            [0 0 1 0]           [1 1 0 1 0 0 0 0 0]
            [0 1 0 0]           [1 1 0 1 0 0 0 0 0]
            [1 0 0 0]           [1 1 0 1 0 0 0 0 0]
            [0 1 0 0 0 1 1 1 1] [1 0 0 1 0 0 0 0 0 0 0 0 1 0 0 1]
            [0 1 0 1 0 0 1 1 1] [1 0 0 1 0 0 0 0 0 0 0 0 1 0 0 1]
            [1 1 1 0 0 1 0 1 0] [1 0 0 1 0 0 0 0 0 0 0 0 1 0 0 1]
            [0 1 1 1 0 1 0 0 1] [1 0 0 1 0 0 0 0 0 0 0 0 1 0 0 1]
            [1 1 1 1 0 0 0 1 0] [1 0 0 1 0 0 0 0 0 0 0 0 1 0 0 1]
            [1 0 0 1 0 1 1 1 0] [1 0 0 1 0 0 0 0 0 0 0 0 1 0 0 1]}
           (d21/full-rulebook d21-s00)))))

(deftest subsquares-test
  (testing "Breaks apart a square into 2x2 or 3x3 subsquares"
    (is (= [[0 1 2
             3 4 5
             6 7 8]] (d21/subsquares (range 9))))
    (is (= [[0 1 4 5]
            [2 3 6 7]
            [8 9 12 13]
            [10 11 14 15]] (d21/subsquares (range 16))))
    (is (= [[0 1 6 7]
            [2 3 8 9]
            [4 5 10 11]
            [12 13 18 19]
            [14 15 20 21]
            [16 17 22 23]
            [24 25 30 31]
            [26 27 32 33]
            [28 29 34 35]] (d21/subsquares (range 36))))
    (is (= [[0 1 2 9 10 11 18 19 20]
            [3 4 5 12 13 14 21 22 23]
            [6 7 8 15 16 17 24 25 26]
            [27 28 29 36 37 38 45 46 47]
            [30 31 32 39 40 41 48 49 50]
            [33 34 35 42 43 44 51 52 53]
            [54 55 56 63 64 65 72 73 74]
            [57 58 59 66 67 68 75 76 77]
            [60 61 62 69 70 71 78 79 80]] (d21/subsquares (range 81))))))

(deftest recombine-test
  (testing "Takes the subsquares and recombines them back into a whole"
    (is (= (range 9) (d21/recombine (d21/subsquares (range 9)))))
    (is (= (range 16) (d21/recombine (d21/subsquares (range 16)))))
    (is (= (range 36) (d21/recombine (d21/subsquares (range 36)))))
    (is (= (range 81) (d21/recombine (d21/subsquares (range 81)))))))

(deftest step-test
  (testing "Iteratively applies the replacement rules one step at a time"
    (is (= [1 0 0 1
            0 0 0 0
            0 0 0 0
            1 0 0 1]
           (d21/step (d21/full-rulebook d21-s00) d21/start)))
    (is (= [1 1 0 1 1 0
            1 0 0 1 0 0
            0 0 0 0 0 0
            1 1 0 1 1 0
            1 0 0 1 0 0
            0 0 0 0 0 0]
           (d21/step (d21/full-rulebook d21-s00)
                     (d21/step (d21/full-rulebook d21-s00) d21/start))))))

(deftest pixels-on-at-n-test
  (testing "How many pixels are on after n steps?"
    (is (= 5 (d21/pixels-on-at-n d21-s00 0)))
    (is (= 4 (d21/pixels-on-at-n d21-s00 1)))
    (is (= 12 (d21/pixels-on-at-n d21-s00 2)))))

(def day21-input (u/parse-puzzle-input d21/parse 2017 21))

(deftest part1-test
  (testing "Reproduces the answer for day21, part1"
    (is (= 364 (d21/part1 day21-input)))))
