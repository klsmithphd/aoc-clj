(ns aoc-clj.2016.day04-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2016.day04 :as t]))

(def day04-sample
  (map t/parse-line
       ["aaaaa-bbb-z-y-x-123[abxyz]"
        "a-b-c-d-e-f-g-h-987[abcde]"
        "not-a-real-room-404[oarel]"
        "totally-real-room-200[decoy]"]))

(deftest real-room?
  (testing "Identifies which rooms are real (not decoys)"
    (is (= [true true true false]
           (map t/real-room? day04-sample)))))

(deftest real-room-sector-id-sum
  (testing "Adds up the sector ids of all the real rooms"
    (is (= 1514 (t/real-room-sector-id-sum day04-sample)))))

(deftest day04-part1-soln
  (testing "Reproduces the answer for day04, part1"
    (is (= 361724 (t/day04-part1-soln)))))

(deftest day04-part2-soln
  (testing "Reproduces the answer for day04, part2"
    (is (= 482 (t/day04-part2-soln)))))