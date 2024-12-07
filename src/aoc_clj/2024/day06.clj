(ns aoc-clj.2024.day06
  "Solution to https://adventofcode.com/2024/day/6"
  (:require [aoc-clj.utils.grid :as grid :refer [value]]
            [aoc-clj.utils.grid.mapgrid :as mg]))

;; Input parsing
(def charmap {\. nil \# :wall \^ :guard})

(defn parse
  [input]
  (mg/ascii->MapGrid2D charmap input))

;; Puzzle logic
(defn guard-start
  [{:keys [grid]}]
  {:heading :n
   :pos (ffirst (filter #(= :guard (val %)) grid))
   :loop? false
   :seen #{}})

(defn next-move
  [grid {:keys [seen] :as guard}]
  (let [ahead (grid/forward guard 1)
        new-guard (if (= :wall (value grid (:pos ahead)))
                    (next-move grid (grid/turn guard :right))
                    ahead)
        loc   (select-keys new-guard [:pos :heading])]
    (if (seen loc)
      (do
        (println "We've got a loop!")
        (assoc new-guard :loop? true))
      (update new-guard :seen conj loc))))

;; (defn take-until
;;   [pred coll]
;;   (let [foo (partition-by pred coll)]
;;     (into (vec (first foo)) (take 1 (second foo)))))

(defn guard-path
  [grid]
  (->> (guard-start grid)
       (iterate #(next-move grid %))
       (take-while (complement :loop?))
       (take-while #(grid/in-grid? grid (:pos %)))))

(defn with-obstruction
  [grid pos]
  (assoc-in grid [:grid pos] :wall))

(defn loop?
  [path]
  (= (:pos (first path))
     (:pos (last path))))

(defn looping-guard-paths
  [grid]
  (let [start #{(:pos (guard-start grid))}
        orig-path (->> (guard-path grid)
                       (map :pos)
                       rest
                       (remove start))]
    (->> orig-path
         (map #(with-obstruction grid %))
         (map guard-path)
         (filter loop?)
         count)))

(defn distinct-guard-positions
  [grid]
  (count (set (map :pos (guard-path grid)))))

;; Puzzle solutions
(defn part1
  [input]
  (distinct-guard-positions input))