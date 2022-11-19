(ns aoc-clj.2015.day19-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2015.day19 :as t]))

(def day19-sample1
  (t/parse
   ["e => H"
    "e => O"
    "H => HO"
    "H => OH"
    "O => HH"
    ""
    "HOH"]))

(def day19-sample2
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
    (is (= 4 (count (t/distinct-molecules day19-sample1))))
    (is (= 7 (count (t/distinct-molecules day19-sample2))))))

(deftest fabrication-steps-test
  (testing "Can count the minimum number of steps to fabricate the molecule"
    (is (= 3 (count (t/fabrication-steps day19-sample1))))
    (is (= 6 (count (t/fabrication-steps day19-sample2))))))

(deftest day19-part1-soln
  (testing "Reproduces the answer for day19, part1"
    (is (= 518 (t/day19-part1-soln)))))

(deftest day19-part2-soln
  (testing "Reproduces the answer for day19, part2"
    (is (= 200 (t/day19-part2-soln)))))