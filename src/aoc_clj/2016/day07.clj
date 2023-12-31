(ns aoc-clj.2016.day07
  "Solution to https://adventofcode.com/2016/day/7"
  (:require [clojure.string :as str]))

(defn parse-line
  [line]
  (let [chunks (str/split line #"\[")
        splits (map #(str/split % #"\]") (rest chunks))]
    {:supernets (into [(first chunks)] (map second splits))
     :hypernets (mapv first splits)}))

(defn parse
  [input]
  (map parse-line input))

(defn abba?
  [s]
  (re-seq #"(.)(?!\1)(.)\2\1" s))

(defn supports-tls?
  [{:keys [supernets hypernets]}]
  (if (some identity (filter abba? hypernets))
    false
    (boolean (some identity (filter abba? supernets)))))

(defn all-abas
  [s]
  (re-seq #"(?=((.)(?!\2)(.)\2))" s))

(defn bab
  [[_ _ a b]]
  (re-pattern (str b a b)))

(defn supports-ssl?
  [{:keys [supernets hypernets]}]
  (->> (mapcat all-abas supernets)
       (map bab)
       (filter (fn [re]
                 (some identity (mapcat (partial re-find re) hypernets))))
       (some identity)
       boolean))

(defn day07-part1-soln
  [input]
  (count (filter supports-tls? input)))

(defn day07-part2-soln
  [input]
  (count (filter supports-ssl? input)))

