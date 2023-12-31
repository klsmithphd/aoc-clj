(ns aoc-clj.2016.day10
  "Solution to https://adventofcode.com/2016/day/10"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

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
  [state {:keys [value bot]}]
  (if (get state bot)
    (update state bot conj value)
    (assoc state bot #{value})))

(defn assignments
  [cmds]
  (filter #(= :assignment (:type %)) cmds))

(defn establish-comparison
  [{:keys [bot low high]}]
  [bot {:low low :high high}])

(defn comparisons
  [cmds]
  (filter #(= :comparison (:type %)) cmds))

(defn parse
  [input]
  (let [cmds (map parse-line input)]
    {:assignments (reduce assign {} (assignments cmds))
     :comparisons (into {} (map establish-comparison (comparisons cmds)))}))

(defn ready-bots
  [{:keys [assignments]}]
  (keys (filter #(= 2 (count (val %))) assignments)))

(defn apply-logic
  [comparisons assignments bot]
  (let [logic (get comparisons bot)
        [low high] (sort (get assignments bot))]
    (-> assignments
        (assign {:bot (:low logic) :value low})
        (assign {:bot (:high logic) :value high})
        (assoc bot #{}))))

(defn step
  [{:keys [assignments comparisons] :as state}]
  (let [ready (ready-bots state)]
    (assoc state :assignments
           (reduce (partial apply-logic comparisons) assignments ready))))

(defn bot-that-compares
  [state values]
  (loop [s state]
    (let [matches (filter #(= values (val %)) (:assignments s))]
      (if (not-empty matches)
        (read-string (subs (ffirst matches) 3))
        (recur (step s))))))

(defn output-values
  [state]
  (let [outputs (-> (u/converge step state)
                    last
                    :assignments
                    (select-keys ["output0" "output1" "output2"])
                    vals)]
    (->> outputs
         (map first)
         (reduce *))))

(defn day10-part1-soln
  [input]
  (bot-that-compares input #{17 61}))

(defn day10-part2-soln
  [input]
  (output-values input))