(ns aoc-clj.2015.day04
  "Solution to https://adventofcode.com/2015/day/4"
  (:import java.security.MessageDigest))

(def parse first)
(def md5-alg (MessageDigest/getInstance "MD5"))

(defn md5-bytes
  [^String s]
  (.digest md5-alg (.getBytes s)))

(defn starts-with-five-zeros?
  [bytes]
  (and (zero? (aget bytes 0))
       (zero? (aget bytes 1))
       (>= (aget bytes 2) 0)
       (< (aget bytes 2) 16)))

(defn starts-with-six-zeros?
  [bytes]
  (and (zero? (aget bytes 0))
       (zero? (aget bytes 1))
       (zero? (aget bytes 2))))

(defn first-to-meet-condition
  [condition secret-key]
  (let [thehash #(md5-bytes (str secret-key %))]
    (->>  (range)
          (filter (comp condition thehash))
          first)))

(def first-to-start-with-five-zeros
  (partial first-to-meet-condition starts-with-five-zeros?))

(def first-to-start-with-six-zeros
  (partial first-to-meet-condition starts-with-six-zeros?))

(defn day04-part1-soln
  [input]
  (first-to-start-with-five-zeros input))

(defn day04-part2-soln
  [input]
  (first-to-start-with-six-zeros input))