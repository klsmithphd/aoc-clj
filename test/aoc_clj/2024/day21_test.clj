(ns aoc-clj.2024.day21-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2024.day21 :as d21]))

(def d21-s00
  ["029A"
   "980A"
   "179A"
   "456A"
   "379A"])

(deftest keypad-paths-test
  (testing "Constructs the mapping between any two keys and the shortest moves
            necessary to get from one key to the other"
    (is (= {[\A \^] ["<A"]
            [\A \<] ["v<<A" "<v<A"]
            [\A \>] ["vA"]
            [\A \A] ["A"]
            [\A \v] ["v<A" "<vA"]

            [\^ \^] ["A"]
            [\^ \<] ["v<A"]
            [\^ \>] [">vA" "v>A"]
            [\^ \A] [">A"]
            [\^ \v] ["vA"]

            [\< \^] [">^A"]
            [\< \<] ["A"]
            [\< \>] [">>A"]
            [\< \A] [">>^A" ">^>A"]
            [\< \v] [">A"]

            [\> \^] ["^<A" "<^A"]
            [\> \<] ["<<A"]
            [\> \>] ["A"]
            [\> \A] ["^A"]
            [\> \v] ["<A"]

            [\v \^] ["^A"]
            [\v \<] ["<A"]
            [\v \>] [">A"]
            [\v \A] [">^A" "^>A"]
            [\v \v] ["A"]}
           (d21/keypad-paths d21/directional-keypad)))))

(deftest cost-test
  (testing "Computes the cost of entering a code sequence on the numeric keypad"
    (is (= 4  (d21/cost d21/numeric-paths 0 (nth d21-s00 0))))
    (is (= 12 (d21/cost d21/numeric-paths 1 (nth d21-s00 0))))
    (is (= 28 (d21/cost d21/numeric-paths 2 (nth d21-s00 0))))
    (is (= 68 (d21/cost d21/numeric-paths 3 (nth d21-s00 0))))

    (is (= 60 (d21/cost d21/numeric-paths 3 (nth d21-s00 1))))
    (is (= 68 (d21/cost d21/numeric-paths 3 (nth d21-s00 2))))
    (is (= 64 (d21/cost d21/numeric-paths 3 (nth d21-s00 3))))
    (is (= 64 (d21/cost d21/numeric-paths 3 (nth d21-s00 4))))))

(deftest complexity-sum
  (testing "Computes the sum of the complexity of the codes"
    (is (= 126384 (d21/complexity-sum 2 d21-s00)))))

(def day21-input (u/parse-puzzle-input d21/parse 2024 21))

(deftest part1-test
  (testing "Reproduces the answer for day21, part1"
    (is (= 94284 (d21/part1 day21-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day21, part2"
    (is (= 116821732384052 (d21/part2 day21-input)))))