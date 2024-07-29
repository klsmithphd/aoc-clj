(ns aoc-clj.2016.day19
  "Solution to https://adventofcode.com/2016/day/18" 
  (:require [clojure.math :as math]))

;; Input parsing
(defn parse
  [input]
  (read-string (first input)))

;; Puzzle logic
(defn winning-elf
  [n-elves]
  (let [power-of-two  (math/floor (/ (math/log n-elves) (math/log 2)))
        nearest-power (int (math/pow 2 power-of-two))
        rem           (- n-elves nearest-power)]
    (+ (* 2 rem) 1)))

;; Sequence
;; 1 elf -- winner: 1
;; 2 elves -- 1 takes from 2 ---  winner: 1
;; 3 elves -- 1 takes from 2, 3 takes from 1 --- winner: 3
;; 4 elves -- 1 takes 2, 3 takes 4, 1 takes from 3 -- winner: 1
;; 5 elves -- 1 takes 2, 3 takes 4, 5 takes 1, 3 takes 5, --- winner: 3
;; 6 elves -- 1 takes 2, 3 takes 4, 5 takes 6, 1 takes 3, 5 takes 1 -- winner 5
;; 7 elves -- 1 t 2, 3 t 4, 5 t 6, 7 t 1, 3 t 5, 7 t 3 -- winner 7
;; 8 elves -- 1 t 2, 3 t 4, 5 t 6, 7 t 8, 1 t 3, 5 t 7, 1 t 5 -- winner 1
;; 9 elves -- 1 t 2, 3 t 4, 5 t 6, 7 t 8, 9 t 1, 3 t 5, 7 t 9, 3 t 7 -- winner 3
;; 10 elves - 1 t 2, 3 t 4, 5 t 6, 7 t 8, 9 t 10, 1 t 3, 5 t 7, 9 t 1, 5 t 9 -- winner 5
;; 11 elves - 1 t 2, 3 t 4, 5 t 6, 7 t 8, 9 t 10, 11 t 1, 3 t 5, 7 t 9, 11 t 3, 7 t 11 -- winner 7
;; 12 elves - 1t2, 3t4, 5t6, 7t8, 9t10, 11t12, 1t3, 5t7, 9t11, 1t5, 9t1 -- winner 9
;; 13 elves - 1t2, 3t4, 5t6, 7t8, 9t10, 11t12, 13t1, 3t5, 7t9, 11t13, 3t7, 11t3 --- winner 11
;; 14 elves - 1t2, 3t4, 5t6, 7t8, 9t10, 11t12, 13t14, 1t3, 5t7, 9t11, 13t1, 5t9, 13t5 -- winner 13
;; 15 elves - 1t2, 3t4, 5t6, 7t8, 9t10, 11t12, 13t14, 15t1, 3t5, 7t9, 11t13, 15t3, 7t11, 15t7 -- winner 15
;; 16 elves - 1t2, 3t4, 5t6, 7t8, 9t10, 11t12, 13t14, 15t16, 1t3, 5t7, 9t11, 13t15, 1t5, 9t13, 1t9 -- winner 1


;; It's seeming like the sequence is
;; 1        --- up to 2
;; 1, 3     --- up to 4
;; 1, 3, 5, 7  --- up to 8
;; 1, 3, 5, 7, 9, 11, 13, 15 --- up to 16
;; 1,                        --- guessing up to 32
;; and so on

(comment
  [1, 1, 3, 1, 3, 5, 7, 1, 3, 5, 7, 9, 11, 13, 15, 1])





;; Puzzle solutions
(defn part1
  [input]
  (winning-elf input))