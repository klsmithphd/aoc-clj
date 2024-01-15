(ns aoc-clj.2015.day13-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day13 :as d13]))

(def d13-s00-raw
  ["Alice would gain 54 happiness units by sitting next to Bob."
   "Alice would lose 79 happiness units by sitting next to Carol."
   "Alice would lose 2 happiness units by sitting next to David."
   "Bob would gain 83 happiness units by sitting next to Alice."
   "Bob would lose 7 happiness units by sitting next to Carol."
   "Bob would lose 63 happiness units by sitting next to David."
   "Carol would lose 62 happiness units by sitting next to Alice."
   "Carol would gain 60 happiness units by sitting next to Bob."
   "Carol would gain 55 happiness units by sitting next to David."
   "David would gain 46 happiness units by sitting next to Alice."
   "David would lose 7 happiness units by sitting next to Bob."
   "David would gain 41 happiness units by sitting next to Carol."])

(def d13-s00 {"Alice" {"Bob" 54    "Carol" -79 "David" -2}
              "Bob"   {"Alice" 83  "Carol" -7  "David" -63}
              "Carol" {"Alice" -62 "Bob" 60    "David" 55}
              "David" {"Alice" 46  "Bob" -7    "Carol" 41}})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d13-s00 (d13/parse d13-s00-raw)))))

(deftest arrangements-test
  (testing "Computes all the possible (round table) arrangements"
    (is (= [["Alice" "Bob" "Carol" "David"]
            ["Alice" "Bob" "David" "Carol"]
            ["Alice" "Carol" "Bob" "David"]
            ["Alice" "Carol" "David" "Bob"]
            ["Alice" "David" "Bob" "Carol"]
            ["Alice" "David" "Carol" "Bob"]]
           (d13/arrangements d13-s00)))))

(deftest max-happiness-test
  (testing "Finds the maximum happiness value for the guest list"
    (is (= 330 (d13/max-happiness d13-s00)))))

(def day13-input (u/parse-puzzle-input d13/parse 2015 13))

(deftest part1-test
  (testing "Reproduces the answer for day13, part1"
    (is (= 709 (d13/part1 day13-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day13, part2"
    (is (= 668 (d13/part2 day13-input)))))