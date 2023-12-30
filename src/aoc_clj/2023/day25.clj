(ns aoc-clj.2023.day25
  (:require [clojure.string :as str]
            [aoc-clj.utils.graph :as graph]
            [aoc-clj.utils.core :as u]))

(defn parse-line
  [line]
  (let [[l r] (str/split line #": ")]
    [l (str/split r #" ")]))

(defn parse
  [input]
  (into {} (map parse-line input)))



(defn graphviz-line
  [[a coll]]
  (map #(str a " -- " %) coll))

(defn graphviz-rep
  [input]
  (str/join "\n" (mapcat graphviz-line input)))
