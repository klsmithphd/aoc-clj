(ns aoc-clj.2022.day13-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2022.day13 :as t]))

(def d13-s00
  (t/parse
   ["[1,1,3,1,1]"
    "[1,1,5,1,1]"
    ""
    "[[1],[2,3,4]]"
    "[[1],4]"
    ""
    "[9]"
    "[[8,7,6]]"
    ""
    "[[4,4],4,4]"
    "[[4,4],4,4,4]"
    ""
    "[7,7,7,7]"
    "[7,7,7]"
    ""
    "[]"
    "[3]"
    ""
    "[[[]]]"
    "[[]]"
    ""
    "[1,[2,[3,[4,[5,6,7]]]],8,9]"
    "[1,[2,[3,[4,[5,6,0]]]],8,9]"]))

(deftest parse-test
  (testing "Correctly parses the sample input"
    (is (= d13-s00
           [[[1 1 3 1 1] [1 1 5 1 1]]
            [[[1] [2 3 4]] [[1] 4]]
            [[9] [[8 7 6]]]
            [[[4 4] 4 4] [[4 4] 4 4 4]]
            [[7 7 7 7] [7 7 7]]
            [[] [3]]
            [[[[]]] [[]]]
            [[1 [2 [3 [4 [5 6 7]]]] 8 9]
             [1 [2 [3 [4 [5 6 0]]]] 8 9]]]))))

(def d13-s00-sorted
  [[]
   [[]]
   [[[]]]
   [1,1,3,1,1]
   [1,1,5,1,1]
   [[1],[2,3,4]]
   [1,[2,[3,[4,[5,6,0]]]],8,9]
   [1,[2,[3,[4,[5,6,7]]]],8,9]
   [[1],4]
   [[2]]
   [3]
   [[4,4],4,4]
   [[4,4],4,4,4]
   [[6]]
   [7,7,7]
   [7,7,7,7]
   [[8,7,6]]
   [9]])

(deftest in-order?-test
  (testing "Follows the correct in-order logic for the sample data"
    ;; If the right list runs out of items first, 
    ;; the inputs are not in the right order.
    (is (true? (t/in-order? [1 2] [2])))
    (is (true?  (apply t/in-order? (nth d13-s00 0))))
    (is (true?  (apply t/in-order? (nth d13-s00 1))))
    (is (false? (apply t/in-order? (nth d13-s00 2))))
    (is (true?  (apply t/in-order? (nth d13-s00 3))))
    (is (false? (apply t/in-order? (nth d13-s00 4))))
    (is (true?  (apply t/in-order? (nth d13-s00 5))))
    (is (false? (apply t/in-order? (nth d13-s00 6))))
    (is (false? (apply t/in-order? (nth d13-s00 7))))))

(deftest right-order-packet-id-sum-test
  (testing "The sum of the packet pair ids for in-order packets"
    (is (= 13 (t/right-order-packet-id-sum d13-s00)))))

(deftest sorted-test
  (testing "Returns the packets in-order, with the divider packets"
    (is (= d13-s00-sorted (t/sorted d13-s00)))))

(deftest decoder-key-test
  (testing "Computes the decoder key (product of indices of two divider 
            packets)"
    (is (= 140 (t/decoder-key d13-s00)))))

(def day13-input (u/parse-puzzle-input t/parse 2022 13))

(deftest part1-test
  (testing "Reproduces the answer for day13, part1"
    (is (= 5503 (t/part1 day13-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day13, part2"
    (is (= 20952 (t/part2 day13-input)))))