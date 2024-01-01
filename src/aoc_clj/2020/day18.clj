(ns aoc-clj.2020.day18
  "Solution to https://adventofcode.com/2020/day/18"
  (:require [clojure.string :as str]))

(def parse identity)

(defn interpret
  [input]
  (read-string (str "(" input ")")))

(defn interpret2
  [input]
  (let [updated (-> input
                    (str/replace "(" "((")
                    (str/replace ")" "))")
                    (str/replace "*" ")*("))]
    (read-string (str "((" updated "))"))))

(defn operize
  [sym]
  (case sym
    + clojure.core/+
    * clojure.core/*))

(defn infix
  [expr]
  (let [x    (first expr)
        init (if (list? x) (infix x) x)]
    (reduce
     (fn [acc [oper y]]
       (if (list? y)
         ((operize oper) acc (infix y))
         ((operize oper) acc y)))
     init (partition 2 (rest expr)))))

(defn day18-part1-soln
  [input]
  (reduce + (map (comp infix interpret) input)))

(defn day18-part2-soln
  [input]
  (reduce + (map (comp infix interpret2) input)))