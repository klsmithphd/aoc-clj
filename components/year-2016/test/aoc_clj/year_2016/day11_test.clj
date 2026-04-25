(ns aoc-clj.year-2016.day11-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.util.interface :as u]
            [aoc-clj.year-2016.day11 :as d11]))

(def d11-s00-raw
  ["The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip."
   "The second floor contains a hydrogen generator."
   "The third floor contains a lithium generator."
   "The fourth floor contains nothing relevant."])

(def d11-s01-raw
  ["The first floor contains a hydrogen generator."
   "The second floor contains a hydrogen-compatible microchip."
   "The third floor contains nothing relevant."
   "The fourth floor contains nothing relevant."])

(def d11-s00
  [0 {[0 1] 1 [0 2] 1}])

(def d11-s01
  [0 {[1 0] 1}])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d11-s00 (d11/parse d11-s00-raw)))
    (is (= d11-s01 (d11/parse d11-s01-raw)))))

(deftest endstate-test
  (testing "Defines the desired endstate (with all objects on the fourth floor), given the initial state"
    (is (= [3 {[3 3] 2}] (d11/endstate d11-s00)))))

(deftest selectors-test
  (let [pairs {[0 1] 1 [1 2] 1 [1 1] 1 [3 3] 2}]
    (testing "chips-on includes both lone and paired chips on the floor"
      (is (= #{[1 2] [1 1]} (set (d11/chips-on pairs 1)))))
    (testing "gens-on includes both lone and paired generators on the floor"
      (is (= #{[0 1] [1 1]} (set (d11/gens-on pairs 1)))))
    (testing "matched-on requires both chip and generator on the floor"
      (is (= #{[1 1]} (set (d11/matched-on pairs 1))))
      (is (= #{[3 3]} (set (d11/matched-on pairs 3)))))))

(deftest pair-doubles-multiplicity-test
  (testing "Same tuple-type may be picked twice when its multiplicity is at least 2"
    (let [updates (d11/pair-doubles d11/chips-on d11/chip-candidate
                                    {[0 1] 2} 0 1)]
      (is (= [[[[0 1] [1 1]] [[0 1] [1 1]]]] updates))))
  (testing "Same tuple-type is not picked twice when its multiplicity is 1"
    (is (empty? (d11/pair-doubles d11/chips-on d11/chip-candidate
                                  {[0 1] 1} 0 1)))))

(deftest legal?-test
  (testing "Empty state is legal"
    (is (true? (d11/legal? [0 {}]))))
  (testing "All paired tuples on a single floor is legal"
    (is (true? (d11/legal? [0 {[0 0] 3}]))))
  (testing "A lone chip is legal when no generators share its floor"
    (is (true? (d11/legal? [0 {[1 2] 1 [0 2] 1}]))))
  (testing "A lone chip with a foreign lone generator on the same floor is illegal"
    (is (false? (d11/legal? [0 {[0 1] 1 [2 0] 1}]))))
  (testing "A lone chip with a paired chip+gen of another element on the same floor is illegal"
    (is (false? (d11/legal? [0 {[0 0] 1 [0 1] 1}])))))

(deftest extra-items-test
  (testing "Adds two matched [0 0] pair-tuples without disturbing the elevator floor"
    (is (= [0 {[0 1] 1 [0 2] 1 [0 0] 2}]
           (d11/extra-items d11-s00)))))

(deftest move-count
  (testing "Counts the minimum number of moves "
    (is (= 11 (d11/move-count d11-s00)))))


(def day11-input (u/parse-puzzle-input d11/parse 2016 11))

(deftest part1
  (testing "Reproduces the answer for day11, part1"
    (is (= 31 (d11/part1 day11-input)))))

(deftest  part2
  (testing "Reproduces the answer for day11, part2"
    (is (= 55 (d11/part2 day11-input)))))