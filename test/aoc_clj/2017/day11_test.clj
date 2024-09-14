(ns aoc-clj.2017.day11-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2017.day11 :as d11]))

(def d11-s00-raw
  ["ne,ne,ne"])

(def d11-s00 ["ne" "ne" "ne"])
(def d11-s01 ["ne" "ne" "sw" "sw"])
(def d11-s02 ["ne" "ne" "s" "s"])
(def d11-s03 ["se" "sw" "se" "sw" "sw"])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d11-s00 (d11/parse d11-s00-raw)))))
