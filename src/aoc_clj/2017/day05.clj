(ns aoc-clj.2017.day05
  "Solution to https://adventofcode.com/2017/day/5")

;; Input parsing
(defn parse
  [input]
  (mapv read-string input))

;; Puzzle logic
(defn step
  [part {:keys [jumps idx] :as state}]
  (let [offset (get jumps idx)]
    (-> state
        (update :idx + offset)
        (update :steps inc)
        (update-in [:jumps idx]
                   (case part
                     :part1 inc
                     :part2 (if (>= offset 3)
                              dec
                              inc))))))

(defn not-escaped?
  [{:keys [jumps idx]}]
  (<= 0 idx (dec (count jumps))))

(defn steps-to-escape
  [part jumps]
  (->> (iterate #(step part %) {:jumps jumps :idx 0 :steps 0})
       (drop-while not-escaped?)
       first
       :steps))


;; Puzzle solutions
(defn part1
  [input]
  (steps-to-escape :part1 input))

(defn part2
  [input]
  (steps-to-escape :part2 input))