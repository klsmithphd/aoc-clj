(ns aoc-clj.2023.day25
  (:require [clojure.string :as str]))

(defn parse-line
  [line]
  (let [[l r] (str/split line #": ")]
    [l (str/split r #" ")]))

(defn parse
  [input]
  (into {} (map parse-line input)))