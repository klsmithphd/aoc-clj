(ns aoc-clj.2024.day23-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2024.day23 :as d23]))

(def d23-s00-raw
  ["kh-tc"
   "qp-kh"
   "de-cg"
   "ka-co"
   "yn-aq"
   "qp-ub"
   "cg-tb"
   "vc-aq"
   "tb-ka"
   "wh-tc"
   "yn-cg"
   "kh-ub"
   "ta-co"
   "de-co"
   "tc-td"
   "tb-wq"
   "wh-td"
   "ta-ka"
   "td-qp"
   "aq-cg"
   "wq-ub"
   "ub-vc"
   "de-ta"
   "wq-aq"
   "wq-vc"
   "wh-yn"
   "ka-de"
   "kh-ta"
   "co-tc"
   "wh-qp"
   "tb-vc"
   "td-yn"])

(def d23-s00
  {"aq" #{"cg"}
   "cg" #{"tb"}
   "co" #{"tc"}
   "de" #{"cg" "co" "ta"}
   "ka" #{"co" "de"}
   "kh" #{"ta" "tc" "ub"}
   "qp" #{"kh" "ub"}
   "ta" #{"co" "ka"}
   "tb" #{"ka" "vc" "wq"}
   "tc" #{"td"}
   "td" #{"qp" "yn"}
   "ub" #{"vc"}
   "vc" #{"aq"}
   "wh" #{"qp" "tc" "td" "yn"}
   "wq" #{"aq" "ub" "vc"}
   "yn" #{"aq" "cg"}})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d23-s00 (d23/parse d23-s00-raw)))))

(deftest symmetric-test
  (testing "Returns a symmetric representation of the graph"
    (is (= {"aq" #{"cg" "wq" "vc" "yn"}
            "cg" #{"tb" "de" "aq" "yn"}
            "co" #{"de" "tc" "ka" "ta"}
            "de" #{"cg" "co" "ka" "ta"}
            "ka" #{"tb" "co" "de" "ta"}
            "kh" #{"tc" "qp" "ta" "ub"}
            "qp" #{"kh" "td" "wh" "ub"}
            "ta" #{"kh" "co" "de" "ka"}
            "tb" #{"cg" "wq" "vc" "ka"}
            "tc" #{"kh" "td" "co" "wh"}
            "td" #{"yn" "tc" "wh" "qp"}
            "ub" #{"wq" "kh" "vc" "qp"}
            "vc" #{"wq" "tb" "aq" "ub"}
            "wh" #{"td" "yn" "tc" "qp"}
            "wq" #{"tb" "vc" "aq" "ub"}
            "yn" #{"cg" "td" "aq" "wh"}}
           (d23/symmetric d23-s00)))))

(deftest three-node-networks
  (testing "Finds all of the three-node networks"
    (is (= #{#{"aq" "cg" "yn"}
             #{"aq" "vc" "wq"}
             #{"co" "de" "ka"}
             #{"co" "de" "ta"}
             #{"co" "ka" "ta"}
             #{"de" "ka" "ta"}
             #{"kh" "qp" "ub"}
             #{"qp" "td" "wh"}
             #{"tb" "vc" "wq"}
             #{"tc" "td" "wh"}
             #{"td" "wh" "yn"}
             #{"ub" "vc" "wq"}}
           (d23/three-node-networks (d23/symmetric d23-s00))))))

(deftest three-node-networks-with-t-test
  (testing "Counts the three-node networks with nodes starting with t"
    (is (= 7 (d23/three-node-networks-with-t d23-s00)))))

(def day23-input (u/parse-puzzle-input d23/parse 2024 23))

(deftest part1-test
  (testing "Reproduces the answer for day23, part1"
    (is (= 1075 (d23/part1 day23-input)))))