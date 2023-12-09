(ns aoc-clj.2023.day03
  "Solution to https://adventofcode.com/2023/day/3"
  (:require [clojure.set :as set]
            [aoc-clj.utils.grid :as grid]))

(def digit-chars
  "The set of characters representing the digits 0-9"
  (set (apply str (range 10))))

(defn char-type
  [c]
  (cond
    (= c \.) :space
    (digit-chars c) :number
    :else :symbol))

(defn parse-char
  [y x c]
  [(char-type c) c [x y]])

(defn process-number
  [group]
  {:points (map last group)
   :value (read-string (apply str (map second group)))})

(defn process-group
  [group]
  (if (= :number (ffirst group))
    (process-number group)
    {:point (last (first group)) :value (second (first group))}))


(defn parse-row
  [y row]
  (let [chars (map-indexed (partial parse-char y) row)
        groups (->> (partition-by first chars)
                    (remove (comp #(= :space %) ffirst)))]
    (map process-group groups)))

(defn parse
  [input]
  (let [non-space-symbols (flatten (map-indexed parse-row input))
        numbers (filter :points non-space-symbols)
        symbols (filter :point non-space-symbols)]
    {:numbers numbers :symbols symbols}))

(defn part-number?
  "Any number adjacent to a symbol, even diagonally, is a part number"
  [symbol-pts {:keys [points]}]
  (let [neighbors (->> points
                       (mapcat #(grid/adj-coords-2d % :include-diagonals true))
                       set)]
    (boolean (seq (set/intersection symbol-pts neighbors)))))


(defn part-numbers
  [{:keys [symbols numbers]}]
  (let [symbol-pts (->> symbols
                        (map :point)
                        set)]
    (->> numbers
         (filter #(part-number? symbol-pts %))
         (map :value))))

(defn part-numbers-sum
  [input]
  (reduce + (part-numbers input)))

(defn adjacent-parts
  [numbers point]
  (let [gear-nbrs (set (grid/adj-coords-2d point :include-diagonals true))]
    (filter #(seq (set/intersection gear-nbrs (set (:points %)))) numbers)))

(defn gear-adjacent-parts
  [{:keys [symbols numbers]}]
  (let [gear-pts (->> symbols
                      (filter #(= \* (:value %)))
                      (map :point)
                      set)]
    (->> (map #(adjacent-parts numbers %) gear-pts)
         (filter #(= 2 (count %)))
         (map #(map :value %)))))

(defn gear-ratio-sum
  "A gear is any * symbol that is adjacent to exactly two part numbers.
   Its gear ratio is the result of multiplying those two numbers together."
  [input]
  (->> input
       gear-adjacent-parts
       (map #(reduce * %))
       (reduce +)))


(defn day03-part1-soln
  "What is the sum of all of the part numbers in the engine schematic?"
  [input]
  (part-numbers-sum input))

(defn day03-part2-soln
  "What is the sum of all of the gear ratios in your engine schematic?"
  [input]
  (gear-ratio-sum input))