(ns aoc-clj.2016.day15-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day15 :as d15]))

(def d15-s00-raw
  ["Disc #1 has 5 positions; at time=0, it is at position 4."
   "Disc #2 has 2 positions; at time=0, it is at position 1."])

(def d15-s00
  [{:disc 1 :slot-count 5 :init-pos 4}
   {:disc 2 :slot-count 2 :init-pos 1}])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d15-s00 (d15/parse d15-s00-raw)))))

(deftest drop-time-test
  (testing "Computes the earliest time to drop the capsule"
    (is (= 5 (d15/drop-time d15-s00)))))

(def day15-input (u/parse-puzzle-input d15/parse 2016 15))

(deftest part1-test
  (testing "Reproduces the answer for day15, part1"
    (is (= 376777 (d15/part1 day15-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day15, part2"
    (is (= 3903937 (d15/part2 day15-input)))))
