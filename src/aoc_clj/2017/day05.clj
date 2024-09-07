(ns aoc-clj.2017.day05
  "Solution to https://adventofcode.com/2017/day/5")

;; Input parsing
(defn parse
  [input]
  (mapv read-string input))

;; Puzzle logic
(defn step
  [{:keys [jumps idx] :as state}]
  (-> state
      (update :idx + (get jumps idx))
      (update-in [:jumps idx] inc)
      (update :steps inc)))

(defn not-escaped?
  [{:keys [jumps idx]}]
  (<= 0 idx (dec (count jumps))))

(defn steps-to-escape
  [jumps]
  (->> (iterate step {:jumps jumps :idx 0 :steps 0})
       (drop-while not-escaped?)
       first
       :steps))


;; Puzzle solutions
(defn part1
  [input]
  (steps-to-escape input))
