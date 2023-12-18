(ns aoc-clj.2023.day05
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(defn convert-map
  [[dest_start src_start length]]
  [src_start (+ src_start length -1) (- dest_start src_start)])

(defn parse-maps
  [chunk]
  (map (comp convert-map u/str->vec) (rest chunk)))

(defn parse-seeds
  [seeds-str]
  (let [[_ seeds] (str/split seeds-str #": ")]
    (u/str->vec seeds)))

(defn parse
  [input]
  (let [chunks (u/split-at-blankline input)]
    {:seeds      (parse-seeds (ffirst chunks))
     :range-maps (mapv parse-maps (rest chunks))}))

(defn destination
  "Apply the range map offset to the supplied number to map it to its 
   destination"
  [number [_ _ offset]]
  (+ number offset))

(defn within-range?
  "Determins whether the supplied number falls within the inclusive
   range limits"
  [number [mn mx _]]
  (<= mn number mx))

(defn apply-range-map
  "Apply a level's range maps to the number, translating it to its next
   value"
  [number ranges]
  (let [matches (filter #(within-range? number %) ranges)]
    (if (seq matches)
      (destination number (first matches))
      number)))

(defn range-mappings
  [seed ranges]
  (reductions apply-range-map seed ranges))

(defn location
  "Apply all of the respective range maps to the supplied seed number to
   find its final location value"
  [seed ranges]
  (reduce apply-range-map seed ranges))

(defn lowest-location
  "Returns the minimum location determined by mapping all the seed values
   to their final location"
  [{:keys [seeds range-maps]}]
  (apply min (map #(location % range-maps) seeds)))


(defn break-points
  "Returns a collection of all the interval boundaries in the range-maps"
  [range-maps]
  (mapcat #(vector (first %) (second %)) range-maps))

(defn interval-split
  [[mn mx] split-val]
  (if (<= mn split-val mx)
    [[mn split-val] [split-val mx]]
    [[mn mx]]))

(defn reduce-fn
  [intervals split-val]
  (mapcat #(interval-split % split-val) intervals))

(defn all-intervals-split
  [intervals split-vals]
  (reduce reduce-fn intervals split-vals))

(defn intervals-to-propagate
  [intervals range-maps]
  (all-intervals-split intervals (break-points range-maps)))

(defn next-values
  [intervals range-maps]
  (let [new-intervals (intervals-to-propagate intervals range-maps)]
    (->> (flatten new-intervals)
         (map #(apply-range-map % range-maps))
         (partition 2))))

(defn seed-range
  [[start length]]
  [start (+ start length -1)])

(defn seed-ranges
  [seeds]
  (map seed-range (partition 2 seeds)))

(defn range-lowest-location
  [{:keys [seeds range-maps]}]
  (->> (reduce next-values (seed-ranges seeds) range-maps)
       flatten
       (apply min)))


;; (defn lowest-location-ranges
;;   [input]
;;   (lowest-location (assoc input :seeds (seed-ranges input))))

(defn day05-part1-soln
  "What is the lowest location number that corresponds to any of the initial
   seed numbers?"
  [input]
  (lowest-location input))

(defn day05-part2-soln
  "Consider all of the initial seed numbers listed in the ranges on the 
   first line of the almanac. What is the lowest location number that 
   corresponds to any of the initial seed numbers?"
  [input]
  (range-lowest-location input))
