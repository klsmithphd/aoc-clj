(ns aoc-clj.2024.day24-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2024.day24 :as d24]))

(def d24-s00-raw
  ["x00: 1"
   "x01: 1"
   "x02: 1"
   "y00: 0"
   "y01: 1"
   "y02: 0"
   ""
   "x00 AND y00 -> z00"
   "x01 XOR y01 -> z01"
   "x02 OR y02 -> z02"])

(def d24-s00
  {:init [[:x00 1]
          [:x01 1]
          [:x02 1]
          [:y00 0]
          [:y01 1]
          [:y02 0]]
   :logic {[:x00 :y00] [:and :z00]
           [:x01 :y01] [:xor :z01]
           [:x02 :y02] [:or  :z02]}})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d24-s00 (d24/parse d24-s00-raw)))))