(ns aoc-clj.2023.day19-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2023.day19 :as t]))

(def d19-s01-raw
  ["px{a<2006:qkq,m>2090:A,rfg}"
   "pv{a>1716:R,A}"
   "lnx{m>1548:A,A}"
   "rfg{s<537:gd,x>2440:R,A}"
   "qs{s>3448:A,lnx}"
   "qkq{x<1416:A,crn}"
   "crn{x>2662:A,R}"
   "in{s<1351:px,qqz}"
   "qqz{s>2770:qs,m<1801:hdj,R}"
   "gd{a>3333:R,R}"
   "hdj{m>838:A,pv}"
   ""
   "{x=787,m=2655,a=1222,s=2876}"
   "{x=1679,m=44,a=2067,s=496}"
   "{x=2036,m=264,a=79,s=2244}"
   "{x=2461,m=1339,a=466,s=291}"
   "{x=2127,m=1623,a=2188,s=1013}"])

(def d19-s01
  {:workflows {:px  [["a" "<" 2006 :qkq] ["m" ">" 2090 :A]   [:rfg]]
               :pv  [["a" ">" 1716 :R]   [:A]]
               :lnx [["m" ">" 1548 :A]   [:A]]
               :rfg [["s" "<" 537 :gd]   ["x" ">" 2440 :R]   [:A]]
               :qs  [["s" ">" 3448 :A]   [:lnx]]
               :qkq [["x" "<" 1416 :A]   [:crn]]
               :crn [["x" ">" 2662 :A]   [:R]]
               :in  [["s" "<" 1351 :px]  [:qqz]]
               :qqz [["s" ">" 2770 :qs]  ["m" "<" 1801 :hdj] [:R]]
               :gd  [["a" ">" 3333 :R]   [:R]]
               :hdj [["m" ">" 838 :A]    [:pv]]}
   :parts [[787 2655 1222 2876]
           [1679 44 2067 496]
           [2036 264 79 2244]
           [2461 1339 466 291]
           [2127 1623 2188 1013]]})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d19-s01 (t/parse d19-s01-raw)))))

(deftest workflow->fn-str-test
  (testing "Converts the rules in a workflow into a string representation of
            a clojure function"
    (is (= "(fn [[x m a s]] (cond (< a 2006) :qkq (> m 2090) :A :else :rfg))"
           (t/workflow->fn-str (get-in d19-s01 [:workflows :px]))))
    (is (= "(fn [[x m a s]] (cond (> m 838) :A :else :pv))"
           (t/workflow->fn-str (get-in d19-s01 [:workflows :hdj]))))))

(def d19-s01-fns (t/functionized-input d19-s01))
(deftest outcome-test
  (testing "Runs the workflows and returns the correct verdict for each part"
    (is (= :A (t/apply-workflows d19-s01-fns (nth (:parts d19-s01) 0))))
    (is (= :R (t/apply-workflows d19-s01-fns (nth (:parts d19-s01) 1))))
    (is (= :A (t/apply-workflows d19-s01-fns (nth (:parts d19-s01) 2))))
    (is (= :R (t/apply-workflows d19-s01-fns (nth (:parts d19-s01) 3))))
    (is (= :A (t/apply-workflows d19-s01-fns (nth (:parts d19-s01) 4))))))

(deftest accepted-parts-test
  (testing "Returns the parts that are accepted"
    (is (= [[787 2655 1222 2876]
            [2036 264 79 2244]
            [2127 1623 2188 1013]]
           (t/accepted-parts d19-s01)))))

(deftest accepted-parts-sum-test
  (testing "Returns the sum of all the ratings of all the accepted parts"
    (is (= 19114 (t/accepted-parts-sum d19-s01)))))

(def day19-input (u/parse-puzzle-input t/parse 2023 19))

(deftest day19-part1-soln
  (testing "Reproduces the answer for day19, part1"
    (is (= 319062 (t/day19-part1-soln day19-input)))))