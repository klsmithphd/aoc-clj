(ns aoc-clj.2016.day20-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day20 :as d20]))

(def d20-s00-raw
  ["5-8"
   "0-2"
   "4-7"])

(def d20-s00
  [[5 8]
   [0 2]
   [4 7]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d20-s00 (d20/parse d20-s00-raw)))))

