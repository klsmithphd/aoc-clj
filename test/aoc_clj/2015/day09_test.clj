(ns aoc-clj.2015.day09-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day09 :as d09]))

(def d09-s00-raw
  ["London to Dublin = 464"
   "London to Belfast = 518"
   "Dublin to Belfast = 141"])

(def d09-s00
  {"London"  {"Dublin" 464 "Belfast" 518}
   "Dublin"  {"London" 464 "Belfast" 141}
   "Belfast" {"London" 518 "Dublin" 141}})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d09-s00 (d09/parse d09-s00-raw)))))

(deftest route-distances
  (testing "Returns the distance along an identified route"
    (is (= 982 (d09/route-distance d09-s00 ["Dublin" "London" "Belfast"])))
    (is (= 605 (d09/route-distance d09-s00 ["London" "Dublin" "Belfast"])))
    (is (= 659 (d09/route-distance d09-s00 ["London" "Belfast" "Dublin"])))
    (is (= 659 (d09/route-distance d09-s00 ["Dublin" "Belfast" "London"])))
    (is (= 605 (d09/route-distance d09-s00 ["Belfast" "Dublin" "London"])))
    (is (= 982 (d09/route-distance d09-s00 ["Belfast" "London" "Dublin"])))))

(deftest shortest-route-distance
  (testing "Returns the distance of the shortest route"
    (is (= 605 (d09/shortest-route-distance d09-s00)))))

(deftest longest-route-distance
  (testing "Returns the distance of the longest route"
    (is (= 982 (d09/longest-route-distance d09-s00)))))

(def day09-input (u/parse-puzzle-input d09/parse 2015 9))

(deftest part1-test
  (testing "Reproduces the answer for day09, part1"
    (is (= 207 (d09/part1 day09-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day09, part2"
    (is (= 804 (d09/part2 day09-input)))))