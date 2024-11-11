(ns aoc-clj.2018.day11
  "Solution to https://adventofcode.com/2018/day/11")

;; Constants
(def grid-size 300)
(def square-size 3)

;; Input parsing
(defn parse
  [input]
  (read-string (first input)))

;; Puzzle logic
(defn rack-id
  [[x _]]
  (+ x 10))

(defn hundreds
  [num]
  (mod (quot num 100) 10))

(defn power-level
  [serial [_ y :as cell]]
  (let [r-id (rack-id cell)]
    (-> r-id
        (* y)
        (+ serial)
        (* r-id)
        hundreds
        (- 5))))

(defn squares
  [[up-x up-y]]
  (for [y (range up-y (+ up-y square-size))
        x (range up-x (+ up-x square-size))]
    [x y]))

(defn total-power
  [serial upper]
  (->> (squares upper)
       (map #(power-level serial %))
       (reduce +)))

(defn highest-power-3x3-square
  [serial]
  (let [uppers (for [y (range 1 (dec grid-size))
                     x (range 1 (dec grid-size))]
                 [x y])]
    (apply max-key #(total-power serial %) uppers)))

;; Puzzle solution
(defn part1
  [input]
  (highest-power-3x3-square input))