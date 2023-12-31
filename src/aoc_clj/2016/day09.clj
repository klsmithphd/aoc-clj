(ns aoc-clj.2016.day09
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(def day09-input (first (u/puzzle-input "inputs/2016/day09-input.txt")))

(defn repeat-coll
  [n coll]
  (flatten (repeat n coll)))

(defn decompress
  [s]
  (loop [c (first s) cs (rest s) output []]
    (if (not c)
      (apply str output)
      (if (not= \( c)
        (recur (first cs) (rest cs) (conj output c))
        (let [splits (str/split (apply str cs) #"\)")
              r (str/join ")" (rest splits))
              [num repeats] (map read-string (str/split (first splits) #"x"))
              repeat-seq (take num r)
              remainder (drop num r)]
          (recur (first remainder)
                 (rest remainder)
                 (into output (repeat-coll repeats repeat-seq))))))))

(defn day09-part1-soln
  []
  (count (decompress day09-input)))

(defn decompress-count
  [s]
  (loop [char-count 0 remaining s]
    (let [open-marker-loc (str/index-of remaining "(")]
      (if (nil? open-marker-loc)
        (+ char-count (count remaining))
        (let [close-marker-loc (str/index-of remaining ")")
              marker (subs remaining (inc open-marker-loc) close-marker-loc)
              [size repeats] (map read-string (str/split marker #"x"))
              after (subs remaining (inc close-marker-loc))
              repeat-string (subs after 0 size)
              new (subs after size)]
          (recur (+ (* (decompress-count repeat-string) repeats)
                    char-count open-marker-loc) new))))))

(defn day09-part2-soln
  []
  (decompress-count day09-input))
