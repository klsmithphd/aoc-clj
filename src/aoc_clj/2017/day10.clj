(ns aoc-clj.2017.day10
  "Solution to https://adventofcode.com/2017/day/10"
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.math :as math]))

;; Constants
(def append-seq [17 31 73 47 23])

;; Input parsing
(def parse u/firstv)

;; Puzzle logic
(defn twist
  [{:keys [v pos skip] :as state} len]
  (let [shifted (vec (u/rotate pos v))
        new-v (concat (reverse (subvec shifted 0 len))
                      (subvec shifted len))]
    (-> state
        (assoc :v (u/rotate (- pos) new-v))
        (update :pos #(math/mod-add (count v) % (+ len skip)))
        (update :skip inc))))

(defn first-two-nums-prod
  [loop-size lengths]
  (->> (reduce twist {:v (range loop-size) :pos 0 :skip 0} lengths)
       :v
       (take 2)
       (reduce *)))

;; Puzzle solutions
(defn part1
  [input]
  (first-two-nums-prod 256 input))
