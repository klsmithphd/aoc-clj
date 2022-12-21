(ns aoc-clj.2022.day21
  "Solution to https://adventofcode.com/2022/day/21"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(defn parse-line
  [line]
  (let [[l r] (str/split line #": ")
        args  (str/split r #" ")]
    [l (if (= 1 (count args))
         (read-string (first args))
         args)]))

(defn parse
  [input]
  (into {} (map parse-line input)))

(def d21-s01
  (parse
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

(defn yell
  [jobs monkey]
  (let [job (get jobs monkey)]
    (if (number? job)
      job
      (let [[a op b] job]
        ((eval (read-string op)) (yell jobs a) (yell jobs b))))))

(def day21-input (parse (u/puzzle-input "2022/day21-input.txt")))

(defn day21-part1-soln
  []
  (yell day21-input "root"))