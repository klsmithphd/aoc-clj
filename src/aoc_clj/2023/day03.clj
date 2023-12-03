(ns aoc-clj.2023.day03
  "Solution to https://adventofcode.com/2023/day/3")

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


(defn day03-part1-soln
  "Part1"
  [input]
  (identity input))

(defn day03-part2-soln
  "Part2"
  [input]
  (identity input))