(ns aoc-clj.2020.day13-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2020.day13 :as t]))

(def d13-s00 (t/parse ["939" "7,13,x,x,59,x,31,19"]))
(def d13-s01 {:buses '(17,x,13,19)})
(def d13-s02 {:buses '(67,7,59,61)})
(def d13-s03 {:buses '(67,x,7,59,61)})
(def d13-s04 {:buses '(67,7,x,59,61)})
(def d13-s05 {:buses '(1789,37,47,1889)})

(deftest earliest-bus-and-wait-time
  (testing "Can find the earliest bus available after time t"
    (is (= [59 5] (t/earliest-bus-and-wait-time d13-s00)))))

(deftest earliest-time-for-consecutive-buses
  (testing "Can find the earliest time for consecutive buses to depart"
    (is (= 1068781    (t/earliest-consecutive-buses d13-s00)))
    (is (= 3417       (t/earliest-consecutive-buses d13-s01)))
    (is (= 754018     (t/earliest-consecutive-buses d13-s02)))
    (is (= 779210     (t/earliest-consecutive-buses d13-s03)))
    (is (= 1261476    (t/earliest-consecutive-buses d13-s04)))
    (is (= 1202161486 (t/earliest-consecutive-buses d13-s05)))))

(def day13-input (u/parse-puzzle-input t/parse 2020 13))

(deftest part1-test
  (testing "Reproduces the answer for day13, part1"
    (is (= 104 (t/part1 day13-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day13, part2"
    (is (= 842186186521918N (t/part2 day13-input)))))