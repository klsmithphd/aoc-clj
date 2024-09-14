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
(defn group
  "Given a graph and the id of a node, return the set of all other
   reachable nodes"
  [graph id]
  (loop [group #{id} front (graph id)]
    (if-not (seq front)
      group
      (recur (into group front)
             (remove group (mapcat graph front))))))

(defn group-size
  "Given a graph and the id of a node, return the count of all other 
   reachable nodes"
  [graph id]
  (count (group graph id)))

(defn all-groups
  "Returns all the groups (mutually reachable nodes) within the graph"
  [graph]
  (loop [groups #{} yet-ungrouped (keys graph)]
    (if-not (seq yet-ungrouped)
      groups
      (let [new-group (group graph (first yet-ungrouped))]
        (recur (conj groups new-group)
               (remove new-group yet-ungrouped))))))

(defn all-groups-count
  "Counts the number of groups (mutually reachable nodes) within the graph"
  [graph]
  (count (all-groups graph)))

;; Puzzle solutions
(defn part1
  "How many programs are in the group that contains program id 0?"
  [input]
  (group-size input 0))

(defn part2
  "How many groups are there in total?"
  [input]
  (all-groups-count input))