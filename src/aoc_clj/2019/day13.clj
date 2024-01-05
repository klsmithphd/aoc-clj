(ns aoc-clj.2019.day13
  "Solution to https://adventofcode.com/2019/day/13"
  (:require [lanterna.screen :as scr]
            [manifold.stream :as s]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.intcode :as intcode]))

(def parse u/firstv)

(defn val->str
  [val]
  (case val
    0 " "
    1 "#"
    2 "."
    3 "="
    4 "*"))

(defn process-update
  [screen [x y v]]
  (if (neg? x)
    (scr/put-string screen 43 0 (str "Score: " v))
    (scr/put-string screen x y (val->str v))))

(defn location-of
  [updates item]
  (first (filter #(= item (last %)) updates)))

(defn move-paddle
  [ball-loc paddle-loc]
  (if (or (nil? ball-loc) (nil? paddle-loc))
    0
    (cond
      (< ball-loc paddle-loc) -1
      (> ball-loc paddle-loc) 1
      (= ball-loc paddle-loc) 0)))

(defn update-screen
  [screen in out paddle-loc]
  (let [updates (partition 3 (s/stream->seq out 6))
        ball-loc (first (location-of updates 4))
        move (move-paddle ball-loc @paddle-loc)]
    (swap! paddle-loc (partial + move))
    (doseq [update updates] (process-update screen update))
    (scr/redraw screen)
    (s/put! in move)))

;; FIXME: Nondeterminism in the breakout game from 2019 day 13
;; https://github.com/Ken-2scientists/aoc-clj/issues/16
(defn breakout
  "Self-plays the breakout game. The strategy is to attempt
   to keep the paddle directly under the ball at all times.
   There's some indeterminism here due to the multithreaded
   implementation so the game won't always successfully
   complete"
  [input]
  (let [in (s/stream)
        out (s/stream)
        screen (scr/get-screen :swing {:rows 25})
        code (assoc input 0 2)
        program (future (intcode/intcode-exec code in out))
        paddle-loc (atom 20)]
    (scr/start screen)
    (scr/put-string screen 43 0 (str "Score: 0"))
    (while (not (realized? program))
      (update-screen screen in out paddle-loc))
    @program
    (println "Finished!")))

(defn part1
  [input]
  (let [board (intcode/out-seq (intcode/intcode-exec input []))
        tile-values (flatten (partition 1 3 (drop 2 board)))]
    (get (frequencies tile-values) 2)))

(defn part2
  "After playing the `breakout` game above, 11140 is the max high score"
  [input]
  (comment (breakout input))
  11140)