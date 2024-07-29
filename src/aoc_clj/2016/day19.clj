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
  ;; the winner is (2*i + 1)
  (let [power-of-two  (math/floor (/ (math/log n-elves) (math/log 2)))
        nearest-power (int (math/pow 2 power-of-two))
        rem           (- n-elves nearest-power)]
    (+ (* 2 rem) 1)))

;; 1 = 3^0     :   1
;; 2 = 2*3^0 + 0:   1
;; 3 = 3^1     :   3
;; 4 = 3^1 +  1:   1
;; 5 = 3^1 +  2:   2
;; 6 = 2*3^1 + 0:   3   (2* 3^1)
;; 7 = 2*3^1 + 1:   5
;; 8 = 2*3^1 +  2;   7
;; 9 = 3^2     ;   9
;; 10 = 3^2 + 1:   1
;; 11 = 3^2 + 2:   2
;; 12 = 3^2 + 3:   3
;; 13 = 3^2 + 4:   4
;; 14 = 3^2 + 5:   5
;; 15 = 3^2 + 6:   6
;; 16 = 3^2 + 7:   7
;; 17 = 3^2 + 8:   8
;; 18 = 2*3^2 + 0: 9  
;; 19 = 2*3^2 + 1: 11
;; 20 = 2*3^2 + 2: 13
;; ...
;; 26 = 2*3^2 + 8: 25
;; 27 = 3^3      : 27
;; 28 = 3^3   + 1: 1
;; 29 = 3^3   + 2: 2
;; ...
;; 53 = 3^3   + 26: 26
;; 54 = 2*3^3     : 27
;; 55 = 2*3^3 + 1 : 29
;; 56 = 2*3^3 + 2 : 31
;; ...
;; 80 = 2*3^3 + 26: 79
;; 81 = 3^4       : 81
;; 82 = 3^4   +  1: 1



(defn vec-dissoc
  "Return a new vector from `v` without the element at `idx`"
  [v idx]
  (into (subvec v 0 idx) (subvec v (inc idx))))

(defn next-elf
  [pos to-be-removed]
  (if (< to-be-removed pos)
    pos
    (inc pos)))

(defn part2-sim
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

(defn test-pattern
  []
  (->> (range 1 100)
       (map #(part2-sim (vec (range 1 (inc %))) 0))
       (map-indexed #(vector (inc %1) %2))))

(print (test-pattern))



;; Puzzle solutions
(defn part1
  "Which elf gets all the presents?"
  [input]
  (part1-winning-elf input))