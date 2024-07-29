(ns aoc-clj.2016.day19
  "Solution to https://adventofcode.com/2016/day/18" 
  (:require [clojure.math :as math]))

;; Input parsing
(defn parse
  [input]
  (read-string (first input)))

;; Puzzle logic
(defn part1-winning-elf
  "Determines which elf ends up winning (with all the presents) using
   the stealing logic from part1"
  [n-elves]
  ;; Working out the sequence by hand, it becomes apparent that 
  ;; whenever the number of elves is equal to a power of two, it's
  ;; the elf in the first position (1) that wins. For every number
  ;; of elves greater than a power of two (but less than the next power),
  ;; the winning elf is always the next odd number counting up from 1.
  ;;
  ;; In other words, if n_elves = 2^i + x (where x < 2^i),
  ;; the winner is (2*x + 1)
  (let [power-of-two  (math/floor (/ (math/log n-elves) (math/log 2)))
        nearest-power (int (math/pow 2 power-of-two))
        rem           (- n-elves nearest-power)]
    (+ (* 2 rem) 1)))

(defn part2-winning-elf
  "Determines which elf ends up winning all the presents using the 
   stealing logic from part2"
  [n-elves]
  ;; This pattern takes a bit more to work out than that of part1
  ;; You can sketch out the outcomes by hand, but I ended up coding
  ;; up the helper simulations that are in the comments at the end
  ;; of this file. It turns out that the winning elf can be found
  ;; by looking to see where they fall in the interval 3^i to 3^(i+1)
  ;;
  ;; If n-elves exactly equals 3^i, that's the winning elf
  ;; (i.e., 1, 3, 9, 27, 81, ...)
  ;;
  ;; If n-elves is between 3^i and 2*3^i, then the winning elf
  ;; is just how far n-elves is away from 3^i, e.g. 
  ;; n = 4, winner = 1
  ;; n = 5, winner = 2
  ;; n = 6, winner = 3
  ;; n = 28, winner = 1
  ;; n = 29, winner = 2
  ;; n = 30, winner = 3
  ;; 
  ;; Finally, when n-elves is greater than 2*3^i, each additional
  ;; elf increases the winner by 2. 
  ;; n = 7, winner = 5
  ;; n = 8, winner = 7
  ;; n = 9, winner = 9 
  ;; n = 55, winner = 29
  ;; n = 56, winner = 31
  ;; n = 57, winner = 33
  (let [power-of-three (math/floor (/ (math/log n-elves) (math/log 3)))
        nearest-power  (int (math/pow 3 power-of-three))
        rem            (- n-elves nearest-power)]
    (cond
      (zero? rem) nearest-power
      (<= rem nearest-power) rem
      :else (- (* 2 rem) nearest-power))))

;; Puzzle solutions
(defn part1
  "Which elf gets all the presents?"
  [input]
  (part1-winning-elf input))

(defn part2
  "Which elf gets all the presents with the part2 rules?"
  [input]
  (part2-winning-elf input))


;; Supplemental
(comment
  (defn- vec-dissoc
    "Return a new vector from `v` without the element at `idx`"
    [v idx]
    (into (subvec v 0 idx) (subvec v (inc idx))))
  
  (defn- next-elf
    [pos to-be-removed]
    (if (< to-be-removed pos)
      pos
      (inc pos))) 

  (defn- part2-sim
    "A literal implementation of the part2 stealing logic to walk through
   generating the pattern (if any)"
    [elves pos]
    (let [n-elves (count elves)] 
      (if (= 1 n-elves)
        (first elves)
        (let [to-be-removed (mod (+ pos (quot n-elves 2)) n-elves)]
          (part2-sim 
           (vec-dissoc elves to-be-removed)
           (mod (next-elf pos to-be-removed) (dec n-elves))))))) 

  (defn- test-pattern
    []
    (->> (range 1 100)
         (map #(part2-sim (vec (range 1 (inc %))) 0))
         (map-indexed #(vector (inc %1) %2))))
  
  (print test-pattern))

