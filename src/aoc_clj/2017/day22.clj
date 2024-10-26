(ns aoc-clj.2017.day22
  "Solution to https://adventofcode.com/2017/day/22")

;; Input parsing
(defn parse
  [input]
  (let [size (count input)
        dim  (quot size 2)]
    (->>
     (zipmap (for [y (range dim (dec (- dim)) -1)
                   x (range (- dim) (inc dim))]
               [x y])
             (apply concat input))
     (filter #(= \# (val %)))
     keys
     set)))