(ns aoc-clj.2017.day17
  "Solution to https://adventofcode.com/2017/day/17"
  (:require [aoc-clj.utils.core :as u]))

;; Constants
(def part1-limit 2017)
(def part2-limit 50000000)

;; Input parsing
(def parse (comp read-string first))

;; Puzzle logic
(defn spin-step
  [size [buffer pos nxt-val]]
  (let [len (count buffer)
        new-pos (inc (mod (+ pos size) len))]
    [(into [] (concat (subvec buffer 0 new-pos)
                      [nxt-val]
                      (if (< new-pos len)
                        (subvec buffer new-pos)
                        [])))
     new-pos
     (inc nxt-val)]))

(defn final-state
  [limit size]
  (->> [[0] 0 1]
       (iterate #(spin-step size %))
       (drop limit)
       first))

(defn val-after-target
  [target limit size]
  (let [final (first (final-state limit size))
        idx (u/index-of (u/equals? target) final)]
    (get final (mod (inc idx) (inc limit)))))


;; Puzzle solutions
(defn part1
  [input]
  (val-after-target part1-limit part1-limit input))