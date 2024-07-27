(ns aoc-clj.2016.day15
   "Solution to https://adventofcode.com/2016/day/14"
  (:require [aoc-clj.utils.math :as math]
            [aoc-clj.utils.vectors :as vec]
            [aoc-clj.utils.core :as u]))

;; Input parsing
(defn parse-line
  [line]
  (let [[disc size _ pos] (map read-string (re-seq #"\d+" line))]
    {:disc disc :slot-count size :init-pos pos}))

(defn parse
  [input]
  (map parse-line input))

;; Puzzle logic 
(defn offset-mod
  "Compute the time-position offset and the modulus for a given disc"
  [{:keys [disc slot-count init-pos]}]
  [(+ disc init-pos) slot-count])

(defn coefficients
  [[[offset _] & others]]
  (let [mult (reduce * (map second others))]
    [mult (* mult (- offset))]))

(defn drop-time
  [discs]
  (let [data (map offset-mod discs)
        total-mod (reduce * (map second data))
        n-discs (count data)
        coeffs (->> (map #(u/rotate % data) (range n-discs))
                    (map coefficients))
        [a b]  (vec/vec-sum coeffs)]
    (math/mod-mul total-mod b (math/mod-inverse total-mod a))))

(defn add-new-bottom-disc
  [discs]
  (let [last-disc (:disc (last discs))]
    (conj discs {:disc (inc last-disc) :slot-count 11 :init-pos 0})))

;; Puzzle solutions
(defn part1
  [input]
  (drop-time input))

(defn part2
  [input]
  (drop-time (add-new-bottom-disc input)))