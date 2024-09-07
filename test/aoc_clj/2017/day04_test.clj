(ns aoc-clj.2017.day04-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day04 :as d04]))

(def d04-s00-raw
  ["aa bb cc dd ee"
   "aa bb cc dd aa"
   "aa bb cc dd aaa"])

(def d04-s00
  [["aa" "bb" "cc" "dd" "ee"]
   ["aa" "bb" "cc" "dd" "aa"]
   ["aa" "bb" "cc" "dd" "aaa"]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d04-s00 (d04/parse d04-s00-raw)))))

(def d04-s01
  [["abcde" "fghij"]
   ["abcde" "xyz" "ecdab"]
   ["a" "ab" "abc" "abd" "abf" "abj"]
   ["iiii" "oiii" "ooii" "oooi" "oooo"]
   ["oiii" "ioii" "iioi" "iiio"]])

(deftest is-valid-part1?-test
  (testing "Identifies whether a passphrase is valid in part1"
    (is (= true (d04/is-valid-part1? (nth d04-s00 0))))
    (is (= false (d04/is-valid-part1? (nth d04-s00 1))))
    (is (= true (d04/is-valid-part1? (nth d04-s00 2))))))

(deftest is-valid-part2?-test
  (testing "Identifies whether a passphrase is valid in part2"
    (is (= true (d04/is-valid-part2? (nth d04-s01 0))))
    (is (= false (d04/is-valid-part2? (nth d04-s01 1))))
    (is (= true (d04/is-valid-part2? (nth d04-s01 2))))
    (is (= true (d04/is-valid-part2? (nth d04-s01 3))))
    (is (= false (d04/is-valid-part2? (nth d04-s01 4))))))

(deftest valid-passphrase-count-test
  (testing "Counts the number of valid passphrases"
    (is (= 2 (d04/valid-passphrase-count d04/is-valid-part1? d04-s00)))
    (is (= 3 (d04/valid-passphrase-count d04/is-valid-part2? d04-s01)))))

(def day04-input (u/parse-puzzle-input d04/parse 2017 4))

(deftest part1-test
  (testing "Reproduces the answer for day04, part1"
    (is (= 455 (d04/part1 day04-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day04, part2"
    (is (= 186 (d04/part2 day04-input)))))