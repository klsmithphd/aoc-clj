(ns aoc-clj.2023.day25-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2023.day25 :as t]))

(def d25-s00-raw
  ["jqt: rhn xhk nvd"
   "rsh: frs pzl lsr"
   "xhk: hfx"
   "cmg: qnr nvd lhk bvb"
   "rhn: xhk bvb hfx"
   "bvb: xhk hfx"
   "pzl: lsr hfx nvd"
   "qnr: nvd"
   "ntq: jqt hfx bvb xhk"
   "nvd: lhk"
   "lsr: lhk"
   "rzs: qnr cmg lsr rsh"
   "frs: qnr lhk lsr"])

(def d25-s01-raw
  ["jqt: rhn xhk nvd"
   "rsh: frs pzl lsr"])

(def d25-s00
  {"jqt" ["rhn" "xhk" "nvd"]
   "rsh" ["frs" "pzl" "lsr"]
   "xhk" ["hfx"]
   "cmg" ["qnr" "nvd" "lhk" "bvb"]
   "rhn" ["xhk" "bvb" "hfx"]
   "bvb" ["xhk" "hfx"]
   "pzl" ["lsr" "hfx" "nvd"]
   "qnr" ["nvd"]
   "ntq" ["jqt" "hfx" "bvb" "xhk"]
   "nvd" ["lhk"]
   "lsr" ["lhk"]
   "rzs" ["qnr" "cmg" "lsr" "rsh"]
   "frs" ["qnr" "lhk" "lsr"]})

(def d25-s01
  {"jqt" ["rhn" "xhk" "nvd"]
   "rsh" ["frs" "pzl" "lsr"]})

(def d25-s01-flattened
  [#{"jqt" "rhn"}
   #{"jqt" "xhk"}
   #{"jqt" "nvd"}
   #{"rsh" "frs"}
   #{"rsh" "pzl"}
   #{"rsh" "lsr"}])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d25-s00 (t/parse d25-s00-raw)))
    (is (= d25-s01 (t/parse d25-s01-raw)))))

(deftest flatten-graph-test
  (testing "Flattens the graph to just edge sets"
    (is (= d25-s01-flattened (t/flatten-graph d25-s01)))))

(deftest graph-without-nodes-test
  (testing "Removes edge sets from the graph"
    (is (= [#{"jqt" "xhk"}
            #{"jqt" "nvd"}
            #{"rsh" "pzl"}
            #{"rsh" "lsr"}]
           (t/graph-without-nodes d25-s01-flattened [#{"jqt" "rhn"}
                                                     #{"rsh" "frs"}])))))

(deftest neighbors-test
  (testing "Computes the neighboring (directly connected) nodes"
    (is (= #{"rhn" "xhk" "nvd"} (t/neighbors d25-s01-flattened "jqt")))))

;; Determined by plotting the graph with graphviz and seeing what links crossed
;; between the two clusters
(def d25-s00-cuts
  [#{"hfx" "pzl"}
   #{"bvb" "cmg"}
   #{"nvd" "jqt"}])

(deftest clique-sizes-test
  (testing "Computes the sizes of the two subgraphs after removing 3 edges"
    (is (= #{9 6} (set (t/clique-sizes d25-s00 d25-s00-cuts))))))

(def day25-input (u/parse-puzzle-input t/parse 2023 25))

(deftest part1-test
  (testing "Reproduces the answer for day25, part1"
    (is (= 606062 (t/part1 day25-input)))))