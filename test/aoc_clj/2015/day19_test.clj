(ns aoc-clj.2015.day19-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day19 :as d19]))

(def d19-s00-raw
  ["e => H"
   "e => O"
   "H => HO"
   "H => OH"
   "O => HH"
   ""
   "HOH"])

(def d19-s01-raw
  ["e => H"
   "e => O"
   "H => HO"
   "H => OH"
   "O => HH"
   ""
   "HOHOHO"])

(def d19-s00
  {:replacements [["e" "H"]
                  ["e" "O"]
                  ["H" "HO"]
                  ["H" "OH"]
                  ["O" "HH"]]
   :molecule "HOH"})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d19-s00 (d19/parse d19-s00-raw)))))

(def d19-s01 (d19/parse d19-s01-raw))

(deftest match-indices-test
  (testing "Finds the indices of all matching substrings in a string"
    (is (= [[0 2] [2 4] [4 6]] (d19/match-ranges "HOHOHO" "HO")))
    (is (= [[1 3] [3 5]]       (d19/match-ranges "HOHOHO" "OH")))
    (is (= []                  (d19/match-ranges "HOHOHO" "e")))
    (is (= [[1 4] [3 6]]       (d19/match-ranges "HOHOHO" "OHO")))))

(deftest single-replacements-test
  (testing "Returns all possible new strings for a given replacement pair"
    (is (= ["HOOH" "HOHO"] (d19/single-replacements "HOH" ["H" "HO"])))
    (is (= ["OHOH" "HOOH"] (d19/single-replacements "HOH" ["H" "OH"])))
    (is (= ["HHHH"]        (d19/single-replacements "HOH" ["O" "HH"])))))

(deftest distinct-molecules-test
  (testing "Can count the correct number of distinct molecules generated"
    (is (= 4 (count (d19/distinct-molecules d19-s00))))
    (is (= 7 (count (d19/distinct-molecules d19-s01))))))

(deftest fabrication-steps-test
  (testing "Can count the minimum number of steps to fabricate the molecule"
    (is (= 3 (count (d19/fabrication-steps d19-s00))))
    (is (= 6 (count (d19/fabrication-steps d19-s01))))))

(def day19-input (u/parse-puzzle-input d19/parse 2015 19))

(deftest part1-test
  (testing "Reproduces the answer for day19, part1"
    (is (= 518 (d19/part1 day19-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day19, part2"
    (is (= 200 (d19/part2 day19-input)))))