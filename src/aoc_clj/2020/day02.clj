(ns aoc-clj.2020.day02
  "Solution to https://adventofcode.com/2020/day/2"
  (:require [clojure.string :as str]))

(defn parse-line
  [input]
  (let [segments  (str/split input #" ")
        limits    (str/split (first segments) #"-")
        character (first (second segments))
        password  (last segments)]
    {:min (Integer/parseInt (first limits))
     :max (Integer/parseInt (second limits))
     :char character
     :pass password}))

(defn parse
  [input]
  (map parse-line input))

(defn part1-valid?
  [{:keys [min max char pass]}]
  (let [count (get (frequencies pass) char)]
    (and
     (not (nil? count))
     (>= count min)
     (<= count max))))

(defn part2-valid?
  [{:keys [min max char pass]}]
  (let [a (nth pass (dec min))
        b (nth pass (dec max))]
    (or (and (= a char)
             (not (= b char)))
        (and (= b char)
             (not (= a char))))))

(defn day02-part1-soln
  [input]
  (count (filter part1-valid? input)))

(defn day02-part2-soln
  [input]
  (count (filter part2-valid? input)))