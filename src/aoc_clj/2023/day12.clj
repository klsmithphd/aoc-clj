(ns aoc-clj.2023.day12
  (:require [clojure.string :as str]))

(defn parse-line
  [line]
  (let [[l r] (str/split line #" ")]
    {:springs l
     :groups (read-string (str "[" r "]"))}))

(defn parse
  [input]
  (map parse-line input))