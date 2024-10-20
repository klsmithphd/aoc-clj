(ns aoc-clj.2017.day21-test
  (:require [clojure.test :refer [deftest testing is]]
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
    (is (= [0 1
            1 0]
           (d21/flip-h [1 0
                        0 1])))
    (is (= [0 0 1
            1 0 0
            0 0 1] (d21/flip-h [1 0 0
                                0 0 1
                                1 0 0])))))

(deftest flip-v-test
  (testing "Flips the pattern vertically"
    (is (= [0 0
            1 1]
           (d21/flip-v [1 1
                        0 0])))
    (is (= [1 1 1
            0 1 0
            0 1 1] (d21/flip-v [0 1 1
                                0 1 0
                                1 1 1])))))