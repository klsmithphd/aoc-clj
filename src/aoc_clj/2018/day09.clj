(ns aoc-clj.2018.day09
  "Solution to https://adventofcode.com/2018/day/9"
  (:require
   [aoc-clj.utils.core :as u]))

;; Input parsing
(defn parse
  [input]
  (mapv read-string (re-seq #"\d+" (first input))))

;; Puzzle logic
(defn init-state
  [players]
  {:players (zipmap (range players) (repeat 0))
   :player  0
   :marbles [0]
   :marble  1
   :pos     0})

(defn insert-marble
  "Add the next marble into the circle, returning the new game state"
  [{:keys [players marbles pos marble player] :as state}]
  (if (zero? (mod marble 23))
    ;; Special rules if marble is a multiple of 23
    (let [remove-pos (mod (- pos 7) (count marbles))]
      (-> state
          (update-in [:players player] + marble (get marbles remove-pos))
          (update    :marbles u/vec-remove remove-pos)
          (assoc     :player (mod (inc player) (count players)))
          (assoc     :pos remove-pos)
          (update    :marble inc)))
    ;; Normal rules
    (let [add-pos (inc (mod (inc pos) (count marbles)))]
      (-> state
          (update :marbles u/vec-insert add-pos marble)
          (assoc  :player (mod (inc player) (count players)))
          (assoc  :pos add-pos)
          (update :marble inc)))))

(defn play
  "Play the game with the provided number of players and the final
   marble to play"
  [[players last-marble]]
  (letfn [(not-done? [{:keys [marble]}]
            (<= marble last-marble))]
    (->> (init-state players)
         (iterate insert-marble)
         (drop-while not-done?)
         first)))

(defn high-score
  "Play the game with the provided number of players and final marble,
   returning the high score"
  [input]
  (->> (play input)
       :players
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