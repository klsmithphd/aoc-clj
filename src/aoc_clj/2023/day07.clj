(ns aoc-clj.2023.day07
  (:require [clojure.string :as str]))

(defn card-map
  [ch]
  (case ch
    \A 14
    \K 13
    \Q 12
    \J 11
    \T 10
    (read-string (str ch))))

(defn parse-row
  [row]
  (let [[hand-str bid-str] (str/split row #" ")]
    {:hand (mapv card-map hand-str)
     :bid  (read-string bid-str)}))

(defn parse
  [input]
  (map parse-row input))