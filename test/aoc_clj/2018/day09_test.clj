(ns aoc-clj.2018.day09-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2018.day09 :as d09]))

(def d09-s00-raw ["10 players; last marble is worth 1618 points"])

(def d09-s00 [10 1618])
(def d09-s01 [13 7999])
(def d09-s02 [17 1104])
(def d09-s03 [21 6111])
(def d09-s04 [30 5807])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d09-s00 (d09/parse d09-s00-raw)))))