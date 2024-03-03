(ns aoc-clj.utils.vectors
  "Utility functions for vector arithmetic"
  (:require [clojure.math :as m]))

(defn scalar-mult
  "Multiplies a vector `v` by a scalar"
  [v scale]
  (mapv #(* scale %) v))

(defn vec-sum
  "Computes the sum of any collection of vectors"
  [vectors]
  (apply (partial mapv +) vectors))

(defn vec-add
  "Computes the sum of two vectors"
  [a b]
  (vec-sum [a b]))

(defn manhattan
  "Computes the Manhattan distance (L1 norm) between two vectors"
  [v1 v2]
  (->> (map (comp abs -) v2 v1)
       (reduce +)))

(defn euclidean
  "Computes the Euclidean distance (L2 norm) between two vectors"
  [v1 v2]
  (->> (map #(m/pow (- %2 %1) 2) v1 v2)
       (reduce +)
       m/sqrt))

(defn l1-norm
  "Computes the L1 norm of a vector"
  [v]
  (reduce + (map abs v)))

(defn l2-norm
  "Computes the L2 norm of a vector"
  [v]
  (->> (map #(m/pow % 2) v)
       (reduce +)
       (m/sqrt)))