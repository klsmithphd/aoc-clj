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

(defn possible?
  [towels pattern]
  ;; (println "Pattern: " pattern)
  (if (empty? pattern)
    true
    (loop [matches (->> (towels (subs pattern 0 1))
                        (filter #(str/starts-with? pattern %)))]
      ;; (println "First match:" (first matches))
      (if (and (empty? matches) (not-empty pattern))
        false
        (if (possible? towels (subs pattern (count (first matches))))
          true
          (recur (rest matches)))))))

;; (defn possible?
;;   [towels pattern]
;;   (boolean (not-empty (possible-helper? towels pattern))))

(defn possible-count
  [{:keys [towels patterns]}]
  (->> patterns
       (filter #(possible? towels %))
       count))

;; Puzzle solutions
(defn part1
  [input]
  (possible-count input))


;; brwrr
;; br wr r ;; Done

;; bggr
;; b g g r

;; gbbr
;; gb br

;; rrbgbr
;; r rb gb r

;; r -> ["" b]
;; w -> r
;; b -> ["" r {w [u]}]
;; g -> ["" b]


;; gur, uub, wgwu, rggu, urguur

;; (str/starts-with?)