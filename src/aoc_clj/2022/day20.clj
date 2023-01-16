(ns aoc-clj.2022.day20
  "Solution to https://adventofcode.com/2022/day/20"
  (:require [aoc-clj.utils.core :as u]))

;;;; Constants

(def decryption-key 811589153)

;;;; Input parsing

(defn parse
  [input]
  (mapv read-string input))

(def day20-input (u/parse-puzzle-input parse 2022 20))

;;;; Puzzle logic

(defn remove-and-insert
  "Construct a new vector from `v` where the number at `old-pos`
   is moved to `new-pos`"
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
  "Compute the new position based on shifting from `old` by `shift` amount,
   accounting for wrap-around in a vec that goes up to `max-idx`"
  [max-idx old shift]
  (let [newval (+ old shift)]
    (if (zero? newval)
      max-idx
      (mod newval max-idx))))

(defn mix
  "To mix the file, move each number forward or backward in the file a 
   number of positions equal to the value of the number being moved. 
   The list is circular, so moving a number off one end of the list wraps 
   back around to the other end as if the ends were connected."
  [input order idx]
  (let [shift   (input idx)
        old-pos (u/index-of idx order)
        new-pos (bounded-shift (dec (count input)) old-pos shift)]
    (remove-and-insert order old-pos new-pos)))

(defn mixed
  "Return the list of numbers after they have been mixed once."
  [input]
  (let [indices (vec (range (count input)))
        mixer   (partial mix input)]
    (mapv input (reduce mixer indices indices))))

(defn mixed-ten
  "Return the list of numbers after they have been mixed 10 times."
  [input]
  (let [n       (count input)
        indices (vec (range n))
        mixer   (partial mix input)]
    (mapv input (reduce mixer indices (take (* n 10) (cycle indices))))))

(defn grove-coordinates
  "The grove coordinates can be found by looking at the 1000th, 2000th, and 
   3000th numbers after the value 0, wrapping around the list as necessary."
  [nums]
  (let [n (count nums)
        zero-pos (u/index-of 0 nums)]
    (+ (nth nums (mod (+ zero-pos 1000) n))
       (nth nums (mod (+ zero-pos 2000) n))
       (nth nums (mod (+ zero-pos 3000) n)))))

(defn decrypt-and-mix-ten
  "First, you need to apply the decryption key, 811589153. 
   Multiply each number by the decryption key before you begin; 
   this will produce the actual list of numbers to mix.
   
   Second, you need to mix the list of numbers ten times."
  [input]
  (->> (mapv #(* decryption-key %) input)
       mixed-ten
       grove-coordinates))

;;;; Puzzle solutions

(defn day20-part1-soln
  "Mix your encrypted file exactly once. 
   What is the sum of the three numbers that form the grove coordinates?"
  []
  (grove-coordinates (mixed day20-input)))

(defn day20-part2-soln
  "Apply the decryption key and mix your encrypted file ten times. 
   What is the sum of the three numbers that form the grove coordinates?"
  []
  (decrypt-and-mix-ten day20-input))
