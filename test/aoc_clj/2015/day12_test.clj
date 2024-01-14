(ns aoc-clj.2015.day12-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day12 :as d12]))

(def d12-s00 "[1,2,3]")
(def d12-s01 "{\"a\":2,\"b\":4}")
(def d12-s02 "[[[3]]]")
(def d12-s03 "{\"a\":{\"b\":4},\"c\":-1}")
(def d12-s04 "{\"a\":[-1,1]}")
(def d12-s05 "[-1,{\"a\":1}]")
(def d12-s06 "[]")
(def d12-s07 "{}")

(deftest all-numbers-total-test
  (testing "Computes the sum of the numbers in each JSON doc"
    (is (= 6 (d12/all-numbers-total d12-s00)))
    (is (= 6 (d12/all-numbers-total d12-s01)))
    (is (= 3 (d12/all-numbers-total d12-s02)))
    (is (= 3 (d12/all-numbers-total d12-s03)))
    (is (= 0 (d12/all-numbers-total d12-s04)))
    (is (= 0 (d12/all-numbers-total d12-s05)))
    (is (= 0 (d12/all-numbers-total d12-s06)))
    (is (= 0 (d12/all-numbers-total d12-s07)))))

(deftest drop-red-test
  (testing "Correctly drops sub-maps with any value that is 'red'"
    (is (= [1 2 3]     (d12/drop-red [1,2,3])))
    (is (= [1 {} 3]    (d12/drop-red [1,{"c" "red" "b" 2},3])))
    (is (= {}          (d12/drop-red {"d" "red","e" [1,2,3,4],"f" 5})))
    (is (= [1 "red" 5] (d12/drop-red [1, "red", 5])))))

(def day12-input (u/parse-puzzle-input d12/parse 2015 12))

(deftest part1-test
  (testing "Reproduces the answer for day12, part1"
    (is (= 111754 (d12/part1 day12-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day12, part2"
    (is (= 65402 (d12/part2 day12-input)))))