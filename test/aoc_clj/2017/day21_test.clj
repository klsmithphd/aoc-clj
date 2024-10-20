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
