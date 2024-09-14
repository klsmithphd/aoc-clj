(ns aoc-clj.2017.day10-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day10 :as d10]))

(def d10-s00-raw "3,4,1,5")
(def d10-s00 [3 4 1 5])

(deftest twist-test
  (testing "Executes one twist instruction and returns the new state"
    (is (= {:v [2 1 0 3 4] :pos 3 :skip 1}
           (d10/twist {:v [0 1 2 3 4] :pos 0 :skip 0} 3)))
    (is (= {:v [4 3 0 1 2] :pos 3 :skip 2}
           (d10/twist {:v [2 1 0 3 4] :pos 3 :skip 1} 4)))
    (is (= {:v [4 3 0 1 2] :pos 1 :skip 3}
           (d10/twist {:v [4 3 0 1 2] :pos 3 :skip 2} 1)))
    (is (= {:v [3 4 2 1 0] :pos 4 :skip 4}
           (d10/twist {:v [4 3 0 1 2] :pos 1 :skip 3} 5)))))

(deftest first-two-nums-prod-test
  (testing "Applies all the twists and then computes product of first two nums"
    (is (= 12 (d10/first-two-nums-prod 5 d10-s00)))))

(deftest ascii-codes-test
  (testing "Converts character strings to their ASCII code values"
    (is (= [49 44 50 44 51] (d10/ascii-codes "1,2,3")))))

(deftest knot-hash-test
  (testing "Computes the Knot Hash of a string"
    (is (= "a2582a3a0e66e6e86e3812dcb672a272" (d10/knot-hash "")))
    (is (= "33efeb34ea91902bb2f59c9920caa6cd" (d10/knot-hash "AoC 2017")))
    (is (= "3efbe78a8d82f29979031a4aa0b16a9d" (d10/knot-hash "1,2,3")))
    (is (= "63960835bcdc130f0b66d7ff4f6a5a8e" (d10/knot-hash "1,2,4")))))

(def day10-input (u/parse-puzzle-input d10/parse 2017 10))

(deftest part1-test
  (testing "Reproduces the answer for day10, part1"
    (is (= 38628 (d10/part1 day10-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day10, part2"
    (is (= "e1462100a34221a7f0906da15c1c979a" (d10/part2 day10-input)))))