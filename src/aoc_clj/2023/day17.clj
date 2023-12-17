(ns aoc-clj.2023.day17
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(defn parse-line
  [line]
  (u/str->vec (str/join " " line)))

(defn parse
  [input]
  (mapv parse-line input))