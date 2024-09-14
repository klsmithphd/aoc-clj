(ns aoc-clj.2017.day10
  "Solution to https://adventofcode.com/2017/day/10"
  (:require [aoc-clj.utils.core :as u]))

;; Constants
(def loop-size 256)
(def suffix [17 31 73 47 23])

;; Input parsing
(def parse first)

;; Puzzle logic
(defn twist
  "Reorder the values along the loop.
   
   Starting at the current value of `pos`, reverse the order of the next
   `len` values, then move the `pos` forward by that `len` plus the `skip`
   size, and increment the `skip` value by one."
  [{:keys [v pos skip] :as state} len]
  (let [shifted (vec (u/rotate pos v))
        new-v (concat (reverse (subvec shifted 0 len))
                      (subvec shifted len))]
    (-> state
        (assoc :v (u/rotate (- pos) new-v))
        (update :pos #(mod (+ % len skip) (count v)))
        (update :skip inc))))

(defn first-two-nums-prod
  "After twisting a loop of 256 values by the length values provided,
   multiply the first two numbers in the list."
  [loop-size lengths]
  (->> (reduce twist {:v (range loop-size) :pos 0 :skip 0} lengths)
       :v
       (take 2)
       (reduce *)))

(defn ascii-codes
  "Convert a string `s` into its corresponding ASCII decoded byte values"
  [s]
  (map int s))

(defn knot-hash
  "Compute the Knot Hash for an input string, returning a hex-encoded string"
  [s]
  (let [lengths (->> (concat (ascii-codes s) suffix)
                     (repeat 64)
                     flatten)
        sparse-hash (->> lengths
                         (reduce twist {:v (range loop-size) :pos 0 :skip 0})
                         :v)]
    (->> (partition 16 sparse-hash)
         (map #(apply bit-xor %))
         (map #(format "%02x" %))
         (apply str))))

;; Puzzle solutions
(defn part1
  "After processing the input values, what is the result of multiplying the
   first two numbers in the output list?"
  [input]
  (first-two-nums-prod loop-size (u/str->vec input)))

(defn part2
  "What is the Knot Hash of the puzzle input, interpreted as a string?"
  [input]
  (knot-hash input))