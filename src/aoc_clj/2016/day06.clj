(ns aoc-clj.2016.day06
  "Solution to https://adventofcode.com/2016/day/6")

(def parse identity)

(defn frequent-chars
  [max-or-min-f input]
  (let [size (count input)]
    (->> (apply interleave input)
         (partition size)
         (map frequencies)
         (map (partial apply max-or-min-f val))
         (map first)
         (apply str))))

(def most-frequent-chars (partial frequent-chars max-key))
(def least-frequent-chars (partial frequent-chars min-key))

(defn day06-part1-soln
  [input]
  (most-frequent-chars input))

(defn day06-part2-soln
  [input]
  (least-frequent-chars input))