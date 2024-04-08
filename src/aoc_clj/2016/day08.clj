- (ns aoc-clj.2016.day08
    "Solution to https://adventofcode.com/2016/day/8"
    (:require [clojure.string :as str]
              [aoc-clj.utils.blockstring :as blstr]
              [aoc-clj.utils.core :as u]))

;; Constants
(def screen-dims [50 6])

;; Input parsing
(defn parse-rect
  [rect-str]
  (let [[w h] (re-seq #"\d+" rect-str)]
    {:cmd :rect
     :width  (read-string w)
     :height (read-string h)}))

(defn parse-rotate
  [rot-str]
  (let [[_ type pos amt] (re-find #"(row|column) [x|y]=(\d+) by (\d+)" rot-str)]
    {:cmd :rotate
     :type (keyword type)
     :pos  (read-string pos)
     :amount (read-string amt)}))

(defn parse-line
  [s]
  (if (str/starts-with? s "rect")
    (parse-rect s)
    (parse-rotate s)))

(defn parse
  [input]
  (map parse-line input))

;; Puzzle logic
;; (defn row-indices
;;   "For 2-d data stored in a 1-d seq, return the indices for the row at idx"
;;   [[width _] idx]
;;   (range (* idx width) (* (inc idx) width)))

(defn col-indices
  "For 2-d data stored in a 1-d seq, return the indices for the column at idx"
  [[width height] idx]
  (range idx (* width height) width))

(defn rotate-row
  "Rotate the values of the row in vec `v` at position `pos` by `amount`"
  [[width] v pos amount]
  (vec
   (concat
    (subvec v 0 (* pos width))
    (u/rotate (- amount) (subvec v (* pos width) (* (inc pos) width)))
    (subvec v (* (inc pos) width)))))

(defn rotate-col
  "Rotate the values of the column in vec `v` at position `pos` by `amount`"
  [dims v pos amount]
  (let [idxs    (col-indices dims pos)
        new-col (u/rotate (- amount) (map v idxs))]
    (vec (apply assoc v (interleave idxs new-col)))))

(defn init-grid
  "Construct an grid initialized to zero of size given by `dims`"
  [[width height]]
  {:dims [width height]
   :grid (vec (repeat (* width height) 0))})

(defn apply-rotate
  "Apply a `rotate` instruction to update the display grid"
  [{:keys [dims grid]} {:keys [type pos amount]}]
  (case type
    :row    (rotate-row dims grid pos amount)
    :column (rotate-col dims grid pos amount)))

(defn apply-rect
  "Apply a `rect` instruction to update the display grid"
  [{:keys [grid] [grid-width] :dims} {:keys [width height]}]
  (let [idxs (for [y (range height)
                   x (range width)]
               (+ x (* y grid-width)))]
    (vec (apply assoc grid (interleave idxs (repeat 1))))))

(defn step
  "Update the `grid` following one instruction "
  [state {:keys [cmd] :as instruction}]
  (assoc state :grid
         (case cmd
           :rect   (apply-rect state instruction)
           :rotate (apply-rotate state instruction))))

(defn final-state
  "Iteratively apply the instructions to a (w x h) display grid"
  [dims cmds]
  (let [start (init-grid dims)]
    (reduce step start cmds)))

(defn lit-pixels
  "How many pixels are lit up on the (w x h) display after
   executing each of the `cmds` sequentially"
  [dims cmds]
  (->> (final-state dims cmds)
       :grid
       (reduce +)))

;; Puzzle solutions
(defn part1
  "How many pixels are lit up after following the instructions"
  [input]
  (lit-pixels screen-dims input))

(defn part2
  "What string do the lit up pixels spell after following the instructions"
  [input]
  (->> (final-state screen-dims input)
       :grid
       (blstr/blockstring->str blstr/fontmap-6x5)))
