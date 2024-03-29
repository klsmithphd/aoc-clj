- (ns aoc-clj.2016.day08
    "Solution to https://adventofcode.com/2016/day/8"
    (:require [clojure.string :as str]
              [aoc-clj.utils.core :as u]
              [aoc-clj.utils.grid :as grid]
              [aoc-clj.utils.grid.mapgrid :as mg]))

;; Constants
(def screen-width 50)
(def screen-height 6)

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
(defn grid-set
  "Return a (w x h) grid map with all values set to `val`"
  [width height val]
  (into {} (for [y (range height)
                 x (range width)]
             [[x y] val])))

(defn get-slice
  "Retrieve a slice of the `grid` of `type` (`:row|:column`) at
   index `pos` (0-indexed)"
  [grid type pos]
  (let [coord (case type
                :column first
                :row second)]
    (into (sorted-map) (filter #(= pos (-> % key coord)) grid))))

(defn rotate-slice
  "Cycle the values of the slice by `amount`"
  [slice amount]
  (zipmap (keys slice) (u/rotate (- amount) (vals slice))))

(defn apply-rotate
  "Apply a `rotate` instruction to update the display grid"
  [grid {:keys [type pos amount]}]
  (merge grid (-> (get-slice grid type pos)
                  (rotate-slice amount))))

(defn apply-rect
  "Apply a `rect` instruction to update the display grid"
  [grid {:keys [width height]}]
  (merge grid (grid-set width height 1)))

(defn step
  "Update the `grid` following one instruction "
  [grid {:keys [cmd] :as instruction}]
  (case cmd
    :rect   (apply-rect grid instruction)
    :rotate (apply-rotate grid instruction)))

(defn init-grid
  "Intialize an empty grid (w x h) to all pixels off"
  [width height]
  (grid-set width height 0))

(defn final-state
  "Iteratively apply the instructions to a (w x h) display grid"
  [width height cmds]
  (reduce step (init-grid width height) cmds))

(defn lit-pixels
  "How many pixels are lit up on the (w x h) display after
   executing each of the `cmds` sequentially"
  [width height cmds]
  (->> (final-state width height cmds)
       vals
       (filter pos?)
       count))

;; Puzzle solutions
(defn part1
  "How many pixels are lit up after following the instructions"
  [input]
  (lit-pixels screen-width screen-height input))

(defn part2
  "What string do the lit up pixels spell after following the instructions"
  [input]
  ;; Print the grid so as to be able to read the block characters
  (println (grid/Grid2D->ascii
            {\  0 \# 1}
            (mg/->MapGrid2D
             screen-width
             screen-height
             (final-state screen-width screen-height input))
            :down true))
  (comment
    "####  ##   ##  ###   ##  ###  #  # #   # ##   ##  "
    "#    #  # #  # #  # #  # #  # #  # #   ##  # #  # "
    "###  #  # #  # #  # #    #  # ####  # # #  # #  # "
    "#    #  # #### ###  # ## ###  #  #   #  #### #  # "
    "#    #  # #  # # #  #  # #    #  #   #  #  # #  # "
    "####  ##  #  # #  #  ### #    #  #   #  #  #  ##  ")
  "EOARGPHYAO")

