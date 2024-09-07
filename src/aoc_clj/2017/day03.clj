(ns aoc-clj.2017.day03
  "Solution to https://adventofcode.com/2017/day/3"
  (:require [clojure.math :as math]
            [aoc-clj.utils.vectors :as v]
            [aoc-clj.utils.grid :as grid]))

;; Constants
(def start-grid {[0 0] 1})

;; Input parsing
(def parse (comp read-string first))

;; Puzzle logic
(defn spiral-position
  "Finds the two-dimensional coordinates of numbers laid out in a spiral
   sequence, with 1 starting at (0,0) and subsequent numbers appearing
   following the pattern:
   ```
   17  16  15  14  13
   18   5   4   3  12
   19   6   1   2  11
   20   7   8   9  10
   21  22  23---> ...
   ```"
  [num]
  ;; The squares of odd integers follow the diagonal [0 0] [1 -1] [2 -2], etc
  ;; ring 0: 1-8    : 1^2 to 3^2-1  : edge 2  : 8 vals
  ;; ring 1: 9-24   : 3^2 to 5^2-1  : edge 4  : 16 vals
  ;; ring 2: 25-48  : 5^2 to 7^2-1  : edge 6  : 24 vals
  ;; ring 3: 49-80  : 7^2 to 9^2-1  : edge 8  : 32 vals
  ;; ring 4: 81-120 : 9^2 to 10^2-1 : edge 10 : 40 vals
  (let [sqrt (int (math/sqrt num))
        odd-sqrt (if (odd? sqrt) sqrt (dec sqrt))
        ring (quot odd-sqrt 2)
        edge (* 2 (inc ring))
        offset (- num (* odd-sqrt odd-sqrt))
        start  [ring (- ring)]]
    (if (zero? offset)
      start
      (let [side (quot offset edge)
            rem  (mod offset edge)]
        (case side
          0 (v/vec-add start [1 (- rem 1)])
          1 (v/vec-add start [(- 1 rem) (- edge 1)])
          2 (v/vec-add start [(- 1 edge) (- edge rem 1)])
          3 (v/vec-add start [(- (+ 1 rem) edge) -1])
          4 (v/vec-add start [1 -1]))))))

(defn distance
  "Finds the manhattan distance of a given number, laid out in the spiral
   pattern above, from the origin"
  [num]
  (->> (spiral-position num)
       (v/manhattan [0 0])))

(defn grid-fill
  "Adds a new grid value to the grid whose value is equal to the sum of a
   the diagonally adjacent values filled in so far."
  [grid pos]
  (let [new-val (->> (grid/neighbors-2d grid pos :include-diagonals true)
                     vals
                     (reduce +))]
    (assoc grid pos new-val)))

(defn first-value-bigger-than
  "Finds the first value written into the grid that's strictly larger
   than the provided value"
  [num]
  (->> (range)
       (drop 2)
       (map spiral-position)
       (reductions grid-fill start-grid)
       (drop-while #(<= (apply max (vals %)) num))
       first
       vals
       (apply max)))

;; Puzzle solutions
(defn part1
  "How many steps required to carry data from the identified square to the
   access port"
  [input]
  (distance input))

(defn part2
  "What is the first value written into the grid that's larger than the
   provided value?"
  [input]
  (first-value-bigger-than input))
