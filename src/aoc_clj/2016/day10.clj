(ns aoc-clj.2016.day10
  "Solution to https://adventofcode.com/2016/day/10"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

;; Input parsing
(defn parse-assignment
  [s]
  (let [[_ value bot] (re-find #"value (\d+) goes to bot (\d+)" s)]
    {:type :assignment :value (read-string value) :bot (str "bot" bot)}))

(defn parse-comparison
  [s]
  (let [[_ bot low-type low-val high-type high-val]
        (re-find #"bot (\d+) gives low to (\w+) (\d+) and high to (\w+) (\d+)" s)]
    {:type :comparison
     :bot (str "bot" bot)
     :low (str low-type low-val)
     :high (str high-type high-val)}))

(defn parse-line
  [s]
  (if (str/starts-with? s "value")
    (parse-assignment s)
    (parse-comparison s)))

(defn assign
  "Update the `state` to assign `value` to the given `bot` number"
  [state {:keys [value bot]}]
  (if (get state bot)
    (update state bot conj value)
    (assoc state bot #{value})))

(defn establish-comparison
  [{:keys [bot low high]}]
  [bot {:low low :high high}])

(defn cmd-filter
  [k]
  (fn [cmds] (filter #(= k (:type %)) cmds)))
(def assignments (cmd-filter :assignment))
(def comparisons (cmd-filter :comparison))

(defn parse
  [input]
  (let [cmds (map parse-line input)]
    {:assignments (reduce assign {} (assignments cmds))
     :comparisons (into {} (map establish-comparison (comparisons cmds)))}))

;; Puzzle logic
(defn ready-bots
  "Determine the names of the bot(s) that have two microchips to compare"
  [{:keys [assignments]}]
  (keys (filter #(= 2 (count (val %))) assignments)))

(defn apply-logic
  "For a `bot` that is currently comparing two microchips, hand over
   the chips to the intended recipients defined in `comparisons` and
   return the new assignments data"
  [comparisons assignments bot]
  (let [logic (get comparisons bot)
        [low high] (sort (get assignments bot))]
    (-> assignments
        (assign {:bot (:low logic) :value low})
        (assign {:bot (:high logic) :value high})
        (assoc bot #{}))))

(defn step
  "Given the current state of bot assignments, advance one step in time
   given the logic defined in `state`'s `:comparisons` value"
  [{:keys [assignments comparisons] :as state}]
  (let [ready (ready-bots state)]
    (assoc state :assignments
           (reduce (partial apply-logic comparisons) assignments ready))))

(defn bot-that-compares
  "The number of the bot that ends up comparing the two values in `values`
   given the assignments and comparison logic in `init-state`"
  [init-state values]
  (loop [state init-state]
    (let [matches (filter #(= values (val %)) (:assignments state))]
      (if (not-empty matches)
        (read-string (subs (ffirst matches) 3))
        (recur (step state))))))

(defn output-values
  "Product of the values in outputs 0, 1, and 2 after all bots have
   handed off their microchips"
  [state]
  (let [outputs (-> (u/converge step state)
                    last
                    :assignments
                    (select-keys ["output0" "output1" "output2"])
                    vals)]
    (->> outputs
         (map first)
         (reduce *))))

;; Puzzle solutions
(defn part1
  "The number of the bot that compares 61 and 17"
  [input]
  (bot-that-compares input #{17 61}))

(defn part2
  "The product of the values of outputs 0, 1, and 2"
  [input] 
  (output-values input))