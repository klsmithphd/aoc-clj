(ns aoc-clj.2018.day09
  "Solution to https://adventofcode.com/2018/day/9"
  (:require [data.deque :as deque]))

;; Input parsing
(defn parse
  [input]
  (mapv read-string (re-seq #"\d+" (first input))))

;; Puzzle logic
(defn init-state
  "Constructs the initial state object for the game"
  [players]
  {:marbles (deque/deque 0)
   :scores  (zipmap (range players) (repeat 0))})

(defn rotate-left
  "Shifts the deque to the left (moving items from front to back)"
  [dq]
  (let [elem (deque/peek-first dq)]
    (-> dq
        deque/remove-first
        (deque/add-last elem))))

(defn rotate-right
  "Shifts the deque to the right (moving items from the back to the front)"
  [dq]
  (let [elem (deque/peek-last dq)]
    (-> dq
        (deque/remove-last)
        (deque/add-first elem))))

(def left2
  "Rotates the deque two moves to the left"
  (apply comp (repeat 2 rotate-left)))
(def right7
  "Rotates the deque seven moves to the right"
  (apply comp (repeat 7 rotate-right)))

(defn remove-seven-marbles-back
  "Removes the seventh marble back, adding it to the current player's score
   in addition to the current marble, and returns the new state."
  [{:keys [marbles scores] :as state} marble]
  (let [new-marbles (right7 marbles)
        player (mod marble (count scores))]
    (-> state
        (update-in [:scores player] + marble (deque/peek-first new-marbles))
        (assoc :marbles (deque/remove-first new-marbles)))))

(defn insert-marble-two-ahead
  "Shifts the deque two places to the left and adds the new marble to the
   front of the deque."
  [marbles marble]
  (deque/add-first (left2 marbles) marble))

(defn insert-marble
  "Inserts the next marble numbered `marble` into the circle."
  [state marble]
  (if (zero? (mod marble 23))
    (remove-seven-marbles-back state marble)
    (update state :marbles insert-marble-two-ahead marble)))

(defn play
  "Play the game with the provided number of players and the final
   marble to play"
  [[players last-marble]]
  (reduce insert-marble (init-state players) (range 1 (inc last-marble))))

(->> (play [9 23])
     :scores)

(defn high-score
  "Play the game with the provided number of players and final marble,
   returning the high score"
  [input]
  (->> (play input)
       :scores
       vals
       (apply max)))

;; Puzzle solutions
(defn part1
  "What is the winning Elf's score?"
  [input]
  (high-score input))

(defn part2
  "What is the winning Elf's score?"
  [[players last-marble]]
  (high-score [players (* 100 last-marble)]))