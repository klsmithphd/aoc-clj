(ns aoc-clj.2016.day08-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day08 :as t]))

(def d08-s00
  (map t/parse-line
       ["rect 3x2"
        "rotate column x=1 by 1"
        "rotate row y=0 by 4"
        "rotate column x=1 by 1"]))

(deftest apply-instructions
  (testing "Can apply the instructions for the sample data"
    (is (= #{[1 0] [4 0] [6 0] [0 1] [2 1] [1 2]}
           (->>  (t/apply-instructions 7 3 d08-s00)
                 :grid
                 (filter #(= :on (val %)))
                 keys
                 set)))))

(def day08-input (u/parse-puzzle-input t/parse 2016 8))

(deftest part1-test
  (testing "Reproduces the answer for day08, part1"
    (is (= 128 (t/part1 day08-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day08, part2"
    (is (= "EOARGPHYAO" (t/part2 day08-input)))))