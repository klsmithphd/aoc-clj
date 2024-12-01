(ns aoc-clj.2018.day21
  "Solution to https://adventofcode.com/2018/day/21"
  (:require [aoc-clj.2018.day19 :as d19]
            [aoc-clj.utils.core :as u]))

;; Constants
(def init-regs d19/init-regs)

;; Input parsing
(def parse d19/parse)

;; Puzzle logic
(defn only-halt-points
  "Executes the program and returns the value of the 5th register
   whenever the program could be halted."
  [regs {:keys [ip] :as program}]
  (let [stepper  (partial d19/step program)
        running? #(not= 28 (get % ip 0))]
    (->> regs
         (iterate stepper)
         (remove running?)
         (map #(get % 5)))))

(defn lowest-integer-to-halt
  [program]
  (first (only-halt-points init-regs program)))

(defn equiv-program
  "A program equivalent to *my* opcode program, which returns the next value
   that can be assigned to the 0th register to cause the program to 
   terminate, based on the previous value of 5th register `f`.
   
   See the notes at the bottom of this source file for the derivation"
  [reg5]
  (loop [c (bit-or reg5 65536) f 7571367]
    (if (zero? c)
      f
      (recur (quot c 256)
             (->> (bit-and c 255)
                  (+ f)
                  (bit-and 16777215)
                  (* 65899)
                  (bit-and 16777215))))))

(defn max-run-value
  "Using the equivalent program, find the penultimate value of the 5th
   register that can halt the program before the program enters into
   a recurrence loop."
  []
  (let [[_ scd] (u/first-duplicates (iterate equiv-program 0))]
    (->> (iterate equiv-program 0)
         (drop (dec scd))
         first)))

;; Puzzle solutions
(defn part1
  "What is the lowest non-negative integer value for register 0 that causes the
   program to halt after executing the fewest instructions?"
  [input]
  (lowest-integer-to-halt input))

(defn part2
  "What is the lowest non-negative integer value for register 0 that causes
   the program to halt after executing the most instructions?"
  [_]
  (max-run-value))

;; Notes

;;       72 -> 0000 0000 0000 0000 0100 1000
;;      123 -> 0000 0000 0000 0000 0111 1011
;;      255 -> 0000 0000 0000 0000 1111 1111
;;      456 -> 0000 0000 0000 0001 1100 1000
;;    65536 -> 0000 0001 0000 0000 0000 0000
;;  7571367 -> 0111 0011 1000 0111 1010 0111
;; 16777215 -> 1111 1111 1111 1111 1111 1111

;; I'm using the convention that the registers are named a,b,c,d,e,f:
;; [0 1 2 3 4 5]
;; [a b c d e f]

;; Here's how to translate the program:
;;      "#ip 1"               ;; instruction pointer is register 1 "b"
;; 00   "seti 123 0 5"        ;; f = 123
;; 01   "bani 5 456 5"        ;; f &= 456
;; 02   "eqri 5 72 5"         ;; f = 1 if 72 == f else 0
;; 03   "addr 5 1 1"          ;; b += f ;; if 72 == f, jump to 5, else jump to 4
;; 04   "seti 0 0 1"          ;; b = 0
;; 05   "seti 0 9 5"          ;; f = 0
;; 06   "bori 5 65536 2"      ;; c = f ^ 65536 ;; any of lower 17 bits of f
                                               ;; same as f + 65536 for f < 65536
                                               ;; initially c = 65536
;; 07   "seti 7571367 9 5"    ;; f = 7571367
;; 08   "bani 2 255 4"        ;; e = c & 255   ;; lowest 8 bits of c
                                               ;; initially e = 0
;; 09   "addr 5 4 5"          ;; f += e
;; 10   "bani 5 16777215 5"   ;; f &= 1677215  ;; lowest 24 bits of f
                                               ;; initially f = 7571367  
;; 11   "muli 5 65899 5"      ;; f *= 65899
;; 12   "bani 5 16777215 5"   ;; f &= 1677215  ;; lowest 24 bits of f
;; 13   "gtir 256 2 4"        ;; e = 1 if 256 > c else 0
;; 14   "addr 4 1 1"          ;; b += e        
;; 15   "addi 1 1 1"          ;; b += 1        ;; if NOT 256 > c, jump to 17
;; 16   "seti 27 1 1"         ;; b = 27        ;; if 256 > c, jump to 28
;; 17   "seti 0 2 4"          ;; e = 0
;; 18   "addi 4 1 3"          ;; d = e + 1
;; 19   "muli 3 256 3"        ;; d *= 256
;; 20   "gtrr 3 2 3"          ;; d = 1 if d > c else 0
;; 21   "addr 3 1 1"          ;; b += d        ;;   
;; 22   "addi 1 1 1"          ;; b += 1        ;; if NOT d>c, jump to 24
;; 23   "seti 25 6 1"         ;; b = 25        ;; IF d>c, jump to 26
;; 24   "addi 4 1 4"          ;; e += 1
;; 25   "seti 17 8 1"         ;; b = 17       ;; jump to 18
;; 26   "setr 4 6 2"          ;; c = e
;; 27   "seti 7 4 1"          ;; b = 7        ;; jump to 8
;; 28   "eqrr 5 0 4"          ;; e = 1 if f == a else 0
;; 29   "addr 4 1 1"          ;; b += e       ;; if f == a, end program
;; 30   "seti 5 5 1"          ;; b = 5        ;; jump to 6

;; Here's a C-ish equivalent:
;; while (f != 72) {
;;   f = 123
;;   f &= 456
;; }
;; f = 0
;; while (a != f) {   
;;   c = f ^ 65536  ;; 65536 = 2^16
;; 
;;   f = 7571367
;;   while (c >= 256) {
;;     f = (1677215 & (65899 * (1677215 & (f + (c & 255)))))
;;     c = c // 256
;;   }
;; }