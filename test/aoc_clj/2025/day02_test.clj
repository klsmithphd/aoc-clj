(ns aoc-clj.2025.day02-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2025.day02 :as d02]))

(def d02-s00-raw
  ["11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"])

(def d02-s00
  [[11 22]
   [95 115]
   [998 1012]
   [1188511880 1188511890]
   [222220 222224]
   [1698522 1698528]
   [446443 446449]
   [38593856 38593862]
   [565653 565659]
   [824824821 824824827]
   [2121212118 2121212124]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d02-s00 (d02/parse d02-s00-raw)))))


(def day02-input (u/parse-puzzle-input d02/parse 2025 1))

(deftest part1-test
  (testing "Reproduces the answer for day02, part1"
    (is (= :not-implemented (d02/part1 day02-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day02, part2"
    (is (= :not-implemented (d02/part2 day02-input)))))