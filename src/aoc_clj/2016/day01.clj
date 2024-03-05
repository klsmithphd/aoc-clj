(ns aoc-clj.2016.day01
  "Solution to https://adventofcode.com/2016/day/1"
  (:require [clojure.string :as str]
            [aoc-clj.utils.vectors :as v]
            [aoc-clj.utils.grid :as grid]))

;; Input parsing
(defn parse-cmd
  [cmd]
  (let [dir  ({"R" :right "L" :left} (subs cmd 0 1))
        dist (read-string (subs cmd 1))]
    {:dir dir :dist dist}))

(defn parse
  [input]
  (map parse-cmd (str/split (first input) #", ")))

;; Puzzle logic
(defn rotate
  [state dir]
  (let [rmap {:n :e :e :s :s :w :w :n}
        lmap {:n :w :w :s :s :e :e :n}]
    (case dir
      :right (update state :heading rmap)
      :left  (update state :heading lmap))))

(defn forward
  [{:keys [heading] :as state} dist]
  (case heading
    :n (update-in state [:pos 1] + dist)
    :s (update-in state [:pos 1] - dist)
    :e (update-in state [:pos 0] + dist)
    :w (update-in state [:pos 0] - dist)))

(defn step
  [state {:keys [dir dist]}]
  (-> state
      (rotate dir)
      (forward dist)))

(defn move
  [steps]
  (reduce step {:pos [0 0] :heading :n} steps))

(defn distance
  [steps]
  (-> steps move :pos (v/manhattan [0 0])))

(defn all-points
  [steps]
  (->> (reductions step {:pos [0 0] :heading :n} steps)
       (map :pos)
       (partition 2 1)
       (mapcat grid/interpolated)
       dedupe))

(defn first-duplicate
  [coll]
  (loop [seen #{} xs coll]
    (let [x (first xs)]
      (if (seen x)
        x
        (recur (conj seen x) (rest xs))))))

(defn distance-to-first-duplicate
  [steps]
  (->> steps all-points first-duplicate (v/manhattan [0 0])))

;; Puzzle solutions
(defn part1
  [input]
  (distance input))

(defn part2
  [input]
  (distance-to-first-duplicate input))