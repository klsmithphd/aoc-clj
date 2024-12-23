(ns aoc-clj.2024.day19-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2024.day19 :as d19]))

(def d19-s00-raw
  ["r, wr, b, g, bwu, rb, gb, br"
   ""
   "brwrr"
   "bggr"
   "gbbr"
   "rrbgbr"
   "ubwu"
   "bwurrg"
   "brgr"
   "bbrgwb"])

(def d19-s00
  {:towels   {"b" ["bwu" "br" "b"]
              "g" ["gb" "g"]
              "r" ["rb" "r"]
              "w" ["wr"]}
   :patterns ["brwrr"
              "bggr"
              "gbbr"
              "rrbgbr"
              "ubwu"
              "bwurrg"
              "brgr"
              "bbrgwb"]})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d19-s00 (d19/parse d19-s00-raw)))))

(deftest possible?-test
  (testing "Determines whether the pattern can be created from the available towels"
    (is (= true  (d19/possible? (:towels d19-s00) (nth (:patterns d19-s00) 0))))
    (is (= true  (d19/possible? (:towels d19-s00) (nth (:patterns d19-s00) 1))))
    (is (= true  (d19/possible? (:towels d19-s00) (nth (:patterns d19-s00) 2))))
    (is (= true  (d19/possible? (:towels d19-s00) (nth (:patterns d19-s00) 3))))
    (is (= false (d19/possible? (:towels d19-s00) (nth (:patterns d19-s00) 4))))
    (is (= true  (d19/possible? (:towels d19-s00) (nth (:patterns d19-s00) 5))))
    (is (= true  (d19/possible? (:towels d19-s00) (nth (:patterns d19-s00) 6))))
    (is (= false (d19/possible? (:towels d19-s00) (nth (:patterns d19-s00) 7))))))

(deftest possible-count-test
  (testing "Counts the number of possible towel patterns that can be made"
    (is (= 6 (d19/possible-count d19-s00)))))

(def day19-input (u/parse-puzzle-input d19/parse 2024 19))
;; (:towels day19-input)
;; (count (:patterns day19-input))

;; (get-in day19-input [:towels "u"])
;; (first (:patterns day19-input))


;; (d19/possible? (:towels day19-input) (nth (:patterns day19-input) 0))


(deftest part1-test
  (testing "Reproduces the answer for day19, part1"
    (is (= 342 (d19/part1 day19-input)))))