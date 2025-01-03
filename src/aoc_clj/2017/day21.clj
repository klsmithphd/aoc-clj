(ns aoc-clj.2017.day21
  "Solution to https://adventofcode.com/2017/day/21"
  (:require [clojure.string :as str]
            [clojure.math :as math]))

;; Constants
(def start
  "Initial pattern   
   ```
   .#.
   ..#
   ###
   ```"
  [0 1 0
   0 0 1
   1 1 1])

(def charmap {\. 0 \# 1})

;; Input parsing
(defn parse-line
  [line]
  (let [[l r] (-> line
                  (str/replace "/" "")
                  (str/split #" => "))]
    [(mapv charmap l) (mapv charmap r)]))

(defn parse
  [input]
  (into {} (map parse-line input)))

;; Puzzle logic
(defn size
  "Returns either 2 or 3 depending upon the size of a rule's key"
  [rule]
  (int (math/sqrt (count rule))))

(defn flip-h
  "Returns a rule pattern flipped horizontally."
  [rule]
  (let [size (size rule)]
    (->> rule
         (partition size)
         (map reverse)
         (apply concat)
         vec)))

(defn flip-v
  "Returns a rule pattern flipped vertically."
  [rule]
  (let [size (size rule)]
    (->> rule
         (partition size)
         reverse
         (apply concat)
         vec)))

(defn rotate
  "Returns a rule pattern rotated one quarter turn (counter-clockwise)"
  [rule]
  (let [size (size rule)]
    (case size
      2 (mapv rule [1 3 0 2])
      3 (mapv rule [2 5 8 1 4 7 0 3 6]))))

(defn equivalent-matches
  "Returns a new map of all the equivalent rule patterns"
  [[rule replacement]]
  (zipmap
   [rule
    (rotate rule)
    (rotate (rotate rule))
    (rotate (rotate (rotate rule)))
    (flip-v rule)
    (rotate (flip-v rule))
    (rotate (rotate (flip-v rule)))
    (rotate (rotate (rotate (flip-v rule))))]
   (repeat replacement)))

(defn full-rulebook
  "Returns an expanded rulebook with all of the unrepresented keys
   expressed"
  [rules]
  (into {} (map equivalent-matches rules)))

(defn- resquare
  [subsquare-size square-count chunks]
  (->> (take-nth square-count chunks)
       (partition subsquare-size)
       (map #(vec (apply concat %)))))

(defn subsquares
  "Given a square vec of pixels, return a collection of subsquares."
  [pixels]
  (let [size (size pixels)
        subsquare-size (if (zero? (mod size 2)) 2 3)
        square-count (quot size subsquare-size)
        chunks (partition subsquare-size pixels)]
    (->> (range square-count)
         (map #(drop % chunks))
         (map #(resquare subsquare-size square-count %))
         (apply interleave))))

(defn- de-interleave
  [size sq-size chunks]
  (->> (range size)
       (map #(drop % chunks))
       (map #(resquare sq-size size %))
       flatten))

(defn recombine
  "Given a collection of subsquares, put the elements back into
   order and return a single square set of pixels."
  [squares]
  (let [subsquare-size (size (first squares))
        size (size squares)
        chunks (->> squares
                    (apply concat)
                    (partition (* size subsquare-size subsquare-size))
                    (map #(partition subsquare-size %)))]
    (flatten (map #(de-interleave subsquare-size size %) chunks))))

(defn step
  "Give the rulebook and current pixels, generate the next iteration
   of pixels, by first cutting the pixels into subsquares,
   updating each subsquare given the replacement rules, and then
   recombining into a unified set of pixels."
  [rules pixels]
  (->> pixels
       subsquares
       (map rules)
       recombine))

(defn pixels-on-at-n
  "Given a set of rules and the number of iterations to run, return
   the number of pixels that are `on` after that number of iterations."
  [rules n]
  (let [rulebook (full-rulebook rules)]
    (->> start
         (iterate #(step rulebook %))
         (drop n)
         first
         (filter pos?)
         count)))

;; Puzzle solutions
(defn part1
  "How many pixels stay on after 5 iterations?"
  [input]
  (pixels-on-at-n input 5))

(defn part2
  "How many pixels stay on after 18 iterations?"
  [input]
  (pixels-on-at-n input 18))