(ns aoc-clj.2016.day19-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day19 :as d19]))


(deftest part1-winning-elf-test
  (testing "Finds the winning elf in the exchange"
    (is (= 1 (d19/part1-winning-elf 1)))
    (is (= 1 (d19/part1-winning-elf 2)))
    (is (= 3 (d19/part1-winning-elf 3)))
    (is (= 1 (d19/part1-winning-elf 4)))
    (is (= 3 (d19/part1-winning-elf 5)))
    (is (= 5 (d19/part1-winning-elf 6)))
    (is (= 7 (d19/part1-winning-elf 7)))
    (is (= 1 (d19/part1-winning-elf 8)))
    (is (= 3 (d19/part1-winning-elf 9)))
    (is (= 5 (d19/part1-winning-elf 10)))
    (is (= 7 (d19/part1-winning-elf 11)))
    (is (= 9 (d19/part1-winning-elf 12)))
    (is (= 11 (d19/part1-winning-elf 13)))
    (is (= 13 (d19/part1-winning-elf 14)))
    (is (= 15 (d19/part1-winning-elf 15)))
    (is (= 1 (d19/part1-winning-elf 16)))))

(deftest part2-winning-elf-test
  (testing "Finds the winning elf in the exchange"
    (is (= 1 (d19/part2-winning-elf 1)))
    (is (= 1 (d19/part2-winning-elf 2)))
    (is (= 3 (d19/part2-winning-elf 3)))
    (is (= 1 (d19/part2-winning-elf 4)))
    (is (= 2 (d19/part2-winning-elf 5)))
    (is (= 3 (d19/part2-winning-elf 6)))
    (is (= 5 (d19/part2-winning-elf 7)))
    (is (= 7 (d19/part2-winning-elf 8)))
    (is (= 9 (d19/part2-winning-elf 9)))
    (is (= 1 (d19/part2-winning-elf 10)))
    (is (= 2 (d19/part2-winning-elf 11)))
    (is (= 3 (d19/part2-winning-elf 12)))
    (is (= 9 (d19/part2-winning-elf 18)))
    (is (= 11 (d19/part2-winning-elf 19)))
    (is (= 25 (d19/part2-winning-elf 26)))
    (is (= 27 (d19/part2-winning-elf 27)))
    (is (= 1  (d19/part2-winning-elf 28)))
    (is (= 27 (d19/part2-winning-elf 54)))
    (is (= 29 (d19/part2-winning-elf 55)))
    (is (= 79 (d19/part2-winning-elf 80)))
    (is (= 81 (d19/part2-winning-elf 81)))))

(def day19-input (u/parse-puzzle-input d19/parse 2016 19))

(deftest part1-test
  (testing "Reproduces the answer for day19, part1"
    (is (= 1834903 (d19/part1 day19-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day19, part2"
    (is (= 1420280 (d19/part2 day19-input)))))