(ns aoc-clj.2022.day06-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2022.day06 :as t]))

(def d06-s00 "mjqjpqmgbljsphdztnvjfqwrcgsmlb")
(def d06-s01 "bvwbjplbgvbhsrlpgdmjqwftvncz")
(def d06-s02 "nppdvjthqldpwncqszvftbrmjlhg")
(def d06-s03 "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg")
(def d06-s04 "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw")

(def d06-s05 "mjqjpqmgbljsphdztnvjfqwrcgsmlb")
(def d06-s06 "bvwbjplbgvbhsrlpgdmjqwftvncz")
(def d06-s07 "nppdvjthqldpwncqszvftbrmjlhg")
(def d06-s08 "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg")
(def d06-s09 "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw")

(deftest chars-to-distinct-run-test
  (testing "Finds number of chars to examine to find first
            start-of-packet or start-of-message sequence"
    ;; start of packet: sequence of 4 unique chars
    (is (= 7  (t/chars-to-distinct-run 4 d06-s00)))
    (is (= 5  (t/chars-to-distinct-run 4 d06-s01)))
    (is (= 6  (t/chars-to-distinct-run 4 d06-s02)))
    (is (= 10 (t/chars-to-distinct-run 4 d06-s03)))
    (is (= 11 (t/chars-to-distinct-run 4 d06-s04)))
    ;; start of message: sequence of 14 unique chars
    (is (= 19 (t/chars-to-distinct-run 14 d06-s05)))
    (is (= 23 (t/chars-to-distinct-run 14 d06-s06)))
    (is (= 23 (t/chars-to-distinct-run 14 d06-s07)))
    (is (= 29 (t/chars-to-distinct-run 14 d06-s08)))
    (is (= 26 (t/chars-to-distinct-run 14 d06-s09)))))

(def day06-input (u/parse-puzzle-input t/parse 2022 6))

(deftest part1-test
  (testing "Reproduces the answer for day06, part1"
    (is (= 1855 (t/part1 day06-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day06, part2"
    (is (= 3256 (t/part2 day06-input)))))