(ns aoc-clj.2023.day06)

(defn as-map
  [time dist]
  {:time time :dist dist})

(defn extract-numbers
  [s]
  (map read-string (re-seq #"\d+" s)))

(defn parse
  [input]
  (let [times (extract-numbers (first input))
        dists (extract-numbers (second input))]
    (map as-map times dists)))

(defn distance-options
  [{:keys [time]}]
  (for [i (range 1 time)]
    (* (- time i) i)))

(defn winning-options-count
  [{:keys [dist] :as race}]
  (->> (distance-options race)
       (filter #(> % dist))
       count))

(defn win-count-multiplied
  [races]
  (reduce * (map winning-options-count races)))

(defn day06-part1-soln
  [input]
  (win-count-multiplied input))