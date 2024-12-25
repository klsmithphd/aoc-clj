(ns aoc-clj.2024.day19
  "Solution to https://adventofcode.com/2024/day/19"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

;; Input parsing
(defn parse-towels
  [towels]
  (->> (str/split (first towels) #", ")
       (group-by #(subs % 0 1))
       (u/fmap #(sort-by count > %))))

(defn parse
  [input]
  (let [[towels patterns] (u/split-at-blankline input)]
    {:towels  (parse-towels towels)
     :patterns patterns}))

;; Puzzle logic
(defn- dp-update-helper
  "Returns a function that can be used in a `reduce` to update the number of
   arrangements at a given position for any possibly matching towels
   at the current position"
  [substr i len]
  (fn
    [dp towel]
    (if (str/starts-with? substr towel)
      (let [end-pos (+ i (count towel))]
        (if (<= end-pos len)
          ;; Increase the count of arrangements at this position by
          ;; the number of known arrangements at the end position
          (update dp i + (dp end-pos))
          dp))
      dp)))

(defn towel-arrangements
  "Returns the number of possible ways that the pattern can be created using
   the available towels.
   
   Uses dynamic programming to build up the number of arrangements from
   the end of the string to the beginning"
  [towels pattern]
  (let [len (count pattern)]
    (loop [i  (dec len) ;; Start at the end of the pattern and work backwards
           dp (conj (vec (repeat len 0)) 1)] ;; The blank pattern has one arrangement
      (if (neg? i)
        ;; Return count of arrangements for full string
        (dp 0)
        (let [substr          (subs pattern i)
              possible-towels (towels (subs substr 0 1))
              count-updater   (dp-update-helper substr i len)]
          (recur (dec i) (reduce count-updater dp possible-towels)))))))

(defn possible?
  "Returns true if it's possible to create the pattern using the available
   towels"
  [towels pattern]
  (pos? (towel-arrangements towels pattern)))

(defn arrangement-count
  "Returns the number of ways that the pattern can be created using the
   available towels"
  [towels pattern]
  (towel-arrangements towels pattern))

(defn possible-count
  "Returns the number of patterns that can be created from the available towels"
  [{:keys [towels patterns]}]
  (->> patterns
       (filter #(possible? towels %))
       count))

(defn arrangement-total
  "Returns the total number of all possible towel arrangements"
  [{:keys [towels patterns]}]
  (->> patterns
       (map #(arrangement-count towels %))
       (reduce +)))

;; Puzzle solutions
(defn part1
  "How many designs are possible?"
  [input]
  (possible-count input))

(defn part2
  "What do you get if you add up the number of different ways you could
   make each design?"
  [input]
  (arrangement-total input))