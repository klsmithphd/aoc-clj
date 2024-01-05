(ns aoc-clj.2015.day21
  "Solution to https://adventofcode.com/2015/day/21"
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :as combo]))

(defn parse-line
  [line]
  (let [[attr qty] (str/split line #": ")]
    [(keyword (str/join "-" (str/split (str/lower-case attr) #" "))) (read-string qty)]))

(defn parse
  [input]
  (into {} (map parse-line input)))

(def player {:hit-points 100 :damage 0 :armor 0 :cost 0})

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

(defn all-item-combos
  []
  (->>  (for [weapon (keys weapons)
              armor  (concat (repeat 5 nil) (keys armors))
              ring   (concat (repeat 6 nil) (keys rings) (combo/combinations (keys rings) 2))]
          {:weapon weapon :armor armor :ring (flatten (list ring))})
        distinct))

(defn total
  [acc {:keys [cost damage armor]}]
  (->  acc
       (update :cost   + cost)
       (update :damage + damage)
       (update :armor  + armor)))

(defn combo-total
  [{:keys [weapon armor ring]}]
  (let [w (weapons weapon)
        a (armors  armor)
        r (map rings ring)
        combo (filter some? (flatten [[w] [a] r]))]
    (reduce total player combo)))

(defn player-wins?
  [boss player]
  (let [{p-hit :hit-points p-damage :damage p-armor :armor} player
        {b-hit :hit-points b-damage :damage b-armor :armor} boss]
    (>= (Math/ceil (/ p-hit (max 1 (- b-damage p-armor))))
        (Math/ceil (/ b-hit (max 1 (- p-damage b-armor)))))))

(defn cheapest-winning-combo
  [boss]
  (let [options (map combo-total (all-item-combos))
        winners (filter (partial player-wins? boss) options)]
    (apply min-key :cost winners)))

(defn priciest-losing-combo
  [boss]
  (let [options (map combo-total (all-item-combos))
        losers (remove (partial player-wins? boss) options)]
    (apply max-key :cost losers)))

(defn part1
  [input]
  (:cost (cheapest-winning-combo input)))

(defn part2
  [input]
  (:cost (priciest-losing-combo input)))