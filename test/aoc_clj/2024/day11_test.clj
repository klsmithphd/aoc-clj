(ns aoc-clj.2024.day11-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2024.day11 :as d11]))

(def d11-s00-raw ["0 1 10 99 999"])

(def d11-s00 ["0" "1" "10" "99" "999"])
(def d11-s01 ["125" "17"])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d11-s00 (d11/parse d11-s00-raw)))))