(ns aoc-clj.2016.day02
  "Solution to https://adventofcode.com/2016/day/2")

(def parse identity)

(def square-keypad
  {[-1 -1] 1  [0 -1] 2  [1 -1] 3
   [-1  0] 4  [0  0] 5  [1  0] 6
   [-1  1] 7  [0  1] 8  [1  1] 9})

(def diagonal-keypad
  {[2 -2] 1
   [1 -1] 2  [2 -1] 3  [3 -1] 4
   [0  0] 5  [1  0] 6  [2  0] 7  [3  0] 8  [4  0] 9
   [1  1] "A"  [2  1] "B"  [3  1] "C"
   [2 2] "D"})

(defn intended-step
  [[x y] cmd]
  (case cmd
    \U [x (dec y)]
    \D [x (inc y)]
    \R [(inc x) y]
    \L [(dec x) y]))

(defn step
  [keypad pos cmd]
  (let [goto (intended-step pos cmd)]
    (if (keypad goto)
      goto
      pos)))

(defn move-digit
  [keypad start cmds]
  (reduce (partial step keypad) start cmds))

(defn all-digits
  [keypad cmds]
  (reductions (partial move-digit keypad) [0 0] cmds))

(defn bathroom-code
  [keypad cmds]
  (->> cmds (all-digits keypad) rest (map keypad) (apply str)))

(def square-bathroom-code   (partial bathroom-code square-keypad))
(def diagonal-bathroom-code (partial bathroom-code diagonal-keypad))

(defn part1
  [input]
  (square-bathroom-code input))

(defn part2
  [input]
  (diagonal-bathroom-code input))