(ns aoc-clj.2019.day06-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2019.day06 :as t]))

(deftest orbit-count-test
  (testing "Can correctly count the total number of orbits"
    (is (= 42 (t/orbit-count {"B" "COM"
                              "C" "B"
                              "D" "C"
                              "E" "D"
                              "F" "E"
                              "G" "B"
                              "H" "G"
                              "I" "D"
                              "J" "E"
                              "K" "J"
                              "L" "K"})))))

(deftest orbit-transfers-test
  (testing "Can correctly determine the number of orbit transfers between YOU and SAN"
    (is (= 4 (t/orbit-transfers {"B" "COM"
                                 "C" "B"
                                 "D" "C"
                                 "E" "D"
                                 "F" "E"
                                 "G" "B"
                                 "H" "G"
                                 "I" "D"
                                 "J" "E"
                                 "K" "J"
                                 "L" "K"
                                 "YOU" "K"
                                 "SAN" "I"} "YOU" "SAN")))))

(def day06-input (u/parse-puzzle-input t/parse 2019 6))

(deftest part1-test
  (testing "Can reproduce the answer for part1"
    (is (= 261306 (t/part1 day06-input)))))

(deftest part2-test
  (testing "Can reproduce the answer for part2"
    (is (= 382 (t/part2 day06-input)))))
