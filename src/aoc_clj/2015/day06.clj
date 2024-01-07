(ns aoc-clj.2015.day06
  "Solution to https://adventofcode.com/2015/day/6")

;; Constants
(def init-grid
  "A 1000 by 1000 vec-of-vecs, with all values set to 0"
  (vec (repeat 1000 (vec (repeat 1000 0)))))

;; Input parsing
(def pattern #"(turn on|turn off|toggle) (\d+),(\d+) through (\d+),(\d+)")

(defn parse-line
  [line]
  (let [[a b c d e] (rest (first (re-seq pattern line)))]
    {:cmd ({"turn on" :on "turn off" :off "toggle" :tog} a)
     :start [(read-string b) (read-string c)]
     :end   [(read-string d) (read-string e)]}))

(defn parse
  [input]
  (map parse-line input))

;; Puzzle logic
(defn part1-update
  "Returns the next light val given the cmd and current light val in part1"
  [cmd val]
  (case cmd
    :on  1
    :off 0
    :tog (bit-xor val 1)))

(defn part2-update
  "Returns the next light val given the cmd and current light val in part2"
  [cmd val]
  (case cmd
    :on  (+ val 1)
    :off (if (> val 0) (dec val) 0)
    :tog (+ val 2)))

(defn submapv
  "Returns a vector consisting of the result of applying f to the elements
   of v from start (inclusive) to end (exclusive). Other elements of 
   v outside of that range are left untouched."
  [f start end v]
  (vec (for [idx (range (count v))]
         (if (<= start idx (dec end))
           (f (get v idx))
           (get v idx)))))

(defn update-grid
  "Given an instruction, update the grid with the new light values"
  [update-fn grid {:keys [cmd start end]}]
  (let [[sx sy] start
        [ex ey] end
        update-row (partial submapv (partial update-fn cmd) sx (inc ex))]
    (submapv update-row sy (inc ey) grid)))

(defn brightness
  "Total brightness (sume of all light values) across the entire grid"
  [grid]
  (reduce + (flatten grid)))

;; Puzzle solutions
(defn part1
  "How many lights are on using the interpretation of part1"
  [input]
  (let [update-fn (partial update-grid part1-update)]
    (brightness (reduce update-fn init-grid input))))

(defn part2
  "Total brightness of the lights using the interpretation in part2"
  [input]
  (let [update-fn (partial update-grid part2-update)]
    (brightness (reduce update-fn init-grid input))))