(ns aoc-clj.2021.day25
  "Solution to https://adventofcode.com/2021/day/25"
  (:require [aoc-clj.utils.grid.mapgrid :as mapgrid]))

(def charmap {\v :down \> :right \. :open})

(defn parse
  [input]
  (mapgrid/ascii->MapGrid2D charmap input :down true))

(defn available?
  [grid pos]
  (= :open (get grid pos)))

(defn right-from
  [{:keys [width]} [x y]]
  (if (= (inc x) width)
    [0 y]
    [(inc x) y]))

(defn down-from
  [{:keys [height]} [x y]]
  (if (= (inc y) height)
    [x 0]
    [x (inc y)]))

(defn move-dir
  [label direction {:keys [grid] :as state}]
  (let [locs        (map first (filter #(= label (val %)) grid))
        moveto-locs (map (partial direction state) locs)
        moves      (->> (zipmap locs moveto-locs)
                        (filter #(available? grid (val %)))
                        (into {}))]
    (update state :grid merge
            (zipmap (keys moves) (repeat :open))
            (zipmap (vals moves) (repeat label)))))

(defn step
  [state]
  (->> state
       (move-dir :right right-from)
       (move-dir :down down-from)))

(defn evolve-until-stop
  [input]
  (loop [last-state input curr-state (step input) steps 1]
    (if (= last-state curr-state)
      [steps curr-state]
      (recur curr-state (step curr-state) (inc steps)))))

(defn part1
  [input]
  (first (evolve-until-stop input)))
