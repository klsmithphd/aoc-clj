(ns aoc-clj.2019.day12
  "Solution to https://adventofcode.com/2019/day/12"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.math :as math]))

(defn parse-coord
  [s]
  (->> (-> s
           (str/replace #"[^\d\-]" " ")
           str/trim
           (str/split #"\s+"))
       (map read-string)))

(defn parse
  [input]
  (map parse-coord input))

(defn rotations
  [coll]
  (take (count coll) (partition (count coll) 1 (cycle coll))))

(defn pos-compare
  [pos1 pos2]
  (cond
    (< pos1 pos2) 1
    (> pos1 pos2) -1
    :else 0))

(defn velocity-component
  [moon1 moon2]
  (map pos-compare moon1 moon2))

(defn velocity
  [moons]
  (let [velocity-changes (map (partial velocity-component (first moons)) (rest moons))]
    (map #(reduce + %) (apply mapv vector velocity-changes))))

(defn velocities
  [moons]
  (let [perms (rotations moons)]
    (map velocity perms)))

(defn add-deltas
  [old changes]
  (map #(map + %1 %2) old changes))

(defn next-step
  [[moons vels]]
  (let [new-vels (add-deltas vels (velocities moons))
        new-moons (add-deltas moons new-vels)]
    [new-moons new-vels]))

(def initial-velocity
  (repeat 4 (repeat 3 0)))

(defn simulate
  [moons]
  (iterate next-step [moons initial-velocity]))

(defn energy
  [moon]
  (reduce + (map #(Math/abs %) moon)))

(defn total-energy
  [[moons vels]]
  (let [potential-energies (map energy moons)
        kinetic-energies (map energy vels)]
    (reduce + (map * potential-energies kinetic-energies))))

(def initial-velocity-axis
  (repeat 4 (repeat 1 0)))

(defn simulate-axis
  [moons]
  (iterate next-step [moons initial-velocity-axis]))

(defn axis-period
  [axis moons]
  (let [axis-moons (case axis
                     :x (map (comp vector first) moons)
                     :y (map (comp vector second) moons)
                     :z (map (comp vector last) moons))]
    (inc (u/index-of zero? (rest (map total-energy (simulate-axis axis-moons)))))))

(defn recurrence-period
  [moons]
  (* 2 (apply math/lcm (pmap axis-period [:x :y :z] (repeat moons)))))

(defn part1
  [input]
  (total-energy (nth (simulate input) 1000)))

(defn part2
  [input]
  (recurrence-period input))