(ns aoc-clj.2016.day01
  "Solution to https://adventofcode.com/2016/day/1"
  (:require [clojure.string :as str]
            [aoc-clj.utils.math :as math]))

(defn parse-cmd
  [cmd]
  (let [dir  (subs cmd 0 1)
        dist (read-string (subs cmd 1))]
    {:dir dir :dist dist}))

(defn parse
  [input]
  (map parse-cmd (str/split (first input) #", ")))

(defn rotate
  [heading dir]
  (let [rmap {:north :east :east :south :south :west :west :north}
        lmap {:north :west :west :south :south :east :east :north}]
    (case dir
      "R" (rmap heading)
      "L" (lmap heading))))

(defn step
  [{:keys [pos heading]} {:keys [dir dist]}]
  (let [new-heading (rotate heading dir)]
    {:heading new-heading
     :pos (case new-heading
            :north (update pos 1 + dist)
            :south (update pos 1 - dist)
            :east  (update pos 0 + dist)
            :west  (update pos 0 - dist))}))

(defn move
  [steps]
  (reduce step {:pos [0 0] :heading :north} steps))

(defn distance
  [steps]
  (-> steps move :pos (math/manhattan [0 0])))

(defn all-steps
  [{:keys [visited heading]} {:keys [dir dist]}]
  (let [new-heading (rotate heading dir)
        pos         (last visited)
        steps       (range 1 (inc dist))]
    {:heading new-heading
     :visited (into visited
                    (case new-heading
                      :north (map #(update pos 1 + %) steps)
                      :south (map #(update pos 1 - %) steps)
                      :east  (map #(update pos 0 + %) steps)
                      :west  (map #(update pos 0 - %) steps)))}))

(defn all-visited
  [steps]
  (reduce all-steps {:visited [[0 0]] :heading :north} steps))

(defn first-duplicate
  [coll]
  (loop [seen #{} xs coll]
    (let [x (first xs)]
      (if (seen x)
        x
        (recur (conj seen x) (rest xs))))))

(defn distance-to-first-duplicate
  [steps]
  (-> steps all-visited :visited first-duplicate (math/manhattan [0 0])))

(defn part1
  [input]
  (distance input))

(defn part2
  [input]
  (distance-to-first-duplicate input))