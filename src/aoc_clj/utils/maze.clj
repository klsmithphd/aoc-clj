(ns aoc-clj.utils.maze
  (:require [aoc-clj.utils.graph :as graph :refer [Graph ->MapGraph]]
            [aoc-clj.utils.grid :as grid]
            [aoc-clj.utils.core :as u]))

(defn all-open
  [open? maze]
  (map first (filter #(open? (val %)) maze)))

(defrecord Maze [maze open?]
  Graph
  (vertices
    [_]
    (all-open open? maze))

  (edges
    [_ v]
    (all-open open? (grid/neighbors-2d maze v)))

  (distance
    [_ _ _]
    1)

  (without-vertex
    [_ v]
    (->Maze (assoc maze v :wall) open?)))

(defn Maze->Graph
  [maze]
  (->MapGraph (graph/adjacencies maze)))

(defn follow-left-wall
  "Pick the next relative direction that isn't known to be a wall, 
   favoring following the left wall"
  [rel-neighbors]
  (->> [:left :forward :right :backward]
       (filter #(not= :wall (rel-neighbors %)))
       first))

(defn next-move-attempt
  "Next move to attempt given a (partially) known `maze` state (a map
   of locations and known values), at the current position `pos`, and
   facing current direction `dir` (one of :n :e :s :w)"
  [{:keys [maze pos dir]}]
  (let [rel-move (grid/relative->cardinal dir)]
    (->> (grid/rel-neighbors maze pos dir)
         follow-left-wall
         rel-move)))

(defn update-mazemap
  "Given the result provided from the probe, update our
   knowledge of the map of the maze and our knowledge of
   the probe's position/direction"
  [{:keys [maze pos dir wall?] :as state} result]
  (let [tested-pos (grid/neighbor-pos pos dir)]
    (merge
     state
     {:maze (assoc maze tested-pos result)}
     (if (wall? result)
       {:dir (get-in grid/relative->cardinal [dir :right])}
       {:pos tested-pos}))))

(defn maze-step
  "Attempt to take a step and based on what the probe reports back,
   update the map of the maze"
  [probe state]
  (let [newdir (next-move-attempt state)
        result (probe newdir state)]
    (update-mazemap (assoc state :dir newdir) result)))

(defn map-maze
  "Given a stateful oracle function `probe` that will test
   a given direction and report back what it found, construct
   a map of a maze"
  [probe wall?]
  (loop [state {:maze {[0,0] :open} :pos [0,0] :dir :n :wall? wall?}]
      ;; Stop when we return back to the origin and we 
      ;; know all of the adjacent values
    (if (and (= [0 0] (state :pos))
             (= 4 (count (grid/neighbors-2d (state :maze) (state :pos)))))
      (->Maze (:maze state) (complement wall?))
      (recur (maze-step probe state)))))

(defn find-target
  "Find the coordinate of the point in the maze that has value `target`"
  [maze target]
  (ffirst (filter #(= target (val %)) (:maze maze))))

(defn spread-to-adjacent
  [maze [x y]]
  (let [thens (grid/neighbors-2d maze [x y])
        to-add (filter #(= :open (val %)) thens)]
    (keys to-add)))

(defn flood-fill
  [maze start]
  (loop [newmaze (:maze maze) last-added [start] count 0]
    (if (= 0 (u/count-if newmaze #(= :open (val %))))
      count
      (let [changes (mapcat (partial spread-to-adjacent newmaze) last-added)
            updates (merge newmaze (zipmap changes (repeat :oxygen)))]
        (recur updates changes (inc count))))))