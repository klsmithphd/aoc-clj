(ns aoc-clj.2023.day02-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2023.day02 :as t]))

(def d02-s00-raw
  ["Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
   "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue"
   "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red"
   "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red"
   "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"])

(def d02-s00
  [{:id 1, :sets [{:blue 3, :red 4}
                  {:red 1, :green 2, :blue 6}
                  {:green 2}]}
   {:id 2, :sets [{:blue 1, :green 2}
                  {:green 3, :blue 4, :red 1}
                  {:green 1, :blue 1}]}
   {:id 3, :sets [{:green 8, :blue 6, :red 20}
                  {:blue 5, :red 4, :green 13}
                  {:green 5, :red 1}]}
   {:id 4, :sets [{:green 1, :red 3, :blue 6}
                  {:green 3, :red 6}
                  {:green 3, :blue 15, :red 14}]}
   {:id 5, :sets [{:red 6, :blue 1, :green 3}
                  {:blue 2, :red 1, :green 2}]}])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d02-s00 (t/parse d02-s00-raw)))))

(deftest possible-game-test
  (testing "Determines which games are possible"
    (is (= [true true false false true] (map t/possible-game d02-s00)))))

(deftest possible-game-id-sum-test
  (testing "Computes sum of the ids of all possible games"
    (is (= 8 (t/possible-game-id-sum d02-s00)))))

(deftest fewest-cubes-test
  (testing "Computes the fewest cubes of each color in a game"
    (is (= [{:red 4 :green 2 :blue 6}
            {:red 1 :green 3 :blue 4}
            {:red 20 :green 13 :blue 6}
            {:red 14 :green 3 :blue 15}
            {:red 6 :green 3 :blue 2}]
           (map t/fewest-cubes d02-s00)))))

(deftest power-fewest-cubes-test
  (testing "Computes the `power` of the fewest cubes of each color"
    (is (= [48 12 1560 630 36] (map t/power-fewest-cubes d02-s00)))))

(deftest power-fewest-cubes-sum-test
  (testing "Computes the sum of the powers of the fewest cubes"
    (is (= 2286 (t/power-fewest-cubes-sum d02-s00)))))

(def day02-input (u/parse-puzzle-input t/parse 2023 2))

(deftest part1-test
  (testing "Reproduces the answer for day02, part1"
    (is (= 2162 (t/part1 day02-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day02, part2"
    (is (= 72513 (t/part2 day02-input)))))