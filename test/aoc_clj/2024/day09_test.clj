(ns aoc-clj.2024.day09-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2024.day09 :as d09]))

(def d09-s00-raw
  ["2333133121414131402"])

(def d09-s00
  [2 3 3 3 1 3 3 1 2 1 4 1 4 1 3 1 4 0 2])

(def d09-s01
  [1 2 3 4 5])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d09-s00 (d09/parse d09-s00-raw)))))

(deftest blocks-test
  (testing "Determines the data blocks from the input representation"
    (is (= {:blocks {0 1
                     1 3
                     2 5}
            :gaps   [2 4]}
           (d09/blocks d09-s01)))

    (is (= {:blocks {0 2
                     1 3
                     2 1
                     3 3
                     4 2
                     5 4
                     6 4
                     7 3
                     8 4
                     9 2}
            :gaps    [3 3 3 1 1 1 1 1 0]}
           (d09/blocks d09-s00)))))

(deftest end-blocks-test
  (testing "Returns a lazy seq of the block values read in reverse"
    (is (= [2 2 2 2 2 1 1 1 0]
           (d09/end-blocks (:blocks (d09/blocks d09-s01)))))

    (is (= [9 9 8 8 8 8 7 7 7 6]
           (take 10 (d09/end-blocks (:blocks (d09/blocks d09-s00))))))))

(deftest fills-test
  (testing "Constructs fill chunks from the end of the blocks"
    (is (= [[2 2] [2 2 2 1]]
           (d09/fills [2 4] [2 2 2 2 2 1 1 1 0])))

    (is (= [[1 2 3] [4 5 6] [] [7]]
           (d09/fills [3 3 0 1] [1 2 3 4 5 6 7 8 9 10 11])))))

(deftest compacted-test
  (testing "Compacts the blocks"
    (is (= [0 2 2 1 1 1 2 2 2]
           (d09/compacted (d09/blocks d09-s01))))

    (is (= [0 0 9 9 8 1 1 1 8 8 8 2 7 7 7 3 3 3 6 4 4 6 5 5 5 5 6 6]
           (d09/compacted (d09/blocks d09-s00))))))

(deftest checksum-test
  (testing "Computes the checksum for the compacted blocks"
    (is (= 1928 (d09/checksum (d09/compacted (d09/blocks d09-s00)))))))

(def day09-input (u/parse-puzzle-input d09/parse 2024 9))

(deftest part1-test
  (testing "Reproduces the answer for day09, part1"
    (is (= 6259790630969 (d09/part1 day09-input)))))