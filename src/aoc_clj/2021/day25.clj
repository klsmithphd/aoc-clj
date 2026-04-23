(ns aoc-clj.2021.day25
  "Solution to https://adventofcode.com/2021/day/25"
  (:require [aoc-clj.grid.interface :as mapgrid]))

(def charmap {\v :down \> :right \. :open})

(defn parse
  [input]
  (mapgrid/ascii->MapGrid2D charmap input))

(defn available?
  [grid-map pos]
  (= :open (get grid-map pos)))

(defn right-from
  [{:keys [width]} [row col]]
  (if (= (inc col) width)
    [row 0]
    [row (inc col)]))

(defn down-from
  [{:keys [height]} [row col]]
  (if (= (inc row) height)
    [0 col]
    [(inc row) col]))

(defn move-dir
  [label direction {:keys [grid-map] :as state}]
  (let [locs        (map first (filter #(= label (val %)) grid-map))
        moveto-locs (map (partial direction state) locs)
        moves      (->> (zipmap locs moveto-locs)
                        (filter #(available? grid-map (val %)))
                        (into {}))]
    (update state :grid-map merge
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
