(ns aoc-clj.2017.day22-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2017.day22 :as d22]))

(def d22-s00-raw
  ["..#"
   "#.."
   "..."])

(def d22-s00
  #{[1 1] [-1 0]})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d22-s00 (d22/parse d22-s00-raw)))))