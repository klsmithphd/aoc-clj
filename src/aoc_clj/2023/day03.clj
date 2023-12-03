(ns aoc-clj.2023.day03
  "Solution to https://adventofcode.com/2023/day/3"
  (:require [clojure.set :as set]
            [aoc-clj.utils.grid :as grid]))

(def d03-s01-raw ["467..114.."
                  "...*......"
                  "..35..633."
                  "......#..."
                  "617*......"
                  ".....+.58."
                  "..592....."
                  "......755."
                  "...$.*...."
                  ".664.598.."])

(def d03-s01
  {:numbers [{:points [[0 0] [1 0] [2 0]] :value 467}
             {:points [[5 0] [6 0] [7 0]] :value 114}
             {:points [[2 2] [3 2]] :value 35}
             {:points [[6 2] [7 2] [8 2]] :value 633}
             {:points [[0 4] [1 4] [2 4]] :value 617}
             {:points [[7 5] [8 5]] :value 58}
             {:points [[2 6] [3 6] [4 6]] :value 592}
             {:points [[6 7] [7 7] [8 7]] :value 755}
             {:points [[1 9] [2 9] [3 9]] :value 664}
             {:points [[5 9] [6 9] [7 9]] :value 598}]
   :symbols [{:point [3 1] :value \*}
             {:point [6 3] :value \#}
             {:point [3 4] :value \*}
             {:point [5 5] :value \+}
             {:point [3 8] :value \$}
             {:point [5 8] :value \*}]})

;; Every contiguous grouping of digits is a number, and has positions
;; Every non-period symbol has a position
;; A number is a part number if it is adjacent (within the 8-neighborhood
;; of a symbol)


(defn space? [c] (= c \.))

(def digits (set (apply str (range 10))))

(defn char-type
  [c]
  (cond
    (= c \.) :space
    (digits c) :number
    :else :symbol))

(defn process-number
  [group]
  {:points (map last group)
   :value (read-string (apply str (map second group)))})

(defn process-group
  [group]
  (if (= :number (ffirst group))
    (process-number group)
    {:point (last (first group)) :value (second (first group))}))

(defn parse-char
  [y x c]
  [(char-type c) c [x y]])

(defn parse-row
  [y row]
  (let [chars (map-indexed (partial parse-char y) row)
        groups (->> (partition-by first chars)
                    (remove (comp #(= :space %) ffirst)))]
    (map process-group groups)))


(defn parse
  [input]
  (let [foo (flatten (map-indexed parse-row input))
        numbers (filter :points foo)
        symbols (filter :point foo)]
    {:numbers numbers :symbols symbols}))


(defn part-number?
  [symbol-pts {:keys [points]}]
  (let [neighbors (->> points
                       (mapcat #(grid/adj-coords-2d % :include-diagonals true))
                       set)]
    (boolean (seq (set/intersection symbol-pts neighbors)))
    ;; neighbors
    ))

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


(defn day03-part1-soln
  "Part1"
  [input]
  (part-numbers-sum input))

(defn day03-part2-soln
  "Part2"
  [input]
  (identity input))