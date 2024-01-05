(ns aoc-clj.2016.day04-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day04 :as t]))

(def d04-s00
  (map t/parse-line
       ["aaaaa-bbb-z-y-x-123[abxyz]"
        "a-b-c-d-e-f-g-h-987[abcde]"
        "not-a-real-room-404[oarel]"
        "totally-real-room-200[decoy]"]))

(deftest real-room?
  (testing "Identifies which rooms are real (not decoys)"
    (is (= [true true true false]
           (map t/real-room? d04-s00)))))

(deftest real-room-sector-id-sum
  (testing "Adds up the sector ids of all the real rooms"
    (is (= 1514 (t/real-room-sector-id-sum d04-s00)))))

(def day04-input (u/parse-puzzle-input t/parse 2016 4))

(deftest part1-test
  (testing "Reproduces the answer for day04, part1"
    (is (= 361724 (t/part1 day04-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day04, part2"
    (is (= 482 (t/part2 day04-input)))))