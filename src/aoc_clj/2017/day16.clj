(ns aoc-clj.2017.day16
  "Solution to https://adventofcode.com/2017/day/16"
  (:require [clojure.string :as str]))

;; Input parsing
(defn parse-inst
  [inst]
  (cond
    (str/starts-with? inst "s") [:spin (read-string (subs inst 1))]
    (str/starts-with? inst "x") [:exchange (map read-string (re-seq #"\d+" inst))]
    (str/starts-with? inst "p") [:partner (str/split (subs inst 1) #"\/")]))

(defn parse
  [input]
  (map parse-inst (str/split (first input) #",")))