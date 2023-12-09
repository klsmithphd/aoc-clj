(ns aoc-clj.2023.day08-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2023.day08 :as t]))

(def d08-s01-raw ["RL"
                  ""
                  "AAA = (BBB, CCC)"
                  "BBB = (DDD, EEE)"
                  "CCC = (ZZZ, GGG)"
                  "DDD = (DDD, DDD)"
                  "EEE = (EEE, EEE)"
                  "GGG = (GGG, GGG)"
                  "ZZZ = (ZZZ, ZZZ)"])

(def d08-s02-raw ["LLR"
                  ""
                  "AAA = (BBB, BBB)"
                  "BBB = (AAA, ZZZ)"
                  "ZZZ = (ZZZ, ZZZ)"])

(def d08-s01
  {:instructions [:right :left]
   :nodes {"AAA" {:left "BBB" :right "CCC"}
           "BBB" {:left "DDD" :right "EEE"}
           "CCC" {:left "ZZZ" :right "GGG"}
           "DDD" {:left "DDD" :right "DDD"}
           "EEE" {:left "EEE" :right "EEE"}
           "GGG" {:left "GGG" :right "GGG"}
           "ZZZ" {:left "ZZZ" :right "ZZZ"}}})

(def d08-s02
  {:instructions [:left :left :right]
   :nodes {"AAA" {:left "BBB" :right "BBB"}
           "BBB" {:left "AAA" :right "ZZZ"}
           "ZZZ" {:left "ZZZ" :right "ZZZ"}}})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d08-s01 (t/parse d08-s01-raw)))
    (is (= d08-s02 (t/parse d08-s02-raw)))))

(deftest steps-to-zzz
  (testing "Correctly counts the number of steps to get to ZZZ"
    (is (= 2 (t/steps-to-zzz d08-s01)))
    (is (= 6 (t/steps-to-zzz d08-s02)))))

(def day08-input (u/parse-puzzle-input t/parse 2023 8))

(deftest day08-part1-soln
  (testing "Reproduces the answer for day08, part1"
    (is (= 12169 (t/day08-part1-soln day08-input)))))