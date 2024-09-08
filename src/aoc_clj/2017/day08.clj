(ns aoc-clj.2017.day08
  "Solution to https://adventofcode.com/2017/day/8")

;; Input parsing
(defn ineq-map
  [s]
  (case s
    "==" "="
    "!=" "not="
    s))

(def pattern #"(\w+) (\w+) (-?\d+) if (\w+) (\S+) (-?\d+)")

(defn parse-line
  [line]
  (let [[reg op amt if-reg ineq val] (rest (re-find pattern line))]
    [reg op (read-string amt) if-reg (ineq-map ineq) (read-string val)]))

(defn parse
  [input]
  (map parse-line input))