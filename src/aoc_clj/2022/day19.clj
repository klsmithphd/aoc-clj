(ns aoc-clj.2022.day19
  "Solution to https://adventofcode.com/2022/day/19"
  (:require [aoc-clj.utils.core :as u]))

(defn parse-line
  [line]
  (let [[a b c d e f g] (map read-string (re-seq #"\d+" line))]
    [a {:ore      {:ore b}
        :clay     {:ore c}
        :obsidian {:ore d :clay e}
        :geode    {:ore f :obsidian g}}]))

(defn parse
  [input]
  (into {} (map parse-line input)))

(def d19-s01
  (parse
   ["Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian."
    "Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian."]))

(def day19-input (parse (u/puzzle-input "2022/day19-input.txt")))
(def time-limit 24)
(def init-inventory {:robots {:ore 1}
                     :materials {:ore 0
                                 :clay 0
                                 :obsidian 0
                                 :geode 0}})

(defn gather-materials
  [{:keys [robots materials] :as inv}]
  (assoc inv :materials
         (apply merge (map (fn [[k v]] (update materials k + v)) robots))))

(defn build-robots
  [{:keys [robots materials]}])

(defn update-inventory
  [inventory]
  (->> inventory
       gather-materials
       build-robots))

(defn tick
  [blueprint]
  (loop [timer time-limit inv init-inventory]
    (if (zero? timer)
      inv
      (recur (dec timer) (update-inventory inv)))))