(ns aoc-clj.2022.day21-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2022.day21 :as t]))

(def d21-s01
  (t/parse
   ["root: pppw + sjmn"
    "dbpl: 5"
    "cczh: sllz + lgvd"
    "zczc: 2"
    "ptdq: humn - dvpt"
    "dvpt: 3"
    "lfqf: 4"
    "humn: 5"
    "ljgn: 2"
    "sjmn: drzm * dbpl"
    "sllz: 4"
    "pppw: cczh / lfqf"
    "lgvd: ljgn * ptdq"
    "drzm: hmdt - zczc"
    "hmdt: 32"]))

(deftest yell-test
  (testing "Yells out the value for root on the sample data"
    (is (= 152 (t/root-yell d21-s01)))))

(deftest day21-part1-soln
  (testing "Reproduces the answer for day21, part1"
    (is (= 63119856257960 (t/day21-part1-soln)))))

;; (deftest day21-part2-soln
;;   (testing "Reproduces the answer for day21, part2"
;;     (is (= 0 (t/day21-part2-soln)))))