(ns aoc-clj.2024.day22
  "Solution to https://adventofcode.com/2024/day/22")

;; Constants
(def part1-n 2000)

;; Input parsing
(defn parse
  [input]
  (map read-string input))

;; Puzzle logic
(defn mix
  "Mixing is defined as taking this bitwise XOR of a value into the number"
  [num mix-val]
  (bit-xor num mix-val))

(defn prune
  "Pruning is defined as the value of the number modulo 16777216"
  [num]
  (mod num 16777216))

(defn op-mix-and-prune
  [op arg num]
  (->> (op num arg)
       (mix num)
       prune))

(defn next-secret
  "Returns the next secret in the sequence from the current num"
  [num]
  (->> num
       (op-mix-and-prune bit-shift-left 6)  ;; Multiply by 64
       (op-mix-and-prune bit-shift-right 5) ;; Truncate-divide by 32
       (op-mix-and-prune bit-shift-left 11) ;; Multiply by 2048
       ))

(defn secret-seq
  "Returns a lazy seq of the next values of the secret"
  [num]
  (iterate next-secret num))

(defn secret-at-n
  [n num]
  (first (drop n (secret-seq num))))

(defn secrets-at-n-sum
  [n nums]
  (->> nums
       (map #(secret-at-n n %))
       (reduce +)))

;; Puzzle solutions
(defn part1
  [input]
  (secrets-at-n-sum part1-n input))