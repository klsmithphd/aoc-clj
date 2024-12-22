(ns aoc-clj.2024.day22-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2024.day22 :as d22]))

(def d22-s00-raw
  ["1"
   "10"
   "100"
   "2024"])

(def d22-s00
  [1 10 100 2024])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d22-s00 (d22/parse d22-s00-raw)))))

(deftest secret-seq-test
  (testing "Returns the next values in the secret sequence"
    (is (= [123
            15887950
            16495136
            527345
            704524
            1553684
            12683156
            11100544
            12249484
            7753432
            5908254]
           (take 11 (d22/secret-seq 123))))))

(deftest secret-at-n-test
  (testing "Returns the Nth new secret number"
    (is (= 8685429 (d22/secret-at-n 2000 1)))))

(deftest secret-at-n-sum
  (testing "Computes the sum of all the Nth new secret numbers"
    (is (= 37327623 (d22/secrets-at-n-sum 2000 d22-s00)))))

(def day22-input (u/parse-puzzle-input d22/parse 2024 22))

(deftest part1-test
  (testing "Reproduces the answer for day22, part1"
    (is (= 18525593556 (d22/part1 day22-input)))))