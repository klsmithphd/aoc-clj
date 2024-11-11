(ns aoc-clj.2018.day12
  "Solution to https://adventofcode.com/2018/day/12"
  (:require [aoc-clj.utils.core :as u]
            [clojure.string :as str]))

;; Constants
(def charmap {\# 1 \. 0})

;; Input parsing
(defn parse-state
  [[state]]
  (mapv charmap (subs state 15)))

(defn parse-rule
  [rule]
  (let [[l [r]] (str/split rule #" => ")]
    [(mapv charmap l) (charmap r)]))

(defn parse-rules
  [rules]
  (into {} (map parse-rule rules)))

(defn parse
  [input]
  (let [[state rules] (u/split-at-blankline input)]
    {:state (parse-state state)
     :rules (parse-rules rules)
     :left  0}))
