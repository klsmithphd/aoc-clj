(ns aoc-clj.2017.day03
  "Solution to https://adventofcode.com/2017/day/2"
  (:require [clojure.math :as math]))

;; Input parsing
(def parse (comp read-string first))

;; Puzzle logic
(defn is-square?
  [num]
  (let [sqrt (int (math/sqrt num))]
    (= num (* sqrt sqrt))))

(is-square? 1)

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
  ;; ring 1: 2-9     : 1^2 + 1 to 3^2    up 1, left 2, down 2, right 2
  ;; ring 2: 10-25   : 3^2 + 1 to 5^2    up 3, left 4, down 4, right 4
  ;; ring 3: 26-49   : 5^2 + 1 to 7^2    up 5, left 6, down 6, right 6
  ;; ring 4: 50-81   : 7^2 + 1 to 9^2    up 7, left 8, down 8, right 8
  ;; ring 5: 82-121  : 9^2 + 1 to 10^2   up 9, left 10, down 10, right 10
  (if (is-square? num)
    (let [sqrt (int (math/sqrt num))]
      (if (odd? sqrt)
        [(quot sqrt 2)     (- (quot sqrt 2))]
        [(- 1 (quot sqrt 2)) (quot sqrt 2)]))
    num))




;; Puzzle solutions