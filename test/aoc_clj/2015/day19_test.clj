(ns aoc-clj.2015.day19-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day19 :as t]))

(def d19-s00
  (t/parse
   ["e => H"
    "e => O"
    "H => HO"
    "H => OH"
    "O => HH"
    ""
    "HOH"]))

(def d19-s01
  (t/parse
   ["e => H"
    "e => O"
    "H => HO"
    "H => OH"
    "O => HH"
    ""
    "HOHOHO"]))

(deftest distinct-replacements-test
  (testing "Can count the correct number of distinct molecules generated"
    (is (= 4 (count (t/distinct-molecules d19-s00))))
    (is (= 7 (count (t/distinct-molecules d19-s01))))))

(deftest fabrication-steps-test
  (testing "Can count the minimum number of steps to fabricate the molecule"
    (is (= 3 (count (t/fabrication-steps d19-s00))))
    (is (= 6 (count (t/fabrication-steps d19-s01))))))

(def day19-input (u/parse-puzzle-input t/parse 2015 19))

(deftest part1-test
  (testing "Reproduces the answer for day19, part1"
    (is (= 518 (t/part1 day19-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day19, part2"
    (is (= 200 (t/part2 day19-input)))))