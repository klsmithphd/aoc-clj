(ns aoc-clj.2015.day12-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day12 :as t]))

(deftest drop-red-test
  (testing "Correctly drops sub-maps with any value that is 'red'"
    (is (= [1 2 3]     (t/drop-red [1,2,3])))
    (is (= [1 {} 3]    (t/drop-red [1,{"c" "red" "b" 2},3])))
    (is (= {}          (t/drop-red {"d" "red","e" [1,2,3,4],"f" 5})))
    (is (= [1 "red" 5] (t/drop-red [1, "red", 5])))))

(def day12-input (u/parse-puzzle-input t/parse 2015 12))

(deftest part1-test
  (testing "Reproduces the answer for day12, part1"
    (is (= 111754 (t/part1 day12-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day12, part2"
    (is (= 65402 (t/part2 day12-input)))))