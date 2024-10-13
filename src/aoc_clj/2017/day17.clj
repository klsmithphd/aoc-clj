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

(defn val-after-2017
  [size]
  (let [final (first (final-state part1-limit size))
        idx (u/index-of (u/equals? part1-limit) final)]
    (get final (mod (inc idx) (inc part1-limit)))))

(defn next-pos
  [size [pos num]]
  [(inc (mod (+ pos size) (inc num))) (inc num)])

(defn val-after-zero
  [limit size]
  (let [next-pos-step (partial next-pos size)]
    (->> (iterate next-pos-step [0 0])
         (filter #(= 1 (first %)))
         (take-while #(<= (second %) limit))
         last
         second)))

;; Puzzle solutions
(defn part1
  "What is the value after 2017 in your completed circular buffer?"
  [input]
  (val-after-2017 input))

(defn part2
  "What is the value after 0 the moment 50000000 is inserted?"
  [input]
  (val-after-zero part2-limit input))