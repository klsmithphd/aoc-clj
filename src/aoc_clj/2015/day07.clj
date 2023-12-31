(ns aoc-clj.2015.day07
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(defn tokenize
  [s]
  (if (number? (read-string s))
    (read-string s)
    s))

(defn parse-unary
  [s]
  (let [[op a] (str/split s #" ")]
    {:op (keyword (str/lower-case op)) :args [(tokenize a)]}))

(defn parse-binary
  [s]
  (let [[a op b] (str/split s #" ")]
    {:op (keyword (str/lower-case op)) :args [(tokenize a)
                                              (tokenize b)]}))

(defn parse-assign
  [s]
  {:op :assign :args (tokenize s)})

(defn parse
  [line]
  (let [[ops dest] (str/split line #" -> ")
        inst (cond
               (str/includes? ops "NOT")             (parse-unary ops)
               (some? (re-find #"AND|OR|SHIFT" ops)) (parse-binary ops)
               :else (parse-assign ops))]
    [(tokenize dest) inst]))

(def day07-input (into {} (map parse (u/puzzle-input "inputs/2015/day07-input.txt"))))

(def wire-val
  (memoize
   (fn  [circuit wire]
     (let [{:keys [op args]} (get circuit wire)]
       (case op
         :assign (if (number? args)
                   args
                   (wire-val circuit args))
         :or  (bit-or  (wire-val circuit (first args)) (wire-val circuit (second args)))
         :and (if (number? (first args))
                (bit-and (first args)                    (wire-val circuit (second args)))
                (bit-and (wire-val circuit (first args)) (wire-val circuit (second args))))
         :lshift (bit-shift-left  (wire-val circuit (first args)) (second args))
         :rshift (bit-shift-right (wire-val circuit (first args)) (second args))
         :not (bit-and 65535 (bit-not (wire-val circuit (first args)))))))))

(defn override-wire-b
  [circuit val]
  (assoc circuit "b" {:op :assign :args val}))

(defn day07-part1-soln
  []
  (wire-val day07-input "a"))

(defn day07-part2-soln
  []
  (let [circuit day07-input
        wirea (wire-val circuit "a")
        newcircuit (override-wire-b circuit wirea)]
    (wire-val newcircuit "a")))