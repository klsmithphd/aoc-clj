(ns aoc-clj.2024.day09-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2024.day09 :as d09]))

(def d09-s00-raw
  ["2333133121414131402"])

(def d09-s00
  [{:pos 0  :data [0 0]     :space 0}
   {:pos 1  :data []        :space 3}
   {:pos 2  :data [1 1 1]   :space 0}
   {:pos 3  :data []        :space 3}
   {:pos 4  :data [2]       :space 0}
   {:pos 5  :data []        :space 3}
   {:pos 6  :data [3 3 3]   :space 0}
   {:pos 7  :data []        :space 1}
   {:pos 8  :data [4 4]     :space 0}
   {:pos 9  :data []        :space 1}
   {:pos 10 :data [5 5 5 5] :space 0}
   {:pos 11 :data []        :space 1}
   {:pos 12 :data [6 6 6 6] :space 0}
   {:pos 13 :data []        :space 1}
   {:pos 14 :data [7 7 7]   :space 0}
   {:pos 15 :data []        :space 1}
   {:pos 16 :data [8 8 8 8] :space 0}
   {:pos 17 :data []        :space 0}
   {:pos 18 :data [9 9]     :space 0}])

(def d09-s01
  (d09/parse ["12345"]))

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d09-s00 (d09/parse d09-s00-raw)))))

(deftest end-data-test
  (testing "Returns the data values read in reverse from the end of the disk"
    (is (= [2 2 2 2 2 1 1 1 0]
           (d09/end-data d09-s01)))

    (is (= [9 9 8 8 8 8 7 7 7 6]
           (take 10 (d09/end-data d09-s00))))))

(deftest partitions-by-sizes-test
  (testing "Partitions the data into groups by provided partition sizes"
    (is (= [[2 2] [2 2 2 1]]
           (d09/parition-by-sizes [2 4] [2 2 2 2 2 1 1 1 0])))

    (is (= [[1 2 3] [4 5 6] [] [7]]
           (d09/parition-by-sizes [3 3 0 1] [1 2 3 4 5 6 7 8 9 10 11])))))

(deftest single-datum-compacted-test
  (testing "Compacts the blocks"
    (is (= [0 2 2 1 1 1 2 2 2]
           (d09/single-datum-compacted  d09-s01)))

    (is (= [0 0 9 9 8 1 1 1 8 8 8 2 7 7 7 3 3 3 6 4 4 6 5 5 5 5 6 6]
           (d09/single-datum-compacted d09-s00)))))

(deftest checksum-test
  (testing "Computes the checksum for the compacted blocks"
    (is (= 1928 (d09/checksum (d09/single-datum-compacted d09-s00))))))

(def day09-input (u/parse-puzzle-input d09/parse 2024 9))

(deftest part1-test
  (testing "Reproduces the answer for day09, part1"
    (is (= 6259790630969 (d09/part1 day09-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day09, part2"
    ;; Too high
    (is (= 6289564621904 (d09/part2 day09-input)))))
