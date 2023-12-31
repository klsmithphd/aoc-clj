(ns aoc-clj.2015.day08
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(def hex-pattern #"\\x[0-9a-f]{2}")
(def day08-input (u/puzzle-input "inputs/2015/day08-input.txt"))

(defn hex->str
  [h]
  (->> (str "0x" (subs h 2))
       read-string
       char
       str))

(defn unescape
  [s]
  (let [news (-> s
                 (str/replace hex-pattern #(hex->str %))
                 (str/replace #"\\\"" "\"")
                 (str/replace #"\\\\" "\\\\"))
        len (count news)]
    (subs news 1 (dec len))))

(defn escape-fn
  [c]
  (case c
    \" [\\ \"]
    \\ [\\ \\]
    [c]))

(defn escape
  [s]
  (str/join (flatten [[\"]
                      (mapcat escape-fn s)
                      [\"]])))

(escape "\"abc\"")

(defn code-chars
  [s]
  (count s))

(defn unescaped-chars
  [s]
  (count (unescape s)))

(defn escaped-chars
  [s]
  (count (escape s)))

(defn unescaped-difference
  [input]
  (- (reduce + (map code-chars input))
     (reduce + (map unescaped-chars input))))

(defn escaped-difference
  [input]
  (- (reduce + (map escaped-chars input))
     (reduce + (map code-chars input))))

(defn day08-part1-soln
  []
  (unescaped-difference day08-input))

(defn day08-part2-soln
  []
  (escaped-difference day08-input))