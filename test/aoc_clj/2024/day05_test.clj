(ns aoc-clj.2024.day05-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2024.day05 :as d05]))

(def d05-s00-raw
  ["47|53"
   "97|13"
   "97|61"
   "97|47"
   "75|29"
   "61|13"
   "75|53"
   "29|13"
   "97|29"
   "53|29"
   "61|53"
   "97|53"
   "61|29"
   "47|13"
   "75|47"
   "97|75"
   "47|61"
   "75|61"
   "47|29"
   "75|13"
   "53|13"
   ""
   "75,47,61,53,29"
   "97,61,53,29,13"
   "75,29,13"
   "75,97,47,61,53"
   "61,13,29"
   "97,13,75,29,47"])

(def d05-s00
  {:ordering {29 #{13}
              47 #{13 29 53 61}
              53 #{13 29}
              61 #{13 29 53}
              75 #{13 29 47 53 61}
              97 #{13 29 47 53 61 75}}
   :updates [[75 47 61 53 29]
             [97 61 53 29 13]
             [75 29 13]
             [75 97 47 61 53]
             [61 13 29]
             [97 13 75 29 47]]})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d05-s00 (d05/parse d05-s00-raw)))))

(deftest in-order?-test
  (testing "Determines whether an update is in order"
    (is (= true (d05/in-order? (:ordering d05-s00)
                               (nth (:updates d05-s00) 0))))
    (is (= true (d05/in-order? (:ordering d05-s00)
                               (nth (:updates d05-s00) 1))))
    (is (= true (d05/in-order? (:ordering d05-s00)
                               (nth (:updates d05-s00) 2))))
    (is (= false (d05/in-order? (:ordering d05-s00)
                                (nth (:updates d05-s00) 3))))
    (is (= false (d05/in-order? (:ordering d05-s00)
                                (nth (:updates d05-s00) 4))))
    (is (= false (d05/in-order? (:ordering d05-s00)
                                (nth (:updates d05-s00) 5))))))

(deftest ordered-update-middle-pages-test
  (testing "Returns the middle pages of all the updates that are in page order"
    (is (= [61 53 29] (d05/ordered-update-middle-pages d05-s00)))))

(def day05-input (u/parse-puzzle-input d05/parse 2024 5))

(deftest part1-test
  (testing "Reproduces the answer for day05, part1"
    (is (= 5275 (d05/part1 day05-input)))))