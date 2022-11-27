(ns aoc-clj.2016.day13
  (:require [aoc-clj.utils.binary :as b]))

(defn wall-calc
  [fav [x y]]
  (+ fav (* x x) (* 3 x) (* 2 x y) y (* y y)))

(defn space
  [fav pos]
  (let [ones (-> (wall-calc fav pos)
                 b/int->bitstr
                 frequencies
                 (get \1))]
    (if (even? ones)
      :open
      :wall)))