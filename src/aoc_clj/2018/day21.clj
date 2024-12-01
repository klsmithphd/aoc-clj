(ns aoc-clj.2018.day21
  "Solution to https://adventofcode.com/2018/day/21"
  (:require [aoc-clj.2018.day19 :as d19]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.binary :as binary]))

;; Constants
(def init-regs d19/init-regs)

;; Input parsing
(def parse d19/parse)

(def day21-input (u/parse-puzzle-input parse 2018 21))
;; [0 1 2 3 4 5]
;; [a b c d e f]
;;       72 -> 0000 0000 0000 0000 0100 1000
;;      123 -> 0000 0000 0000 0000 0111 1011
;;      255 -> 0000 0000 0000 0000 1111 1111
;;      456 -> 0000 0000 0000 0001 1100 1000
;;    65536 -> 0000 0001 0000 0000 0000 0000
;;  7571367 -> 0111 0011 1000 0111 1010 0111
;; 16777215 -> 1111 1111 1111 1111 1111 1111

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

(defn run-till-able-to-exit
  "Executes the program until it has reached a point where it's possible
   to exit if register 0 is set to the value of register 5.
   
   I determined by walking through my opcode instructions that the
   program jumps to initialization logic, dependent upon the value in
   register 0 before setting the instruction pointer to line 1."
  [regs {:keys [ip] :as program}]
  (let [stepper  (partial d19/step program)
        running? #(not= 28 (get % ip 0))]
    (->> regs
         (iterate stepper)
         (drop-while running?)
         first)))

(defn lowest-integer-to-halt
  [program]
  (-> (run-till-able-to-exit init-regs program)
      (get 5)))

;; Puzzle solutions
(defn part1
  [input]
  (lowest-integer-to-halt input))