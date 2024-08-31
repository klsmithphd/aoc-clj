(ns aoc-clj.2016.day24-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day24 :as d24]))

(def d24-s00-raw
  ["###########"
   "#0.1.....2#"
   "#.#######.#"
   "#4.......3#"
   "###########"])

(def d24-s00
  {:start {:stop1 2  :stop2 8 :stop3 10 :stop4 2}
   :stop1 {:start 2  :stop2 6 :stop3 8  :stop4 4}
   :stop2 {:start 8  :stop1 6 :stop3 2  :stop4 10}
   :stop3 {:start 10 :stop1 8 :stop2 2  :stop4 8}
   :stop4 {:start 2  :stop1 4 :stop3 8  :stop2 10}})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d24-s00 (d24/parse d24-s00-raw)))))

(deftest shortest-path-test
  (testing "Finds the shortest path to visit all locations of interest"
    (is (= 14 (d24/shortest-path d24-s00 :part1)))))

(def day24-input (u/parse-puzzle-input d24/parse 2016 24))

(deftest part1-test
  (testing "Reproduces the answer for day24, part1"
    (is (= 428 (d24/part1 day24-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day24, part2"
    (is (= 680 (d24/part2 day24-input)))))