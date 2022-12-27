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
(def init-inventory {:robots {:ore 1
                              :clay 0
                              :obsidian 0
                              :geode 0}
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

(defn robot-build-possible?
  [inventory [_ ingredients]]
  (->> (reduce consume-material inventory ingredients)
       :materials
       (map second)
       (not-any? neg?)))

(defn options
  [blueprint inventory]
  (concat [:noop]
          (keys (filter #(robot-build-possible? inventory %) blueprint))))

(options (get d19-s01 1) init-inventory)

(defn consume-materials
  [inventory ingredients]
  (reduce consume-material inventory ingredients))

(defn gather-materials
  [{:keys [robots] :as inv}]
  (reduce gather-material inv robots))

(defn add-robot
  [inventory robot]
  (update-in inventory [:robots robot] inc))

(defn step
  "Each robot can collect 1 of its resource type per minute. 
   It also takes one minute for the robot factory (also conveniently 
   from your pack) to construct any type of robot, although it consumes the 
   necessary resources available when construction begins.
   
   In every step (1 minute), 
   (a) determine what robot(s) can be built, instantly consuming those resources
   (b) let each active robot gather 1 unit of their resource type
   (c) add the newly built robots into the inventory"
  [blueprint inventory choice]
  (if (= :noop choice)
    (gather-materials inventory)
    (-> inventory
        (consume-materials (blueprint choice))
        gather-materials
        (add-robot choice))))

(defn best-subpath
  "Finds the optimal set of choices to make at each minute so as to maximize
   the number of geodes collected."
  [blueprint t history]
  (if (zero? t)
    history
    (let [inventory (peek history)
          candidates (options blueprint (peek history))
          choices  (map #(step blueprint inventory %) candidates)
          subpaths  (map #(best-subpath blueprint (dec t) (conj history %)) choices)]
      (if (empty? subpaths)
        history
        (first (sort-by (comp :geode :materials peek) > subpaths))))))

(best-subpath (get d19-s01 1) 19 [init-inventory])
(gather-materials init-inventory)
(step (get d19-s01 1) init-inventory :noop)

;; (defn build-robot-type?
;;   [inv type ingredients]
;;   (and
;;    (robot-build-possible? inv ingredients)
;;    (case type
;;      :ore false
;;      :clay (if (= (get-in inv [:robots :obsidian] 0) 1)
;;              (< (get-in inv [:robots :clay] 0) 4)
;;              (< (get-in inv [:robots :clay] 0) 3))
;;      :obsidian (< (get-in inv [:robots :obsidian] 0) 2)
;;      true)))

;; (defn add-robot
;;   [{:keys [pending] :as inv}]
;;   (if (nil? pending)
;;     inv
;;     (-> inv
;;         (assoc-in [:robots pending] (inc (get-in inv [:robots pending] 0)))
;;         (dissoc :pending))))

;; (defn build-robot
;;   [inv [type ingredients]]
;;   (if (build-robot-type? inv type ingredients)
;;     (-> (reduce consume-material inv ingredients)
;;         (assoc :pending type))
;;     inv))

;; (defn order-new-robots
;;   [blueprint inv]
;;   (reduce build-robot inv blueprint))



;; (defn run-blueprint
;;   [blueprint]
;;   (->> (iterate (partial step blueprint) init-inventory)
;;        (drop time-limit)
;;        first))

;; (defn max-geodes
;;   [blueprint]
;;   (get-in (run-blueprint blueprint) [:materials :geode]))