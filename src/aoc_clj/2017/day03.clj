(ns aoc-clj.2017.day03
  "Solution to https://adventofcode.com/2017/day/2"
  (:require [clojure.math :as math]
            [aoc-clj.utils.vectors :as v]))

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
  ;; ring 0: 1
  ;; ring 1: 2-9     : 1^2 + 1 to 3^2    up 2, left 2, down 2, right 2
  ;; ring 2: 10-25   : 3^2 + 1 to 5^2    up 4, left 4, down 4, right 4
  ;; ring 3: 26-49   : 5^2 + 1 to 7^2    up 6, left 6, down 6, right 6
  ;; ring 4: 50-81   : 7^2 + 1 to 9^2    up 8, left 8, down 8, right 8
  ;; ring 5: 82-121  : 9^2 + 1 to 10^2   up 10, left 10, down 10, right 10
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
  [num]
  (->> (spiral-position num)
       (v/manhattan [0 0])))


;; Puzzle solutions
(defn part1
  [input]
  (distance input))