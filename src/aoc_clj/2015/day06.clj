(ns aoc-clj.2015.day06
  (:require [aoc-clj.utils.core :as u]))

(def pattern #"(turn on|turn off|toggle) (\d+),(\d+) through (\d+),(\d+)")
(def command {"turn on" :on
              "turn off" :off
              "toggle" :toggle})

(defn parse
  [line]
  (let [[a b c d e] (rest (first (re-seq pattern line)))]
    {:cmd (command a)
     :start [(read-string b) (read-string c)]
     :end   [(read-string d) (read-string e)]}))

(def day06-input (map parse (u/puzzle-input "2015/day06-input.txt")))

(defn rect-range
  [[sx sy] [ex ey]]
  (for [y (range sy (inc ey))
        x (range sx (inc ex))] (str x "," y)))

(defn off
  [grid pos]
  (assoc grid pos false))

(defn on
  [grid pos]
  (assoc grid pos true))

(defn toggle
  [grid pos]
  (update grid pos not))

(def commands
  {:on on
   :off off
   :toggle toggle})

(defn off2
  [grid pos]
  (update grid pos #(if (nil? %)
                      0
                      (if (pos? %) (dec %) %))))

(defn on2
  [grid pos]
  (update grid pos #(if (nil? %) 1 (inc %))))

(defn toggle2
  [grid pos]
  (update grid pos #(if (nil? %) 2 (+ % 2))))

(def commands2
  {:on on2
   :off off2
   :toggle toggle2})

(defn update-grid
  [cmds grid {:keys [cmd start end]}]
  (let [locs (rect-range start end)
        update-fn (cmds cmd)]
    (reduce update-fn grid locs)))

(defn count-on
  [grid]
  (count (filter true? (vals grid))))

(defn brightness
  [grid]
  (reduce + (filter some? (vals grid))))

(def update-grid-part1 (partial update-grid commands))
(defn day06-part1-soln
  []
  (count-on (reduce update-grid-part1 {} day06-input)))

(def update-grid-part2 (partial update-grid commands2))
(defn day06-part2-soln
  []
  (brightness (reduce update-grid-part2 {} day06-input)))