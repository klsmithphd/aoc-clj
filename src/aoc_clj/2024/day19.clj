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
;; We want to return the earliest possible "No" answer if the pattern 
;; cannot be created.

;; (defn possible-helper?
;;   [towels pattern]
;;   (println pattern)
;;   (if (empty? pattern)
;;     true
;;     (if-let [matches (->> (towels (subs pattern 0 1))
;;                           (filter #(str/starts-with? pattern %)))]
;;       (some true? (->> matches
;;                        (map #(subs pattern (count %)))
;;                        (map #(possible-helper? towels %))))
;;       false)))

;; (defn possible-helper?
;;   [towels pattern]
;;   (println pattern (str/blank? pattern))
;;   (if (str/blank? pattern)
;;     true
;;     (lazy-seq
;;      (some true? (->> (towels (subs pattern 0 1))
;;                       (filter #(str/starts-with? pattern %))
;;                       (map #(possible-helper? towels (subs pattern (count %)))))))))

(defn possible?
  [towels pattern]
  (if (str/blank? pattern)
    true
    (loop [matches (->> (towels (subs pattern 0 1))
                        (filter #(str/starts-with? pattern %)))]
      ;; (println "First match:" (first matches))
      (if (and (empty? matches) (not-empty pattern))
        false
        (if (possible? towels (subs pattern (count (first matches))))
          true
          (recur (rest matches)))))))

(defn towel-arrangements
  "Returns a collection of all the possible ways that the pattern can be
   created using the available towels"
  [towels pattern]
  (letfn
   [(helper [pattern]
      (if (str/blank? pattern)
        [[]]
        (for [towel (towels (subs pattern 0 1))
              :when (str/starts-with? pattern towel)
              rest-pattern (helper (subs pattern (count towel)))]
          (cons towel rest-pattern))))]
    (helper pattern)))

(defn towel-arrangements-dp
  "Returns the number of possible ways that the pattern can be created using
   the available towels"
  [towels pattern]
  (let [len (count pattern)]
    (loop [i (dec len) dp {len 1}]
      (if (neg? i)
        ;; Return count of arrangements for full string
        (dp 0)
        (let [substr          (subs pattern i)
              possible-towels (towels (subs pattern i (inc i)))
              option-counts   (fn
                                [dp towel]
                                (if (str/starts-with? substr towel)
                                  (let [next-pos (+ i (count towel))]
                                    (if (<= next-pos len)
                                      (assoc dp i
                                             (+ (get dp i 0) (get dp next-pos 0)))
                                      dp))
                                  dp))]
          (recur (dec i) (reduce option-counts dp possible-towels)))))))

(defn arrangement-count
  "Returns the number of ways that the pattern can be created using the available towels"
  [towels pattern]
  (println pattern)
  (towel-arrangements-dp towels pattern))

(defn possible-count
  "Returns the number of patterns that can be created from the available towels"
  [{:keys [towels patterns]}]
  (->> patterns
       (filter #(possible? towels %))
       count))

(defn arrangement-total
  [{:keys [towels patterns]}]
  (->> patterns
       (filter #(possible? towels %))
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