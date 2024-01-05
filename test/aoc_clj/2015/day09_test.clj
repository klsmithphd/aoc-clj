(ns aoc-clj.2015.day09-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day09 :as t]))

(def d09-s00 (t/parse
              ["London to Dublin = 464"
               "London to Belfast = 518"
               "Dublin to Belfast = 141"]))

(deftest route-distances
  (testing "Returns the distance along an identified route"
    (is (= 982 (t/route-distance d09-s00 ["Dublin" "London" "Belfast"])))
    (is (= 605 (t/route-distance d09-s00 ["London" "Dublin" "Belfast"])))
    (is (= 659 (t/route-distance d09-s00 ["London" "Belfast" "Dublin"])))
    (is (= 659 (t/route-distance d09-s00 ["Dublin" "Belfast" "London"])))
    (is (= 605 (t/route-distance d09-s00 ["Belfast" "Dublin" "London"])))
    (is (= 982 (t/route-distance d09-s00 ["Belfast" "London" "Dublin"])))))

(deftest shortest-route-distance
  (testing "Returns the distance of the shortest route"
    (is (= 605 (t/shortest-route-distance d09-s00)))))

(deftest longest-route-distance
  (testing "Returns the distance of the longest route"
    (is (= 982 (t/longest-route-distance d09-s00)))))

(def day09-input (u/parse-puzzle-input t/parse 2015 9))

(deftest part1-test
  (testing "Reproduces the answer for day09, part1"
    (is (= 207 (t/part1 day09-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day09, part2"
    (is (= 804 (t/part2 day09-input)))))