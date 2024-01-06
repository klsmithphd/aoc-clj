(ns aoc-clj.2015.day04
  "Solution to https://adventofcode.com/2015/day/4"
  (:import java.security.MessageDigest))

;; Input parsing
(def parse first)

;; Puzzle logic
(def md5-alg (MessageDigest/getInstance "MD5"))
(defn md5-byte-prefix
  "First 3 bytes (as signed ints) of the MD5 hash of the supplied string"
  [^String s]
  (vec (take 3 (.digest md5-alg (.getBytes s)))))

(defn five-zero-start?
  "Whether the three bytes correspond in hex to starting with five zeroes"
  [[a b c]]
  (and (zero? a) (zero? b) (<= 0 c 15)))

(defn six-zero-start?
  "Whether the three bytes correspond in hex to starting with six zeroes"
  [bytes]
  (every? zero? bytes))

(defn first-integer
  "The first integer that satifies the supplied `condition` given the `secret`"
  [condition secret-key]
  (let [thehash #(md5-byte-prefix (str secret-key %))]
    (->>  (range)
          (filter (comp condition thehash))
          first)))

;; Puzzle solutions
(defn part1
  "The first integer whose MD5 hash in hex starts with five zeroes"
  [input]
  (first-integer five-zero-start? input))

(defn part2
  "The first integer whose MD5 hash in hex starts with six zeros"
  [input]
  (first-integer six-zero-start? input))