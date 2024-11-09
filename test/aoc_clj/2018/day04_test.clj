(ns aoc-clj.2018.day04-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2018.day04 :as d04]))

(def d04-s00-raw
  ["[1518-11-01 00:00] Guard #10 begins shift"
   "[1518-11-01 00:05] falls asleep"
   "[1518-11-01 00:25] wakes up"
   "[1518-11-01 00:30] falls asleep"
   "[1518-11-01 00:55] wakes up"
   "[1518-11-01 23:58] Guard #99 begins shift"
   "[1518-11-02 00:40] falls asleep"
   "[1518-11-02 00:50] wakes up"
   "[1518-11-03 00:05] Guard #10 begins shift"
   "[1518-11-03 00:24] falls asleep"
   "[1518-11-03 00:29] wakes up"
   "[1518-11-04 00:02] Guard #99 begins shift"
   "[1518-11-04 00:36] falls asleep"
   "[1518-11-04 00:46] wakes up"
   "[1518-11-05 00:03] Guard #99 begins shift"
   "[1518-11-05 00:45] falls asleep"
   "[1518-11-05 00:55] wakes up"])

(def d04-s00
  {10 [[5 25] [30 55] [24 29]]
   99 [[40 50] [36 46] [45 55]]})

(deftest parse-test
  (testing "Can identify the guard-id, and sleep windows"
    (is (= d04-s00 (d04/parse d04-s00-raw)))))

(deftest strategy1
  (testing "Identify the guard who sleeps the most and the minute they sleep the most"
    (is (= [10 24]
           (d04/sleepiest-guard-and-optimal-minute d04-s00)))))

(deftest strategy2
  (testing "Identify the guard who sleeps the most at any given minute"
    (is (= [99 45]
           (d04/guard-most-frequently-asleep-at-minute d04-s00)))))

(def day04-input (u/parse-puzzle-input d04/parse 2018 4))

(deftest part1-test
  (testing "Reproduces the answer for day04, part1"
    (is (= 74743 (d04/part1 day04-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day04, part2"
    (is (= 132484 (d04/part2 day04-input)))))