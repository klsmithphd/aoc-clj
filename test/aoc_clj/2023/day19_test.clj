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
  {:workflows {:px  [[:qkq "a" "<" 2006] [:A "m" ">" 2090]   [:rfg]]
               :pv  [[:R   "a" ">" 1716] [:A]]
               :lnx [[:A   "m" ">" 1548] [:A]]
               :rfg [[:gd  "s" "<"  537] [:R "x" ">" 2440]   [:A]]
               :qs  [[:A   "s" ">" 3448] [:lnx]]
               :qkq [[:A   "x" "<" 1416] [:crn]]
               :crn [[:A   "x" ">" 2662] [:R]]
               :in  [[:px  "s" "<" 1351] [:qqz]]
               :qqz [[:qs  "s" ">" 2770] [:hdj "m" "<" 1801] [:R]]
               :gd  [[:R   "a" ">" 3333] [:R]]
               :hdj [[:A   "m" ">"  838] [:pv]]}
   :parts [[787 2655 1222 2876]
           [1679 44 2067 496]
           [2036 264 79 2244]
           [2461 1339 466 291]
           [2127 1623 2188 1013]]})

(def d19-s01-explicit
  {:px  [[:qkq [["a" "<" 2006]]]
         [:A   [["a" ">=" 2006] ["m" ">" 2090]]]
         [:rfg [["a" ">=" 2006] ["m" "<=" 2090]]]]
   :pv  [[:R   [["a" ">" 1716]]]
         [:A   [["a" "<=" 1716]]]]
   :lnx [[:A   [["m" ">" 1548]]]
         [:A   [["m" "<=" 1548]]]]
   :rfg [[:gd  [["s" "<" 537]]]
         [:R   [["s" ">=" 537] ["x" ">" 2440]]]
         [:A   [["s" ">=" 537] ["x" "<=" 2440]]]]
   :qs  [[:A   [["s" ">" 3448]]]
         [:lnx [["s" "<=" 3448]]]]
   :qkq [[:A   [["x" "<" 1416]]]
         [:crn [["x" ">=" 1416]]]]
   :crn [[:A   [["x" ">" 2662]]]
         [:R   [["x" "<=" 2662]]]]
   :in  [[:px  [["s" "<" 1351]]]
         [:qqz [["s" ">=" 1351]]]]
   :qqz [[:qs  [["s" ">" 2770]]]
         [:hdj [["s" "<=" 2770] ["m" "<" 1801]]]
         [:R   [["s" "<=" 2770] ["m" ">=" 1801]]]]
   :gd  [[:R   [["a" ">" 3333]]]
         [:R   [["a" "<=" 3333]]]]
   :hdj [[:A   [["m" ">" 838]]]
         [:pv  [["m" "<=" 838]]]]})

(def d19-s01-all-paths
  [[["s" "<" 1351]  ["a" "<" 2006]  ["x" "<" 1416]]
   [["s" "<" 1351]  ["a" "<" 2006]  ["x" ">=" 1416] ["x" ">" 2662]]
   [["s" "<" 1351]  ["a" ">=" 2006] ["m" ">" 2090]]
   [["s" "<" 1351]  ["a" ">=" 2006] ["m" "<=" 2090] ["s" ">=" 537] ["x" "<=" 2440]]
   [["s" ">=" 1351] ["s" ">" 2770]  ["s" ">" 3448]]
   [["s" ">=" 1351] ["s" ">" 2770]  ["s" "<=" 3448] ["m" ">" 1548]]
   [["s" ">=" 1351] ["s" ">" 2770]  ["s" "<=" 3448] ["m" "<=" 1548]]
   [["s" ">=" 1351] ["s" "<=" 2770] ["m" "<" 1801]  ["m" ">" 838]]
   [["s" ">=" 1351] ["s" "<=" 2770] ["m" "<" 1801]  ["m" "<=" 838] ["a" "<=" 1716]]])

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

(deftest explicit-conditions-test
  (testing "Expands the conditional logic to be explicit"
    (is (= d19-s01-explicit (t/explicit-conditions d19-s01)))))

(deftest all-accepted-paths
  (testing "Retrieves all the possible rules that allow for reaching an
            accepted outcome"
    (is (= d19-s01-all-paths (t/all-accepted-paths d19-s01)))))

(deftest all-accepted-count
  (testing "Computes how many possible combinations of ratings will
            reach an acceptable outcome"
    (is (= 167409079868000 (t/all-accepted-count d19-s01)))))


(def day19-input (u/parse-puzzle-input t/parse 2023 19))

(deftest day19-part1-soln
  (testing "Reproduces the answer for day19, part1"
    (is (= 319062 (t/day19-part1-soln day19-input)))))

(deftest day19-part2-soln
  (testing "Reproduces the answer for day19, part2"
    (is (= 118638369682135 (t/day19-part2-soln day19-input)))))