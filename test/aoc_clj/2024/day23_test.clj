(ns aoc-clj.2024.day23-test
  (:require [clojure.test :refer [deftest testing is]]
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