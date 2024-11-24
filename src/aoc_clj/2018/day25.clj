(ns aoc-clj.2018.day25
  "Solution to https://adventofcode.com/2018/day/25"
  (:require [clojure.math.combinatorics :as combo]
            [clojure.set :as set]
            [aoc-clj.utils.vectors :as vec]
            [aoc-clj.utils.core :as u]))

;; Input parsing
(defn parse-line
  [line]
  (mapv read-string (re-seq #"-?\d+" line)))

(defn parse
  [input]
  (map parse-line input))

;; Puzzle logic
(defn close-enough?
  "Two points are close enough if their Manhattan distance is 3 or less"
  [[a b]]
  (<= (vec/manhattan a b) 3))

(defn can-merge?
  "Two constellations can be merged into one if *any* of their member points
   are close enough to each other"
  [const-a const-b]
  (->> (combo/cartesian-product const-a const-b)
       (some close-enough?)
       boolean))

(defn merge-pass
  "A single merge pass takes a collection of constellations
   (represented as sets of points), and attempts to merge the ones
   that are within range of each other"
  [constellations]
  (loop [const  (first constellations)
         others (rest constellations)
         new-consts []]
    (if (empty? others)
      new-consts
      (let [;; Mergable are the other constellations within range of const
            mergable   (filter #(can-merge? const %) others)
            ;; The new-others take out all the constellations we can
            ;; merge with the one currently being examined
            new-others (remove (set mergable) others)
            ;; Combine all the mergeable points together
            merged     (apply set/union const mergable)]
        (recur (first new-others)
               new-others
               (conj new-consts merged))))))

(defn coll-as-sets
  "Convert each element of coll into a set of that one element"
  [coll]
  (map #(conj #{} %) coll))

(defn constellations
  "Given the collection of points, returns the constellations
   (sets of points) that can be formed"
  [points]
  ;; Initially, treat each point as being in a constellation by itself
  (->> (coll-as-sets points)
       ;; Iteratively collapse constellations until no more changes 
       (u/converge merge-pass)
       ;; Return the final configuration of constellations
       last))

(defn constellation-count
  "Returns the number of constellations that can be formed from the points"
  [points]
  (count (constellations points)))

;; Puzzle solutions
(defn part1
  "How many constellations are formed by the fixed points in spacetime?"
  [input]
  (constellation-count input))