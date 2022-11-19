(ns aoc-clj.2015.day09-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2015.day09 :as t]))

(def day09-sample (t/parse
                   ["London to Dublin = 464"
                    "London to Belfast = 518"
                    "Dublin to Belfast = 141"]))

(deftest route-distances
  (testing "Returns the distance along an identified route"
    (is (= 982 (t/route-distance day09-sample ["Dublin" "London" "Belfast"])))
    (is (= 605 (t/route-distance day09-sample ["London" "Dublin" "Belfast"])))
    (is (= 659 (t/route-distance day09-sample ["London" "Belfast" "Dublin"])))
    (is (= 659 (t/route-distance day09-sample ["Dublin" "Belfast" "London"])))
    (is (= 605 (t/route-distance day09-sample ["Belfast" "Dublin" "London"])))
    (is (= 982 (t/route-distance day09-sample ["Belfast" "London" "Dublin"])))))

(deftest shortest-route-distance
  (testing "Returns the distance of the shortest route"
    (is (= 605 (t/shortest-route-distance day09-sample)))))

(deftest longest-route-distance
  (testing "Returns the distance of the longest route"
    (is (= 982 (t/longest-route-distance day09-sample)))))

(deftest day09-part1-soln
  (testing "Reproduces the answer for day09, part1"
    (is (= 207 (t/day09-part1-soln)))))

(deftest day09-part2-soln
  (testing "Reproduces the answer for day09, part2"
    (is (= 804 (t/day09-part2-soln)))))