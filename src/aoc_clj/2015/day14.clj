(ns aoc-clj.2015.day14
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(defn parse-line
  [line]
  (let [bits (str/split line #" ")
        reindeer (nth bits 0)
        speed (read-string  (nth bits 3))
        fly-duration (read-string (nth bits 6))
        rest-duration (read-string (nth bits 13))]
    [reindeer {:speed speed :fly fly-duration :rest rest-duration}]))

(defn parse
  [input]
  (into {} (map parse-line input)))

(def day14-input (parse (u/puzzle-input "inputs/2015/day14-input.txt")))

(defn distance-at-time
  [time {:keys [speed fly rest]}]
  (let [span (+ fly rest)
        intervals (quot time span)
        remainder (min fly (- time (* intervals span)))]
    (+ (* intervals fly speed) (* remainder speed))))

(defn score
  [positions]
  (let [lead (apply max positions)]
    (mapv #(if (= lead %) 1 0) positions)))

(defn points-at-time
  [time stats]
  (let [times (range 1 (inc time))
        positions (mapv (fn [t] (mapv #(distance-at-time t %) (vals stats))) times)
        scores (apply (partial map vector) (map score positions))]
    (mapv (partial reduce +) scores)))

(defn day14-part1-soln
  []
  (apply max (map (partial distance-at-time 2503) (vals day14-input))))

(defn day14-part2-soln
  []
  (apply max (points-at-time 2503 day14-input)))