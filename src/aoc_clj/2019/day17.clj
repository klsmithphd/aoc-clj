(ns aoc-clj.2019.day17
  "Solution to https://adventofcode.com/2019/day/17"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.grid :as grid]
            [aoc-clj.utils.intcode :as intcode]
            [aoc-clj.utils.grid.mapgrid :as mapgrid]))

(def parse u/firstv)

(def scaffold-mapping
  {\. :space
   \# :scaffold
   \^ :robot-up
   \> :robot-right
   \v :robot-down
   \< :robot-left
   \X :tumbling-robot})

(defn scaffold-map
  [ascii]
  (let [lines (str/split (str/join (map char ascii)) #"\n")]
    (:grid (mapgrid/ascii->MapGrid2D scaffold-mapping lines :down true))))

(defn day17-map
  [input]
  (->> (intcode/intcode-exec input)
       intcode/out-seq
       scaffold-map))

(defn intersection?
  [space pos]
  (if (= :scaffold (space pos))
    (every? #(= :scaffold %) (vals (grid/neighbors-2d space pos)))
    false))

(defn intersections
  [space]
  (filter (partial intersection? space) (keys space)))

(defn alignment-sum
  [intersections]
  (reduce + (map #(apply * %) intersections)))

(def path
  (str
   "A,B,B,C,C,A,B,B,C,A\n"
   "R,4,R,12,R,10,L,12\n"
   "L,12,R,4,R,12\n"
   "L,12,L,8,R,10\n"
   "n\n"))

(defn part1
  [input]
  (->> (day17-map input)
       intersections
       alignment-sum))

(defn part2
  [input]
  (let [code (assoc input 0 2)]
    (->> path
         (map (comp int char))
         (intcode/intcode-exec code)
         intcode/last-out)))
