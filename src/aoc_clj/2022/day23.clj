(ns aoc-clj.2022.day23
  "Solution to https://adventofcode.com/2022/day/23"
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.grid :as grid]
            [aoc-clj.utils.grid.mapgrid :as mapgrid]))

(def charmap {\. :open \# :elf})
(def dir-order [:n :s :w :e])

(defn elf? [x] (= :elf x))

(defn parse
  [input]
  (->> (mapgrid/ascii->MapGrid2D charmap input)
       :grid
       (filter #(elf? (val %)))
       (map first)
       (into #{})))

(def day23-input (parse (u/puzzle-input "2022/day23-input.txt")))

(defn done?
  [elves pos]
  (not-any? elves (grid/adj-coords-2d pos :include-diagonals true)))

(defn open-dir
  [elves [x y] dir]
  (let [opts (case dir
               :n [[(dec x) (inc y)] [x (inc y)] [(inc x) (inc y)]]
               :s [[(dec x) (dec y)] [x (dec y)] [(inc x) (dec y)]]
               :w [[(dec x) (dec y)] [(dec x) y] [(dec x) (inc y)]]
               :e [[(inc x) (dec y)] [(inc x) y] [(inc x) (inc y)]])]
    (when (not-any? elves opts)
      (mapv + [x y] (grid/cardinal->offset dir)))))

(defn propose-move
  [elves dirs pos]
  (when-let [new-pos (->> (map #(open-dir elves pos %) dirs)
                          (remove nil?)
                          first)]
    [pos new-pos]))

(defn proposed-moves
  [elves dirs]
  (let [proposer (partial propose-move elves dirs)
        elves-to-move (remove #(done? elves %) elves)]
    (remove #(nil? (second %)) (map proposer elves-to-move))))

(defn duplicates
  [moves]
  (->> (map second moves)
       frequencies
       (filter #(>= (second %) 2))
       (map first)
       set))

(defn deduped-proposals
  [proposals]
  (let [duplicate-locs (duplicates proposals)]
    (remove #(duplicate-locs (second %)) proposals)))

(defn apply-proposal
  [elves [old new]]
  (-> (disj elves old)
      (conj new)))

(defn apply-proposals
  [elves proposals]
  (reduce apply-proposal elves proposals))

(defn round
  [[elves dirs]]
  [(->> (proposed-moves elves dirs)
        deduped-proposals
        (apply-proposals elves))
   (u/rotate 1 dirs)])

(defn area
  [elves]
  (let [[minx maxx] ((juxt #(apply min %) #(apply max %)) (map first elves))
        [miny maxy] ((juxt #(apply min %) #(apply max %)) (map second elves))]
    (* (inc (- maxx minx)) (inc (- maxy miny)))))

(defn empty-tiles-after-ten-rounds
  "Simulate the Elves' process and find the smallest rectangle that 
   contains the Elves after 10 rounds. How many empty ground tiles does that 
   rectangle contain?"
  [elves]
  (let [final (first (nth (iterate round [elves dir-order]) 10))]
    (- (area final) (count final))))

(defn rounds-until-static
  "Figure out where the Elves need to go. What is the number of the first 
   round where no Elf moves?"
  [elves]
  (->> (iterate round [elves dir-order])
       (map first)
       (partition 2 1)
       (take-while #(not= (first %) (second %)))
       count
       inc))

(defn day23-part1-soln
  []
  (empty-tiles-after-ten-rounds day23-input))

(defn day23-part2-soln
  []
  (rounds-until-static day23-input))