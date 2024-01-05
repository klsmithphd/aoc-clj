(ns aoc-clj.2023.day08-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2023.day08 :as t]))

(def d08-s00-raw ["RL"
                  ""
                  "AAA = (BBB, CCC)"
                  "BBB = (DDD, EEE)"
                  "CCC = (ZZZ, GGG)"
                  "DDD = (DDD, DDD)"
                  "EEE = (EEE, EEE)"
                  "GGG = (GGG, GGG)"
                  "ZZZ = (ZZZ, ZZZ)"])

(def d08-s01-raw ["LLR"
                  ""
                  "AAA = (BBB, BBB)"
                  "BBB = (AAA, ZZZ)"
                  "ZZZ = (ZZZ, ZZZ)"])

(def d08-s02-raw ["LR"
                  ""
                  "11A = (11B, XXX)"
                  "11B = (XXX, 11Z)"
                  "11Z = (11B, XXX)"
                  "22A = (22B, XXX)"
                  "22B = (22C, 22C)"
                  "22C = (22Z, 22Z)"
                  "22Z = (22B, 22B)"
                  "XXX = (XXX, XXX)"])

(def d08-s00
  {:instructions [:right :left]
   :nodes {"AAA" {:left "BBB" :right "CCC"}
           "BBB" {:left "DDD" :right "EEE"}
           "CCC" {:left "ZZZ" :right "GGG"}
           "DDD" {:left "DDD" :right "DDD"}
           "EEE" {:left "EEE" :right "EEE"}
           "GGG" {:left "GGG" :right "GGG"}
           "ZZZ" {:left "ZZZ" :right "ZZZ"}}})

(def d08-s01
  {:instructions [:left :left :right]
   :nodes {"AAA" {:left "BBB" :right "BBB"}
           "BBB" {:left "AAA" :right "ZZZ"}
           "ZZZ" {:left "ZZZ" :right "ZZZ"}}})

(def d08-s02
  {:instructions [:left :right]
   :nodes {"11A" {:left "11B" :right "XXX"}
           "11B" {:left "XXX" :right "11Z"}
           "11Z" {:left "11B" :right "XXX"}
           "22A" {:left "22B" :right "XXX"}
           "22B" {:left "22C" :right "22C"}
           "22C" {:left "22Z" :right "22Z"}
           "22Z" {:left "22B" :right "22B"}
           "XXX" {:left "XXX" :right "XXX"}}})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d08-s00 (t/parse d08-s00-raw)))
    (is (= d08-s01 (t/parse d08-s01-raw)))
    (is (= d08-s02 (t/parse d08-s02-raw)))))

(deftest steps-to-zzz
  (testing "Correctly counts the number of steps to get to ZZZ"
    (is (= 2 (t/steps-to-zzz d08-s00 "AAA")))
    (is (= 6 (t/steps-to-zzz d08-s01 "AAA")))))

(deftest start-nodes-test
  (testing "Finds all the start nodes"
    (is (= ["11A" "22A"] (t/start-nodes (:nodes d08-s02))))))

(deftest ghost-steps-to-zzz
  (testing "Counts the number of steps for all starting points to get to a
            node ending in Z"
    (is (= 6 (t/ghost-steps-to-zzz d08-s02)))))

(def day08-input (u/parse-puzzle-input t/parse 2023 8))

(deftest part1-test
  (testing "Reproduces the answer for day08, part1"
    (is (= 12169 (t/part1 day08-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day08, part2"
    (is (= 12030780859469 (t/part2 day08-input)))))