(ns aoc-clj.2022.day23
  "Solution to https://adventofcode.com/2022/day/23"
  (:require [aoc-clj.utils.core :as u]
            [aoc-clj.utils.grid :as grid]
            [aoc-clj.utils.grid.mapgrid :as mapgrid]))

;;;; Constants

(def charmap {\. :open \# :elf})
(def dir-order [:n :s :w :e])

;;;; Input parsing

(defn elf? [x] (= :elf x))

(defn parse
  [input]
  (->> (mapgrid/ascii->MapGrid2D charmap input)
       :grid
       (filter #(elf? (val %)))
       (map first)
       (into #{})))

(def day23-input (u/parse-puzzle-input parse 2022 23))

;;;; Puzzle logic

(defn done?
  "Elves that have no nearest neighbors are done"
  [elves pos]
  (not-any? elves (grid/adj-coords-2d pos :include-diagonals true)))

(defn open-dir
  "Check to see if there are any adjacent elves in the direction
   being checked"
  [elves [x y] dir]
  (let [opts (case dir
               ;; Check Nw, N, and NE
               :n [[(dec x) (inc y)] [x (inc y)] [(inc x) (inc y)]]
               ;; Check SW, S, and SE
               :s [[(dec x) (dec y)] [x (dec y)] [(inc x) (dec y)]]
               ;; Check SW, W, and NW
               :w [[(dec x) (dec y)] [(dec x) y] [(dec x) (inc y)]]
               ;; Check SE, E, and NE
               :e [[(inc x) (dec y)] [(inc x) y] [(inc x) (inc y)]])]
    (when (not-any? elves opts)
      (mapv + [x y] (grid/cardinal->offset dir)))))

(defn propose-move
  "For an elf at `pos` with all other elves at `elves`, and
   the recommended direction sequence to check given by `dirs`,
   propose the first available move option (if any) for the elf"
  [elves dirs pos]
  (when-let [new-pos (->> (map #(open-dir elves pos %) dirs)
                          (remove nil?)
                          first)]
    [pos new-pos]))

(defn proposed-moves
  "For all elves, propose the next possible location (if any)"
  [elves dirs]
  (let [proposer (partial propose-move elves dirs)
        elves-to-move (remove #(done? elves %) elves)]
    (remove #(nil? (second %)) (map proposer elves-to-move))))

(defn duplicates
  "Find the set of duplicated proposed new locations among all the proposals"
  [moves]
  (->> (map second moves)
       frequencies
       (filter #(>= (second %) 2))
       (map first)
       set))

(defn deduped-proposals
  "Remove any proposed locations that more than one elf wants to move to"
  [proposals]
  (let [duplicate-locs (duplicates proposals)]
    (remove #(duplicate-locs (second %)) proposals)))

(defn apply-proposal
  "Apply the proposal by removing the elf's old location and adding their new."
  [elves [old new]]
  (-> (disj elves old)
      (conj new)))

(defn apply-proposals
  "Apply all proposed moves"
  [elves proposals]
  (reduce apply-proposal elves proposals))

(defn round
  "During the first half of each round, each Elf considers the eight positions 
   adjacent to themself. If no other Elves are in one of those eight positions, 
   the Elf does not do anything during this round. Otherwise, the Elf looks 
   in each of four directions in the following order and proposes moving one 
   step in the first valid direction.
   
   After each Elf has had a chance to propose a move, the second half of the 
   round can begin. Simultaneously, each Elf moves to their proposed 
   destination tile if they were the only Elf to propose moving to that 
   position. If two or more Elves propose moving to the same position, 
   none of those Elves move."
  [[elves dirs]]
  [(->> (proposed-moves elves dirs)
        deduped-proposals
        (apply-proposals elves))
   (u/rotate 1 dirs)])

(defn area
  "Compute the area of the smallest rectangular section that contains
   all elves"
  [elves]
  (let [[minx maxx] ((juxt #(apply min %) #(apply max %)) (map first elves))
        [miny maxy] ((juxt #(apply min %) #(apply max %)) (map second elves))]
    (* (inc (- maxx minx)) (inc (- maxy miny)))))

(defn empty-tiles-after-ten-rounds
  "Compute empty tiles after simulating 10 rounds of elf moves"
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

;;;; Puzzle solutions

(defn day23-part1-soln
  "Simulate the Elves' process and find the smallest rectangle that contains 
   the Elves after 10 rounds. How many empty ground tiles does that 
   rectangle contain?"
  []
  (empty-tiles-after-ten-rounds day23-input))

(defn day23-part2-soln
  "Figure out where the Elves need to go. What is the number of the first 
   round where no Elf moves?"
  []
  (rounds-until-static day23-input))