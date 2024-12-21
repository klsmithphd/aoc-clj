(ns aoc-clj.2024.day19-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2024.day19 :as d19]))

(def d19-s00-raw
  ["r, wr, b, g, bwu, rb, gb, br"
   ""
   "brwrr"
   "bggr"
   "gbbr"
   "rrbgbr"
   "ubwu"
   "bwurrg"
   "brgr"
   "bbrgwb"])

(def d19-s00
  {:towels ["r" "wr" "b" "g" "bwu" "rb" "gb" "br"]
   :patterns ["brwrr"
              "bggr"
              "gbbr"
              "rrbgbr"
              "ubwu"
              "bwurrg"
              "brgr"
              "bbrgwb"]})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d19-s00 (d19/parse d19-s00-raw)))))