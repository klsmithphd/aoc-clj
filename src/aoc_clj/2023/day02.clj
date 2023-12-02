(ns aoc-clj.2023.day02
  "Solution to https://adventofcode.com/2023/day/2"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(defn parse-id
  [game-str]
  (read-string (re-find #"\d+" game-str)))

(defn parse-cube
  [cube-str]
  (let [[qty color] (str/split cube-str #" ")]
    [(keyword color) (read-string qty)]))

(defn parse-cubes
  [cubes-str]
  (into {} (map parse-cube (str/split cubes-str #", "))))

(defn parse-sets
  [sets-str]
  (map parse-cubes (str/split sets-str #"; ")))

(defn parse-game
  [line]
  (let [[game-str sets-str] (str/split line #": ")
        game-id (parse-id game-str)
        game-sets (parse-sets sets-str)]
    {:id game-id :sets game-sets}))

(defn parse
  [input]
  (mapv parse-game input))

(def day02-input (u/parse-puzzle-input parse 2023 2))

(def part1-target
  {:red 12
   :green 13
   :blue 14})

(defn set-compare
  [target [k v]]
  (<= v (target k)))

(defn possible-set
  [target game-set]
  (every? #(set-compare target %) game-set))

(defn possible-game
  [{:keys [sets]}]
  (every? #(possible-set part1-target %) sets))

(defn possible-game-id-sum
  [games]
  (->> (filter possible-game games)
       (map :id)
       (reduce +)))

(defn min-color
  [sets color]
  (apply max (map #(get % color 0) sets)))

(def colors [:red :green :blue])

(defn fewest-cubes
  [{:keys [sets]}]
  (zipmap colors (map #(min-color sets %) colors)))

(defn power
  [cube]
  (reduce * (vals cube)))

(defn power-fewest-cubes
  [game]
  (power (fewest-cubes game)))

(defn power-fewest-cubes-sum
  [input]
  (reduce + (map power-fewest-cubes input)))

(defn day02-part1-soln
  []
  (possible-game-id-sum day02-input))

(defn day02-part2-soln
  []
  (power-fewest-cubes-sum day02-input))