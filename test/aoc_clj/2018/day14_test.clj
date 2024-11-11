(ns aoc-clj.2018.day14-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2018.day14 :as d14]))

(def d14-s00-raw
  ["37"])

(def d14-s00
  [3 7])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d14-s00 (d14/parse d14-s00-raw)))))