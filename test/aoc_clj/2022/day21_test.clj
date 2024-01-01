(ns aoc-clj.2022.day21-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2022.day21 :as t]))

(def d21-s00
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

(deftest parse-test
  (testing "Correctly parses the sample input"
    (is (= d21-s00
           {"root" ["pppw" "+" "sjmn"]
            "dbpl" 5
            "cczh" ["sllz" "+" "lgvd"]
            "zczc" 2
            "ptdq" ["humn" "-" "dvpt"]
            "dvpt" 3
            "lfqf" 4
            "humn" 5
            "ljgn" 2
            "sjmn" ["drzm" "*" "dbpl"]
            "sllz" 4
            "pppw" ["cczh" "/" "lfqf"]
            "lgvd" ["ljgn" "*" "ptdq"]
            "drzm" ["hmdt" "-" "zczc"]
            "hmdt" 32}))))

(deftest yell-test
  (testing "Yells out the value for root on the sample data"
    (is (= 152 (t/root-yell d21-s00)))))

(deftest solve-for-x-test
  (testing "Inverts an equation to solve for the unknown"
    (is (= '(+ 3 8) (t/solve-for-x '(= (- :x 3) 8))))
    (is (= '(- 3 8) (t/solve-for-x '(= (- 3 :x) 8))))
    (is (= '(* 2 6) (t/solve-for-x '(= (/ :x 2) 6))))
    (is (= '(/ 2 6) (t/solve-for-x '(= (/ 2 :x) 6))))
    (is (= '(- 3 (/ 4 2))  (t/solve-for-x '(= (/ 4 (- 3 :x)) 2))))
    (is (= '(- (/ 60 5) 3) (t/solve-for-x '(= (* 6 10) (* 5 (+ :x 3))))))
    (is (= '(- (/ 60 5) 3) (t/solve-for-x '(= (* 5 (+ :x 3)) (* 6 10)))))
    (is (= '(- (/ 60 5) 3) (t/solve-for-x '(= (* (+ :x 3) 5) (* 6 10)))))
    (is (= '(- (/ 60 5) 3) (t/solve-for-x '(= (* (+ 3 :x) 5) (* 6 10)))))))

(deftest humn-value-test
  (testing "Computes the value the human should yell"
    (is (= 301 (t/humn-value d21-s00)))))

(def day21-input (u/parse-puzzle-input t/parse 2022 21))

(deftest day21-part1-soln
  (testing "Reproduces the answer for day21, part1"
    (is (= 63119856257960 (t/day21-part1-soln day21-input)))))

(deftest day21-part2-soln
  (testing "Reproduces the answer for day21, part2"
    (is (= 3006709232464 (t/day21-part2-soln day21-input)))))