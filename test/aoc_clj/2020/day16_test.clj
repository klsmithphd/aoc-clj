(ns aoc-clj.2020.day16-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2020.day16 :as t]))

;; (def day16-sample
;;   (t/parse
;;    "class: 1-3 or 5-7
;; row: 6-11 or 33-44
;; seat: 13-40 or 45-50

;; your ticket:
;; 7,1,14

;; nearby tickets:
;; 7,3,47
;; 40,4,50
;; 55,2,20
;; 38,6,12"))

;; (def day16-sample2
;;   (t/parse
;;    "class: 0-1 or 4-19
;; row: 0-5 or 8-19
;; seat: 0-13 or 16-19

;; your ticket:
;; 11,12,13

;; nearby tickets:
;; 3,9,18
;; 15,1,5
;; 5,14,9"))

(def day16-input (u/parse-puzzle-input t/parse 2020 16))

(deftest part1-test
  (testing "Reproduces the answer for day16, part1"
    (is (= 25961 (t/part1 day16-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day16, part2"
    (is (= 603409823791 (t/part2 day16-input)))))