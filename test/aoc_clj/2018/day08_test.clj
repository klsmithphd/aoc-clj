(ns aoc-clj.2018.day08-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2018.day08 :as d08]))

(def d08-s00-raw
  ["2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"])

(def d08-s00
  [2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d08-s00 (d08/parse d08-s00-raw)))))


(deftest node-size-test
  (testing "Determines the number of elements representing a node"
    (is (= 2 (d08/node-size {:children [] :metadata []})))
    (is (= 4 (d08/node-size {:children [] :metadata [1 2]})))

    (is (= 9 (d08/node-size {:children
                             [{:children {} :metadata []}
                              {:children {} :metadata [1]}]
                             :metadata [3 4]})))))

(deftest add-node-test
  (testing "Constructs the tree node by node"
    (is (= {:children [] :metadata []} (d08/parse-node [0 0])))
    (is (= {:children [] :metadata [2]} (d08/parse-node [0 1 2])))
    (is (= {:children
            [{:children [] :metadata []}]
            :metadata []} (d08/parse-node [1 0 0 0])))
    (is (= {:children
            [{:children
              [{:children [] :metadata []}] :metadata []}]
            :metadata []} (d08/parse-node [1 0 1 0 0 0])))

    (is (= {:children
            [{:children [] :metadata [10 11 12]}
             {:children
              [{:children [] :metadata [99]}]
              :metadata [2]}]
            :metadata [1 1 2]}
           (d08/parse-node d08-s00)))))

(deftest metadata-sum-test
  (testing "Computes the sum of all the metadata in the tree"
    (is (= 0 (d08/metadata-sum {:children [] :metadata []})))
    (is (= 10 (d08/metadata-sum {:children [] :metadata [4 6]})))
    (is (= 20 (d08/metadata-sum {:children
                                 [{:children [] :metadata [10]}]
                                 :metadata [4 6]})))
    (is (= 138 (d08/metadata-sum (d08/parse-node d08-s00))))))

(deftest node-value-test
  (testing "Computes the node value"
    (is (= 33 (d08/node-value (d08/parse-node [0 3 10 11 12]))))
    (is (= 99 (d08/node-value (d08/parse-node [0 1 99]))))
    (is (= 0  (d08/node-value (d08/parse-node [1 1 0 1 99]))))
    (is (= 66 (d08/node-value (d08/parse-node d08-s00))))))

(def day08-input (u/parse-puzzle-input d08/parse 2018 8))

(deftest part1-test
  (testing "Reproduces the answer for day08, part1"
    (is (= 41028 (d08/part1 day08-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day08, part2"
    (is (= 20849 (d08/part2 day08-input)))))