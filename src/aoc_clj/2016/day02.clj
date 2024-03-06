(ns aoc-clj.2016.day02
  "Solution to https://adventofcode.com/2016/day/2")

;; Constants
(def first-pos [0 0])

(def square-keypad
  {[-1 -1] 1  [0 -1] 2  [1 -1] 3
   [-1  0] 4  [0  0] 5  [1  0] 6
   [-1  1] 7  [0  1] 8  [1  1] 9})

(def diamond-keypad
  {[2 -2] 1
   [1 -1] 2  [2 -1] 3  [3 -1] 4
   [0  0] 5  [1  0] 6  [2  0] 7  [3  0] 8  [4  0] 9
   [1  1] "A"  [2  1] "B"  [3  1] "C"
   [2 2] "D"})

;; Input parsing
(def parse identity)

;; Puzzle logic
(defn intended-step
  "Determine the grid position that `cmd` would have you try to move to next"
  [[x y] cmd]
  (case cmd
    \U [x (dec y)]
    \D [x (inc y)]
    \R [(inc x) y]
    \L [(dec x) y]))

(defn move
  "Apply the given `cmd` to move (or not move if impossible) to the next
   position on the keypad"
  [keypad pos cmd]
  (let [goto (intended-step pos cmd)]
    (if (keypad goto)
      goto
      pos)))

(defn digit-pos
  "Determine where you end up following the instructions (`cmds`) for a single
   digit, starting at `start`"
  [keypad start cmds]
  (reduce (partial move keypad) start cmds))

(defn all-digits-pos
  "Determine the grid positions of all digits, given the sequential cmds
   for each one"
  [keypad cmds]
  (reductions (partial digit-pos keypad) first-pos cmds))

(defn bathroom-code
  "Compute the bathroom code for the given `keyboard` layout and set of 
   `cmds`"
  [keypad cmds]
  (->> cmds (all-digits-pos keypad) rest (map keypad) (apply str)))

(def square-bathroom-code  (partial bathroom-code square-keypad))
(def diamond-bathroom-code (partial bathroom-code diamond-keypad))

;; Puzzle solutions
(defn part1
  "What's the bathroom code given the provided instructions (square keypad)"
  [input]
  (square-bathroom-code input))

(defn part2
  "What's the bathroom code given the provided instructions (diamond keypad)"
  [input]
  (diamond-bathroom-code input))