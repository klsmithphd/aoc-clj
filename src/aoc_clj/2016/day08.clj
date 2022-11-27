(ns aoc-clj.2016.day08
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.grid :as grid]))

(defn parse-rect
  [s]
  (let [[_ dims] (str/split s #" ")
        [w h]    (str/split dims #"x")]
    {:cmd :rect
     :width (read-string w)
     :height (read-string h)}))

(defn parse-rotate
  [s]
  (let [[_ type rowcol _ amt] (str/split s #" ")
        [_ idx] (str/split rowcol #"=")]
    {:cmd :rotate
     :type (keyword type)
     :pos  (read-string idx)
     :amount (read-string amt)}))

(defn parse-line
  [s]
  (if (str/starts-with? s "rect")
    (parse-rect s)
    (parse-rotate s)))

(def day08-input (map parse-line (u/puzzle-input "2016/day08-input.txt")))

(defn apply-rect
  [state {:keys [width height]}]
  (update state :grid merge
          (into {} (for [y (range height)
                         x (range width)]
                     [[x y] :on]))))

(defn get-slice
  [{:keys [grid]} type pos]
  (let [coord (case type
                :column first
                :row second)]
    (into (sorted-map) (filter #(= pos (-> % key coord)) grid))))

(defn rotate
  [slice amount]
  (zipmap (keys slice) (u/rotate (- amount) (vals slice))))

(defn apply-rotate
  [state {:keys [type pos amount]}]
  (update state :grid merge (-> (get-slice state type pos)
                                (rotate amount))))

(defn apply-cmd
  [state {:keys [cmd] :as instruction}]
  (case cmd
    :rect   (apply-rect state instruction)
    :rotate (apply-rotate state instruction)))

(defn make-grid
  [width height]
  {:width width
   :height height
   :grid
   (zipmap (for [y (range height)
                 x (range width)]
             [x y])
           (repeat :off))})

(defn apply-instructions
  [width height cmds]
  (reduce apply-cmd (make-grid width height) cmds))

(defn lit-pixels
  [width height cmds]
  (->> (apply-instructions width height cmds)
       :grid
       vals
       (filter #{:on})
       count))

(defn day08-part1-soln
  []
  (lit-pixels 50 6 day08-input))

;; Execute to print message to screen
(comment
  (println (grid/Grid2D->ascii {\  :off \# :on} (apply-instructions 50 6 day08-input)))
  "####  ##   ##  ###   ##  ###  #  # #   # ##   ##  "
  "#    #  # #  # #  # #  # #  # #  # #   ##  # #  # "
  "###  #  # #  # #  # #    #  # ####  # # #  # #  # "
  "#    #  # #### ###  # ## ###  #  #   #  #### #  # "
  "#    #  # #  # # #  #  # #    #  #   #  #  # #  # "
  "####  ##  #  # #  #  ### #    #  #   #  #  #  ##  ")

(defn day08-part2-soln
  []
  "EOARGPHYAO")

