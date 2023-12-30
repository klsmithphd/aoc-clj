(ns aoc-clj.2023.day25-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2023.day25 :as t]))

(def d25-s01-raw
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

(def d25-s01
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

;; Determined by plotting the graph with graphviz and seeing what links crossed
;; between the two clusters
(def d25-s01-cuts
  [["hfx" "pzl"]
   ["bvb" "cmg"]
   ["nvd" "jqt"]])

;; Determined by plotting the graph with graphviz and seeing what links crossed
;; between the two clusters
(def day25-input-cuts
  [["mhb" "zqg"]
   ["sjr" "jlt"]
   ["fjn" "mzb"]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d25-s01 (t/parse d25-s01-raw)))))

(def day25-input (u/parse-puzzle-input t/parse 2023 25))