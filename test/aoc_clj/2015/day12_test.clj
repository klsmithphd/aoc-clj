(ns aoc-clj.2015.day12-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2015.day12 :as t]))

(deftest drop-red-test
  (testing "Correctly drops sub-maps with any value that is 'red'"
    (is (= [1 2 3]     (t/drop-red [1,2,3])))
    (is (= [1 {} 3]    (t/drop-red [1,{"c" "red" "b" 2},3])))
    (is (= {}          (t/drop-red {"d" "red","e" [1,2,3,4],"f" 5})))
    (is (= [1 "red" 5] (t/drop-red [1, "red", 5])))))

(deftest day12-part1-soln
  (testing "Reproduces the answer for day12, part1"
    (is (= 111754 (t/day12-part1-soln)))))

(deftest day12-part2-soln
  (testing "Reproduces the answer for day12, part2"
    (is (= 65402 (t/day12-part2-soln)))))