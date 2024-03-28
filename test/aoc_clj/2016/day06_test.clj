(ns aoc-clj.2016.day06-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day06 :as d06]))

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

(deftest most-frequent-chars-test
  (testing "Decodes the most frequent character in each position"
    (is (= "easter" (d06/most-frequent-chars d06-s00)))))

(deftest least-frequent-chars-test
  (testing "Decodes the least frequent character in each position"
    (is (= "advent" (d06/least-frequent-chars d06-s00)))))

(def day06-input (u/parse-puzzle-input d06/parse 2016 6))

(deftest part1-test
  (testing "Reproduces the answer for day06, part1"
    (is (= "agmwzecr" (d06/part1 day06-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day06, part2"
    (is (= "owlaxqvq" (d06/part2 day06-input)))))