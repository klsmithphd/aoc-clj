(ns aoc-clj.2016.day06-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2016.day06 :as t]))

(def day06-sample
  ["eedadn"
   "drvtee"
   "eandsr"
   "raavrd"
   "atevrs"
   "tsrnev"
   "sdttsa"
   "rasrtv"
   "nssdts"
   "ntnada"
   "svetve"
   "tesnvt"
   "vntsnd"
   "vrdear"
   "dvrsen"
   "enarar"])

(deftest most-frequent-chars
  (testing "Decodes the most frequent character in each position"
    (is (= "easter" (t/most-frequent-chars day06-sample)))))

(deftest least-frequent-chars
  (testing "Decodes the least frequent character in each position"
    (is (= "advent" (t/least-frequent-chars day06-sample)))))

(deftest day06-part1-soln
  (testing "Reproduces the answer for day06, part1"
    (is (= "agmwzecr" (t/day06-part1-soln)))))

(deftest day06-part2-soln
  (testing "Reproduces the answer for day06, part2"
    (is (= "owlaxqvq" (t/day06-part2-soln)))))