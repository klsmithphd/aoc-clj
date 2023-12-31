(ns aoc-clj.2019.day15
  (:require [manifold.stream :as s]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.intcode :as intcode]
            [aoc-clj.utils.graph :as g]
            [aoc-clj.utils.maze :as maze]))

(def day15-input (u/firstv (u/puzzle-input "inputs/2019/day15-input.txt")))

(def dir->code
  {:n 1
   :s 2
   :w 3
   :e 4})

(def status
  {0 :wall
   1 :open
   2 :oxygen})

(defn droid-probe
  "Attempt to move in direction `dir` and report back what (if anything)
   was encountered"
  [in out dir _]
  (s/put! in (dir->code dir))
  (status @(s/take! out)))

(defn map-maze-with-droid
  [intcode]
  (let [in    (s/stream)
        out   (s/stream)
        probe (partial droid-probe in out)
        _ (future (intcode/intcode-exec intcode in out))]
    (maze/map-maze probe #(= :wall %))))

(def thismaze (map-maze-with-droid day15-input))

(defn day15-part1-soln
  []
  (let [start [0 0]
        finish (maze/find-target thismaze :oxygen)
        simplified-maze (-> thismaze maze/Maze->Graph (g/pruned #{start finish}))
        path (g/dijkstra simplified-maze start (u/equals? finish))]
    (g/path-distance simplified-maze path)))

(defn day15-part2-soln
  []
  (maze/flood-fill thismaze (maze/find-target thismaze :oxygen)))
