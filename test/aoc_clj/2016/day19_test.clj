(ns aoc-clj.2016.day19-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day19 :as d19]))


(deftest winning-elf-test
  (testing "Finds the winning elf in the exchange"
    (is (= 1 (d19/winning-elf 1)))
    (is (= 1 (d19/winning-elf 2)))
    (is (= 3 (d19/winning-elf 3)))
    (is (= 1 (d19/winning-elf 4)))
    (is (= 3 (d19/winning-elf 5)))
    (is (= 5 (d19/winning-elf 6)))
    (is (= 7 (d19/winning-elf 7)))
    (is (= 1 (d19/winning-elf 8)))
    (is (= 3 (d19/winning-elf 9)))
    (is (= 5 (d19/winning-elf 10)))
    (is (= 7 (d19/winning-elf 11)))
    (is (= 9 (d19/winning-elf 12)))
    (is (= 11 (d19/winning-elf 13)))
    (is (= 13 (d19/winning-elf 14)))
    (is (= 15 (d19/winning-elf 15)))
    (is (= 1 (d19/winning-elf 16)))))

(def day19-input (u/parse-puzzle-input d19/parse 2016 19))

(deftest part1-test
  (testing "Reproduces the answer for day19, part1"
    (is (= 1834903 (d19/part1 day19-input)))))
