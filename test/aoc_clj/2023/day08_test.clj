(ns aoc-clj.2023.day08-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2023.day08 :as t]))

(def d08-s01-raw ["RL"
                  ""
                  "AAA = (BBB, CCC)"
                  "BBB = (DDD, EEE)"
                  "CCC = (ZZZ, GGG)"
                  "DDD = (DDD, DDD)"
                  "EEE = (EEE, EEE)"
                  "GGG = (GGG, GGG)"
                  "ZZZ = (ZZZ, ZZZ)"])

(def d08-s02-raw ["LLR"
                  ""
                  "AAA = (BBB, BBB)"
                  "BBB = (AAA, ZZZ)"
                  "ZZZ = (ZZZ, ZZZ)"])

(def d08-s01
  {:instructions "RL"
   :nodes {"AAA" {:left "BBB" :right "CCC"}
           "BBB" {:left "DDD" :right "EEE"}
           "CCC" {:left "ZZZ" :right "GGG"}
           "DDD" {:left "DDD" :right "DDD"}
           "EEE" {:left "EEE" :right "EEE"}
           "GGG" {:left "GGG" :right "GGG"}
           "ZZZ" {:left "ZZZ" :right "ZZZ"}}})

(def d08-s02
  {:instructions "LLR"
   :nodes {"AAA" {:left "BBB" :right "BBB"}
           "BBB" {:left "AAA" :right "ZZZ"}
           "ZZZ" {:left "ZZZ" :right "ZZZ"}}})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d08-s01 (t/parse d08-s01-raw)))
    (is (= d08-s02 (t/parse d08-s02-raw)))))