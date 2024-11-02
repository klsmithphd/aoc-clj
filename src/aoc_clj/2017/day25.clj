(ns aoc-clj.2017.day25
  "Solution to https://adventofcode.com/2017/day/25"
  (:require
   [aoc-clj.utils.core :as u]
   [clojure.string :as str]))

;; Input Parsing
(defn last-word
  [line]
  (let [last-chars (last (str/split line #" "))
        len        (count last-chars)]
    (subs last-chars 0 (dec len))))

(defn parse-chunk
  [chunk]
  (let [[a b c d e f g h i] (map last-word chunk)]
    [a {(read-string b) {:write (read-string c)
                         :move (keyword d)
                         :state e}
        (read-string f) {:write (read-string g)
                         :move (keyword h)
                         :state i}}]))

(defn parse
  [input]
  (->> (u/split-at-blankline input)
       (map parse-chunk)
       (into {})))