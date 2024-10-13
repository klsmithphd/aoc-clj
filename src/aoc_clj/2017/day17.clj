(ns aoc-clj.2017.day17
  "Solution to https://adventofcode.com/2017/day/17"
  (:require [aoc-clj.utils.core :as u]))

;; Constants
(def part1-limit 2017)
(def part2-limit 50000000)

;; Input parsing
(def parse (comp read-string first))

;; Puzzle logic
(defn next-pos
  "Given a step size `step`, the last position `pos`, and the last `num`
   inserted, returns the position that `num` will occupy and the next num
   to be added in a subsequent iteration."
  [step-size [pos num]]
  ;; Because the buffer is circular, take the current position, add
  ;; the step size, and then mod it by the size of the buffer to compute
  ;; the new position. 
  ;; The buffer size is always one more than the number being inserted. 
  ;; For example, at start, num = 0, and the buffer size is 1.
  [(inc (mod (+ pos step-size) (inc num))) (inc num)])

(defn spin-step
  "Evolves the state one iteration, returning a new circular buffer,
   and vec of the newly inserted position and newly inserted value."
  [step-size [buffer state]]
  (let [[nxt-pos nxt-num :as new-state] (next-pos step-size state)]
    [(u/vec-insert buffer nxt-pos nxt-num) new-state]))

(defn final-buffer
  "Returns the circular buffer after allowing `n` steps to evolve."
  [step-size n]
  (let [step (partial spin-step step-size)]
    (->> [[0] [0 0]]
         (iterate step)
         (drop n)
         ffirst)))

(defn val-after-2017
  "After letting the spinlock insert 2017 values into the circular buffer,
   return the value sequentially after 2017 in the buffer."
  [step-size]
  (let [buff (final-buffer step-size part1-limit)
        idx  (u/index-of (u/equals? part1-limit) buff)]
    (buff (inc idx))))

(defn val-after-zero
  "For a large number of steps `n`, return the value in the circular
   buffer sequentially after the value zero, which will always be at index 0."
  [step-size n]
  (let [step (partial next-pos step-size)]
    (->> [0 0]
         (iterate step)
         ;; Elements to the right of zero will have index = 1
         (filter #(= 1 (first %)))
         ;; Stop iterating once we've gone past the limit we're seeking
         (take-while #(<= (second %) n))
         ;; The last element will have the element that was last
         ;; inserted into the buffer
         last
         ;; The number in that position is the second in the vec
         second)))

;; Puzzle solutions
(defn part1
  "What is the value after 2017 in your completed circular buffer?"
  [input]
  (val-after-2017 input))

(defn part2
  "What is the value after 0 the moment 50000000 is inserted?"
  [input]
  (val-after-zero input part2-limit))