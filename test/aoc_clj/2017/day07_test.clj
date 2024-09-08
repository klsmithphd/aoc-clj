(ns aoc-clj.2017.day07-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day07 :as d07]))

(def d07-s00-raw
  ["pbga (66)"
   "xhth (57)"
   "ebii (61)"
   "havc (66)"
   "ktlj (57)"
   "fwft (72) -> ktlj, cntj, xhth"
   "qoyq (66)"
   "padx (45) -> pbga, havc, qoyq"
   "tknk (41) -> ugml, padx, fwft"
   "jptl (61)"
   "ugml (68) -> gyxo, ebii, jptl"
   "gyxo (61)"
   "cntj (57)"])

(def d07-s00
  {"pbga" {:weight 66 :children []}
   "xhth" {:weight 57 :children []}
   "ebii" {:weight 61 :children []}
   "havc" {:weight 66 :children []}
   "ktlj" {:weight 57 :children []}
   "fwft" {:weight 72 :children ["ktlj" "cntj" "xhth"]}
   "qoyq" {:weight 66 :children []}
   "padx" {:weight 45 :children ["pbga" "havc" "qoyq"]}
   "tknk" {:weight 41 :children ["ugml" "padx" "fwft"]}
   "jptl" {:weight 61 :children []}
   "ugml" {:weight 68 :children ["gyxo" "ebii" "jptl"]}
   "gyxo" {:weight 61 :children []}
   "cntj" {:weight 57 :children []}})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d07-s00 (d07/parse d07-s00-raw)))))

(deftest root-node-test
  (testing "Finds the root node"
    (is (= "tknk" (d07/root-node d07-s00)))))

(deftest weight-test
  (testing "Computes the weight of a node"
    (is (= 61 (d07/node-weight d07-s00 "gyxo")))
    (is (= 61 (d07/node-weight d07-s00 "ebii")))
    (is (= 251 (d07/node-weight d07-s00 "ugml")))
    (is (= 243 (d07/node-weight d07-s00 "padx")))))

(deftest corrected-weight-test
  (testing "Finds the corrected weight of the imbalanced node"
    (is (= 60 (d07/corrected-weight d07-s00)))))

(def day07-input (u/parse-puzzle-input d07/parse 2017 7))

(deftest part1-test
  (testing "Reproduces the answer for day07, part1"
    (is (= "rqwgj" (d07/part1 day07-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day07, part2"
    (is (= 333 (d07/part2 day07-input)))))