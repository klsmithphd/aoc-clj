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

(deftest part1-invalid?-test
  (testing "Correctly identifies IDs that are deemed invalid in part 1"
    (is (= true (d02/part1-invalid? 11)))
    (is (= true (d02/part1-invalid? 22)))
    (is (= true (d02/part1-invalid? 1010)))
    (is (= false (d02/part1-invalid? 9119)))
    (is (= true (d02/part1-invalid? 1188511885)))
    (is (= true (d02/part1-invalid? 222222)))
    (is (= true (d02/part1-invalid? 446446)))
    (is (= true (d02/part1-invalid? 38593859)))))

(deftest part2-invalid?-test
  (testing "Correctly identifies the IDs that are invalid in part 2"
    (is (= true (d02/part2-invalid? 11)))
    (is (= true (d02/part2-invalid? 22)))
    (is (= true (d02/part2-invalid? 99)))
    (is (= true (d02/part2-invalid? 111)))
    (is (= true (d02/part2-invalid? 999)))
    (is (= true (d02/part2-invalid? 1010)))
    (is (= true (d02/part2-invalid? 1188511885)))
    (is (= true (d02/part2-invalid? 222222)))
    (is (= true (d02/part2-invalid? 565656)))))

(deftest part1-all-invalid-ids-test
  (testing "For a collection of ranges, returns all the invalid IDs (in part 1)"
    (is (= [11 22 99 1010 1188511885 222222 446446 38593859]
           (d02/all-invalid-ids d02/part1-invalid? d02-s00)))))

(deftest part2-all-invalid-ids-test
  (testing "For a collection of ranges, returns all the invalid IDs (in part 2)"
    (is (= [11 22 99 111 999 1010 1188511885 222222 446446 38593859
            565656 824824824 2121212121]
           (d02/all-invalid-ids d02/part2-invalid? d02-s00)))))

(deftest part1-all-invalid-ids-sum-test
  (testing "Adds up all the invalid IDs (in part 1) in the ranges given"
    (is (= 1227775554 (d02/all-invalid-ids-sum d02/part1-invalid? d02-s00)))))

(deftest part2-all-invalid-ids-sum-test
  (testing "Adds up all the invalid IDs (in part 2) in the ranges given"
    (is (=  4174379265 (d02/all-invalid-ids-sum d02/part2-invalid? d02-s00)))))

(def day02-input (u/parse-puzzle-input d02/parse 2025 2))

(deftest part1-test
  (testing "Reproduces the answer for day02, part1"
    (is (= 32976912643 (d02/part1 day02-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day02, part2"
    (is (= 54446379122 (d02/part2 day02-input)))))