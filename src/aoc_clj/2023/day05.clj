(ns aoc-clj.2023.day05
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(defn str->numbers
  [s]
  (map read-string (str/split s #"\s")))

(defn parse-map
  [chunk]
  (map str->numbers (rest chunk)))

(defn parse-seeds
  [seeds-str]
  (-> (str/split seeds-str #": ")
      second
      str->numbers))

(defn parse
  [input]
  (let [chunks (u/split-at-blankline input)]
    {:seeds (parse-seeds (ffirst chunks))
     :maps  (mapv parse-map (rest chunks))}))

(defn within-range?
  [number [_ src_start length]]
  (<= src_start number (+ src_start length -1)))

(defn destination
  [number [dest_start src_start _]]
  (+ (- number src_start) dest_start))

(defn apply-map
  [number ranges]
  (let [matches (filter #(within-range? number %) ranges)]
    (if (seq matches)
      (destination number (first matches))
      number)))

(defn mappings
  [number ranges]
  (reductions apply-map number ranges))

(defn location
  [number ranges]
  (reduce apply-map number ranges))

(defn lowest-location
  [{:keys [seeds maps]}]
  (apply min (map #(location % maps) seeds)))

(defn seed-range
  [[start length]]
  (range start (+ start length)))

(defn seed-ranges
  [{:keys [seeds]}]
  (mapcat seed-range (partition 2 seeds)))

(defn lowest-location-ranges
  [input]
  (lowest-location (assoc input :seeds (seed-ranges input))))

(defn day05-part1-soln
  [input]
  (lowest-location input))

(defn day05-part2-soln
  [input]
  (lowest-location-ranges input))
