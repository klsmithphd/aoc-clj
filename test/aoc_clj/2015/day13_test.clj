(ns aoc-clj.2015.day13-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day13 :as t]))

(def d13-s00
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

(deftest max-happiness-test
  (testing "Finds the maximum happiness value for the guest list"
    (is (= 330 (t/max-happiness (t/parse d13-s00))))))

(def day13-input (u/parse-puzzle-input t/parse 2015 13))

(deftest day13-part1-soln
  (testing "Reproduces the answer for day13, part1"
    (is (= 709 (t/day13-part1-soln day13-input)))))

(deftest day13-part2-soln
  (testing "Reproduces the answer for day13, part2"
    (is (= 668 (t/day13-part2-soln day13-input)))))