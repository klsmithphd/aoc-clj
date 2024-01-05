(ns aoc-clj.2016.day06-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day06 :as t]))

(def d06-s00
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
    (is (= "easter" (t/most-frequent-chars d06-s00)))))

(deftest least-frequent-chars
  (testing "Decodes the least frequent character in each position"
    (is (= "advent" (t/least-frequent-chars d06-s00)))))

(def day06-input (u/parse-puzzle-input t/parse 2016 6))

(deftest part1-test
  (testing "Reproduces the answer for day06, part1"
    (is (= "agmwzecr" (t/part1 day06-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day06, part2"
    (is (= "owlaxqvq" (t/part2 day06-input)))))