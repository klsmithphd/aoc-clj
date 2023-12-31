(ns aoc-clj.2016.day05-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day05 :as t]))

(def d05-s00 "abc")

(def day05-sample-valid-offsets
  "Found by calling (valid-hash-offsets day05-sample) and waiting a long time!"
  [3231929 5017308 5278568 5357525 5708769
   6082117 8036669 8605828 8609554 8760605
   9495334 10767910 11039607 12763908 13666005
   13753421 14810753 15274776 15819744 18455182])

(deftest password
  (testing "Identifies the password using part1 logic"
    (is (= "18f47a30" (t/password d05-s00 day05-sample-valid-offsets)))))

(deftest password-part2
  (testing "Identifies the password using part2 logic"
    (is (= "05ace8e3" (t/password-part2 d05-s00 day05-sample-valid-offsets)))))

(def day05-input (u/parse-puzzle-input t/parse 2016 5))

(deftest day05-part1-soln
  (testing "Reproduces the answer for day05, part1"
    (is (= "f97c354d" (t/day05-part1-soln day05-input)))))

(deftest day05-part2-soln
  (testing "Reproduces the answer for day05, part2"
    (is (= "863dde27" (t/day05-part2-soln day05-input)))))