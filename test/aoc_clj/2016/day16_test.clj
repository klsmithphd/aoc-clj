(ns aoc-clj.2016.day16-test
  (:require [clojure.test :refer [deftest testing is]]
            [clojure.string :as str]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day16 :as d16]))

(deftest fill-test
  (testing "Implements the fill logic correctly"
    (is (= "100" (str/join (d16/fill "1"))))
    (is (= "001" (str/join (d16/fill "0"))))
    (is (= "11111000000" (str/join (d16/fill "11111"))))
    (is (= "1111000010100101011110000" 
           (str/join (d16/fill "111100001010"))))))

(deftest fill-to-length-test
  (testing "Iteratively applies fill until necessary length achieved"
    (is (= "10000011110010000111"
           (str/join (d16/fill-to-length 20 "10000"))))))

(deftest checksum-test
  (testing "Computes the checksum for a bit string"
    (is (= "100"   (d16/checksum "110010110100")))
    (is (= "01100" (d16/checksum "10000011110010000111")))))

(def day16-input (u/parse-puzzle-input d16/parse 2016 16))

(deftest part1-test
  (testing "Reproduces the answer for day16, part1"
    (is (= "01110011101111011" (d16/part1 day16-input)))))

;; FIXME Too Slow - https://github.com/klsmithphd/aoc-clj/issues/79
(deftest ^:slow part2-test
  (testing "Reproduces the answer for day16, part2"
    (is (= "11001111011000111" (d16/part2 day16-input)))))