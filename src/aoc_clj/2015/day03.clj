(ns aoc-clj.2015.day03
  "Solution to https://adventofcode.com/2015/day/3")

(def parse first)

(def dir-map
  {\^ [0 -1]
   \v [0 1]
   \> [1 0]
   \< [-1 0]})

(defn vec-add [a b]
  (map + a b))

(defn houses-visited
  [input]
  (->> (map dir-map input)
       (reductions vec-add [0 0])
       set
       count))

(defn split-houses-visited
  [input]
  (let [steps (map dir-map input)
        steps1 (take-nth 2 steps)
        steps2 (take-nth 2 (rest steps))
        houses1 (reductions vec-add [0 0] steps1)
        houses2 (reductions vec-add [0 0] steps2)]
    (->> (concat houses1 houses2)
         set
         count)))

(defn part1
  [input]
  (houses-visited input))

(defn part2
  [input]
  (split-houses-visited input))