(ns aoc-clj.2015.day06
  "Solution to https://adventofcode.com/2015/day/6")

;; Constants
(def init-grid
  "A 1000 by 1000 vec-of-vecs, with all values set to 0"
  (vec (repeat 1000 (vec (repeat 1000 0)))))

;; Input parsing
(def pattern #"(turn on|turn off|toggle) (\d+),(\d+) through (\d+),(\d+)")
(def command {"turn on" :on
              "turn off" :off
              "toggle" :toggle})

(defn parse-line
  [line]
  (let [[a b c d e] (rest (first (re-seq pattern line)))]
    {:cmd (command a)
     :start [(read-string b) (read-string c)]
     :end   [(read-string d) (read-string e)]}))

(defn parse
  [input]
  (map parse-line input))

;; Puzzle logic
(defn rect-range
  "Given a start corner `[sx sy]` and an end corner `[ex ey]` return
   all values in the rectangular range defined by those corners"
  [[sx sy] [ex ey]]
  (for [y (range sy (inc ey))
        x (range sx (inc ex))]
    [y x]))

(defn off
  "Sets the value of the grid at the supplied position to 0"
  [grid pos]
  (assoc-in grid pos 0))

(defn on
  "Sets the value of the grid at the supplied position to 1"
  [grid pos]
  (assoc-in grid pos 1))

(defn toggle
  "Sets the value of the grid at the supplied position to its opposite.
   
   0 becomes 1, 1 becomes 0"
  [grid pos]
  (update-in grid pos {1 0 0 1}))

(defn dec-min-zero
  "Same as `dec`, except won't decrement a value below zero"
  [x]
  (if (zero? x) x (dec x)))

(defn off2
  "Decrease the brightness of the light by one to a minimum of zero"
  [grid pos]
  (update-in grid pos dec-min-zero))

(defn on2
  "Increase the brightness of the light by one"
  [grid pos]
  (update-in grid pos inc))

(defn toggle2
  "Increase the brightness of the light by two"
  [grid pos]
  (update-in grid pos (partial + 2)))

(def part1-commands
  "Mapping of instructions to update fns for part1"
  {:on on
   :off off
   :toggle toggle})

(def part2-commands
  "Mapping of instructions to update fns for part2"
  {:on on2
   :off off2
   :toggle toggle2})

(defn update-grid
  "Given an instruction, update the grid with the new light values"
  [cmds grid {:keys [cmd start end]}]
  (let [locs (rect-range start end)
        update-fn (cmds cmd)]
    (reduce update-fn grid locs)))

(defn brightness
  "Total brightness (sume of all light values) across the entire grid"
  [grid]
  (reduce + (flatten grid)))

;; Puzzle solutions
(defn part1
  "How many lights are on using the interpretation of part1"
  [input]
  (let [update-fn (partial update-grid part1-commands)]
    (brightness (reduce update-fn init-grid input))))

(defn part2
  "Total brightness of the lights using the interpretation in part2"
  [input]
  (let [update-fn (partial update-grid part2-commands)]
    (brightness (reduce update-fn init-grid input))))