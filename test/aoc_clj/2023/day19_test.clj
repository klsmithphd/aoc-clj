(ns aoc-clj.2023.day19-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2023.day19 :as t]))

(def d19-s01-raw
  ["px {a<2006:qkq,m>2090:A,rfg}"
   "pv {a>1716:R,A}"
   "lnx {m>1548:A,A}"
   "rfg {s<537:gd,x>2440:R,A}"
   "qs {s>3448:A,lnx}"
   "qkq {x<1416:A,crn}"
   "crn {x>2662:A,R}"
   "in {s<1351:px,qqz}"
   "qqz {s>2770:qs,m<1801:hdj,R}"
   "gd {a>3333:R,R}"
   "hdj {m>838:A,pv}"
   ""
   "{x=787,m=2655,a=1222,s=2876}"
   "{x=1679,m=44,a=2067,s=496}"
   "{x=2036,m=264,a=79,s=2244}"
   "{x=2461,m=1339,a=466,s=291}"
   "{x=2127,m=1623,a=2188,s=1013}"])

(def d19-s01
  {:rules {"px"  [{:value "a", :oper "<", :num 2006, :outcome "qkq"}
                  {:value "m", :oper ">", :num 2090, :outcome "A"}
                  {:outcome "rfg"}],
           "pv"  [{:value "a", :oper ">", :num 1716, :outcome "R"}
                  {:outcome "A"}],
           "lnx" [{:value "m", :oper ">", :num 1548, :outcome "A"}
                  {:outcome "A"}],
           "rfg" [{:value "s", :oper "<", :num 537, :outcome "gd"}
                  {:value "x", :oper ">", :num 2440, :outcome "R"}
                  {:outcome "A"}],
           "qs"  [{:value "s", :oper ">", :num 3448, :outcome "A"}
                  {:outcome "lnx"}],
           "qkq" [{:value "x", :oper "<", :num 1416, :outcome "A"}
                  {:outcome "crn"}],
           "crn" [{:value "x", :oper ">", :num 2662, :outcome "A"}
                  {:outcome "R"}],
           "in"  [{:value "s", :oper "<", :num 1351, :outcome "px"}
                  {:outcome "qqz"}]
           "qqz" [{:value "s", :oper ">", :num 2770, :outcome "qs"}
                  {:value "m", :oper "<", :num 1801, :outcome "hdj"}
                  {:outcome "R"}],
           "gd"  [{:value "a", :oper ">", :num 3333, :outcome "R"}
                  {:outcome "R"}],
           "hdj" [{:value "m", :oper ">", :num 838, :outcome "A"}
                  {:outcome "pv"}]}
   :ratings [[787 2655 1222 2876]
             [1679 44 2067 496]
             [2036 264 79 2244]
             [2461 1339 466 291]
             [2127 1623 2188 1013]]})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d19-s01 (t/parse d19-s01-raw)))))