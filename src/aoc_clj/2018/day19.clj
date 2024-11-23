(ns aoc-clj.2018.day19
  "Solution to https://adventofcode.com/2018/day/19")

;; Input parsing
(defn parse-ops
  [op]
  (let [opcode (subs op 0 4)
        nums   (mapv read-string (re-seq #"\d+" op))]
    (cons (keyword opcode) nums)))

(defn parse
  [[line1 & lines]]
  {:ip (read-string (re-find #"\d" line1))
   :insts (map parse-ops lines)})