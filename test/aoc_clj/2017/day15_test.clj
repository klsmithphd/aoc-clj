(ns aoc-clj.2017.day15-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2017.day15 :as d15]))


(def d15-s00-raw
  ["Generator A uses 65"
   "Generator B uses 8921"])

(def d15-s00
  [65 8921])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d15-s00 (d15/parse d15-s00-raw)))))