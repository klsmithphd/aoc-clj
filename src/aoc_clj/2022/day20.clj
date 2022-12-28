(ns aoc-clj.2022.day20
  "Solution to https://adventofcode.com/2022/day/20"
  (:require [aoc-clj.utils.core :as u]))

(defn parse
  [input]
  (mapv read-string input))

(def d20-s01
  (parse
   ["1"
    "2"
    "-3"
    "3"
    "-2"
    "0"
    "4"]))

(def day20-input (parse (u/puzzle-input "2022/day20-input.txt")))

(defn new-vec
  [v old-pos new-pos]
  (into
   []
   (if (<= old-pos new-pos)
     (concat (subvec v 0 old-pos)
             (subvec v (inc old-pos) (inc new-pos))
             [(get v old-pos)]
             (subvec v (inc new-pos)))
     (concat (subvec v 0 new-pos)
             [(get v old-pos)]
             (subvec v new-pos old-pos)
             (subvec v (inc old-pos))))))

(defn bounded-shift
  [max-idx old shift]
  (let [newval (+ old shift)]
    (if (zero? newval)
      max-idx
      (mod newval max-idx))))

(defn mix
  [input order idx]
  (let [shift   (input idx)
        old-pos (u/index-of idx order)
        new-pos (bounded-shift (dec (count input)) old-pos shift)]
    (new-vec order old-pos new-pos)))

(defn mixed
  [input]
  (let [indices (vec (range (count input)))
        mixer   (partial mix input)]
    (mapv input (reduce mixer indices indices))))

(defn grove-coordinates
  [nums]
  (let [n (count nums)
        zero-pos (u/index-of 0 nums)]
    (+ (nth nums (mod (+ zero-pos 1000) n))
       (nth nums (mod (+ zero-pos 2000) n))
       (nth nums (mod (+ zero-pos 3000) n)))))

(defn day20-part1-soln
  []
  (grove-coordinates (mixed day20-input)))

