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

(def d22-s01
  [1 2 3 2024])

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

(deftest secret-at-n-sum-test
  (testing "Computes the sum of all the Nth new secret numbers"
    (is (= 37327623 (d22/secrets-at-n-sum 2000 d22-s00)))))

(deftest ones-digit-test
  (testing "Returns the ones digit of a number"
    (is (= [3 0 6 5 4 4 6 4 4 2]
           (->> (d22/secret-seq 123)
                (take 10)
                (map d22/ones-digit))))))

(deftest prices-changes-test
  (testing "Returns the sequence of price changes (consecutive deltas)"
    (is (= [-3 6 -1 -1 0 2 -2 0 -2]
           (->> (d22/secret-seq 123)
                (take 10)
                (map d22/ones-digit)
                d22/price-changes)))))

(deftest changeseq-to-price-map-test
  (testing "Returns a collection of the 4-sequence-change values preceding
            a price, ordered descending by the price"
    (is (= {[-1 -1 0 2] 6
            [-3 6 -1 -1] 4
            [6 -1 -1 0] 4
            [-1 0 2 -2] 4
            [0 2 -2 0] 4
            [-2 0 -2 2] 4
            [2 -2 0 -2] 2}
           (d22/changeseq-to-price-map 10 123)))))

(deftest most-bananas-test
  (testing "Returns the changeseq and the price that maximizes the price"
    (is (= [[-1 -1 0 2] 6]
           (d22/most-bananas (d22/changeseq-to-price-map 10 123))))

    (is (= [[-2 1 -1 3] 23]
           (d22/most-bananas (d22/all-prices 2000 d22-s01))))))
(deftest all-prices-test)

(def day22-input (u/parse-puzzle-input d22/parse 2024 22))

(deftest part1-test
  (testing "Reproduces the answer for day22, part1"
    (is (= 18525593556 (d22/part1 day22-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day22, part2"
    (is (= 2089 (d22/part2 day22-input)))))