(ns aoc-clj.2017.day12
  "Solution to https://adventofcode.com/2017/day/12")

;; Input parsing
(defn parse-line
  [line]
  (let [[id & others] (map read-string (re-seq #"\d+" line))]
    [id others]))

(defn parse
  [input]
  (into {} (map parse-line input)))

;; Puzzle logic
(defn group-size
  "Given a graph and the id of an element, determine how many other
   nodes are reachable."
  [graph id]
  (loop [group #{id} front (graph id)]
    (if-not (seq front)
      (count group)
      (recur (into group front)
             (remove group (mapcat graph front))))))

;; Puzzle solutions
(defn part1
  "How many programs are in the group that contains program id 0?"
  [input]
  (group-size input 0))

