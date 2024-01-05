(ns aoc-clj.2019.day25
  "Solution to https://adventofcode.com/2019/day/25"
  (:require [clojure.math.combinatorics :as combo]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.intcode :as intcode]))

(def parse u/firstv)

(def checkpoint1-items
  ["astronaut ice cream"
   "coin"
   "dark matter"
   "festive hat"
   "klein bottle"
   "mutex"
   "pointer"
   "whirled peas"])

(defn test-combo
  [combo]
  (concat (map #(str "take " %) combo)
          ["east"]
          (map #(str "drop " %) combo)))

(defn test-checkpoint-cmds
  [inventory]
  (let [drop-all (map #(str "drop " %) inventory)
        combo-attempts (mapcat test-combo (mapcat #(combo/combinations inventory %) (range 1 7)))]
    (concat drop-all combo-attempts)))

(defn test-combinations
  [input cmds]
  (intcode/interactive-asciicode
   input
   (concat cmds (test-checkpoint-cmds checkpoint1-items))))

(defn text-adventure-walkthrough
  [input commands]
  (comment
    (test-combinations input commands)
    "Keeps trying to drop all possible combinations of objects until
     the right combination triggers the pressure-sensitive floor.
     That leads to the final commands in the walkthrough below")
  (let [cmds (intcode/cmds->ascii
              (concat commands
                      ["drop dark matter"]
                      ["drop astronaut ice cream"]
                      ["drop klein bottle"]
                      ["drop pointer"]
                      ["east"]))]
    (->> (intcode/intcode-exec input cmds)
         intcode/out-seq
         intcode/read-ascii-output
         println)))

(defn part1
  [input commands]
  (comment
    (text-adventure-walkthrough input commands)
    "A loud, robotic voice says \"Analysis complete! You may proceed.\" and you enter the cockpit."
    "Santa notices your small droid, looks puzzled for a moment, realizes what has happened, and radios your ship directly."
    "Oh, hello! You should be able to get in by typing 16410 on the keypad at the main airlock.")
  16410)