(ns aoc-clj.2023.day02
  "Solution to https://adventofcode.com/2023/day/2"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(def part1-target
  {:red 12
   :green 13
   :blue 14})

(def colors [:red :green :blue])

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

(defn set-compare
  "Given a target map of maximum cubes of any given color, return
   a boolean indicating whether the set value is less than or equal to the target"
  [[k v]]
  (<= v (part1-target k)))

(defn possible-set
  "Returns a boolean if the set is possible. A set is possible
   if the number of cubes of a given color are less than or equal to
   the target numbers for that color"
  [game-set]
  (every? set-compare game-set))

(defn possible-game
  "Returns a boolean indicating whether the game is possible.
   A game is possible if every set in the game is possible."
  [{:keys [sets]}]
  (every? possible-set sets))

(defn possible-game-id-sum
  "Returns the sum of the ids of the games deemed possible"
  [games]
  (->> (filter possible-game games)
       (map :id)
       (reduce +)))

(defn min-color
  "Returns the minimum number of cubes of a `color`, given the observed
   `sets`. The minimum number required is actually the maximum number of cubes
   of that color seen across any set."
  [sets color]
  (apply max (map #(get % color 0) sets)))

(defn fewest-cubes
  "Returns a map of the cube colors and the minimum required number of cubes of
   that color for a given game"
  [{:keys [sets]}]
  (zipmap colors (map #(min-color sets %) colors)))

(defn power
  "The power of a set of cubes is equal to the numbers of red, green, and blue 
   cubes multiplied together."
  [cube]
  (reduce * (vals cube)))

(defn power-fewest-cubes
  "Returns the power of the minimum set of cubes required in a game"
  [game]
  (power (fewest-cubes game)))

(defn power-fewest-cubes-sum
  "Returns the sum of the powers of the minimum sets of cubes require 
   across all games"
  [games]
  (reduce + (map power-fewest-cubes games)))

(defn day02-part1-soln
  "Determine which games would have been possible if the bag had been loaded 
   with only 12 red cubes, 13 green cubes, and 14 blue cubes. 
   What is the sum of the IDs of those games?"
  []
  (possible-game-id-sum day02-input))

(defn day02-part2-soln
  "For each game, find the minimum set of cubes that must have been present. 
   What is the sum of the power of these sets?"
  []
  (power-fewest-cubes-sum day02-input))