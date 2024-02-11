(ns aoc-clj.2015.day21
  "Solution to https://adventofcode.com/2015/day/21"
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :as combo]))

;; Constants
(def empty-stats  {:cost 0 :damage 0 :armor 0})
(def player-start (assoc empty-stats :hit-points 100))

(def weapons
  {:dagger     {:cost 8   :damage 4 :armor 0}
   :shortsword {:cost 10  :damage 5 :armor 0}
   :warhammer  {:cost 25  :damage 6 :armor 0}
   :longsword  {:cost 40  :damage 7 :armor 0}
   :greataxe   {:cost 74  :damage 8 :armor 0}})

(def armors
  {:leather    {:cost 13  :damage 0 :armor 1}
   :chainmail  {:cost 31  :damage 0 :armor 2}
   :splintmail {:cost 53  :damage 0 :armor 3}
   :bandedmail {:cost 75  :damage 0 :armor 4}
   :platemail  {:cost 102 :damage 0 :armor 5}})

(def rings
  {:damage+1   {:cost 25  :damage 1 :armor 0}
   :damage+2   {:cost 50  :damage 2 :armor 0}
   :damage+3   {:cost 100 :damage 3 :armor 0}
   :defense+1  {:cost 20  :damage 0 :armor 1}
   :defense+2  {:cost 40  :damage 0 :armor 2}
   :defense+3  {:cost 80  :damage 0 :armor 3}})

;; Input parsing
(defn parse-line
  [line]
  (let [[attr qty] (str/split line #": ")]
    [(keyword (str/join "-" (str/split (str/lower-case attr) #" "))) (read-string qty)]))

(defn parse
  [input]
  (into {} (map parse-line input)))

;; Puzzle logic
(defn all-item-combos
  "Returns a collection of all the possible legal item combos"
  []
  (for [weapon (vals weapons)
        armor  (concat [empty-stats] (vals armors))
        ring   (concat [[empty-stats]]
                       (mapv vector (vals rings))
                       (combo/combinations (vals rings) 2))]
    (concat [weapon armor] ring)))

(defn total
  "Add the given item's buff stats to our overall total"
  [tot {:keys [cost damage armor]}]
  (->  tot
       (update :cost   + cost)
       (update :damage + damage)
       (update :armor  + armor)))

(defn player-stats
  "For a given combination of a weapon, armor, and rings, compute the
   player's overall stats"
  [combos]
  (reduce total player-start combos))

(defn all-players
  "A collection of all possible player stats"
  []
  (map player-stats (all-item-combos)))

(defn player-wins?
  "We don't need to simulate the game, because once the stats are known,
   the boss and the player will do the same amount of damage each round.
   We just need to figure out who gets to zero first.
   
   Each player does a minimum of one amount of damage every round, but
   typically the amount of damage is the opponent's hit points minus
   the defender's defense (armor) points."
  [boss player]
  (let [{p-hit :hit-points p-damage :damage p-armor :armor} player
        {b-hit :hit-points b-damage :damage b-armor :armor} boss]
    (>= (Math/ceil (/ p-hit (max 1 (- b-damage p-armor))))
        (Math/ceil (/ b-hit (max 1 (- p-damage b-armor)))))))

(defn cheapest-winning-combo
  "Of all the item combos, finds the winning scenario with the cheapest cost"
  [boss]
  (->> (all-players)
       (filter #(player-wins? boss %))
       (map :cost)
       (apply min)))

(defn priciest-losing-combo
  "Of all the item combos, finds the losing scenario with the maximum cost"
  [boss]
  (->> (all-players)
       (remove #(player-wins? boss %))
       (map :cost)
       (apply max)))

;; Puzzle solutions
(defn part1
  "Least amount of gold to spend and still win the fight"
  [input]
  (cheapest-winning-combo input))

(defn part2
  "Most amount of gold to spend and still lose the fight"
  [input]
  (priciest-losing-combo input))