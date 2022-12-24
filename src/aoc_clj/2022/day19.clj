(ns aoc-clj.2022.day19
  "Solution to https://adventofcode.com/2022/day/19"
  (:require [aoc-clj.utils.core :as u]))

(defn parse-line
  [line]
  (let [[a b c d e f g] (map read-string (re-seq #"\d+" line))]
    [a {:geode    {:ore f :obsidian g}
        :obsidian {:ore d :clay e}
        :clay     {:ore c}
        :ore      {:ore b}}]))

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
                     :materials {:geode 0
                                 :obsidian 0
                                 :clay 0
                                 :ore 0}})

(defn gather-material
  [inv [type amt]]
  (update-in inv [:materials type] + amt))

(defn consume-material
  [inv [type amt]]
  (update-in inv [:materials type] - amt))

(defn gather-materials
  [{:keys [robots] :as inv}]
  (reduce gather-material inv robots))

(defn not-build-robot-type?
  [inv ingredients]
  (let [used-materials (:materials (reduce consume-material inv ingredients))]
    (some neg? (map second used-materials))))

(defn add-robot
  [inv type]
  (assoc-in inv [:robots type] (inc (get-in inv [:robots type] 0))))

(defn build-robot
  [inv [type ingredients]]
  (if (not-build-robot-type? inv ingredients)
    inv
    (-> (reduce consume-material inv ingredients)
        (add-robot type))))

(defn build-robots
  [blueprint inv]
  (reduce build-robot inv blueprint))

(defn update-inventory
  [blueprint inventory]
  (->> inventory
       gather-materials
       (build-robots blueprint)))

(defn tick
  [blueprint]
  (let [updater (partial update-inventory blueprint)]
    (loop [timer time-limit inv init-inventory]
      (if (zero? timer)
        inv
        (recur (dec timer) (updater inv))))))

(tick (get d19-s01 1))