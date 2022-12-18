(ns aoc-clj.2022.day16
  "Solution to https://adventofcode.com/2022/day/16"
  (:require [aoc-clj.utils.core :as u]))

(defn parse-line
  [line]
  (let [valves (re-seq #"[A-Z]{2}" line)
        flow   (read-string (re-find #"\d+" line))]
    {:valve (first valves)
     :flow  flow
     :tunnels (rest valves)}))

(defn parse
  [input]
  (map parse-line input))

(def d16-s01
  (parse
   ["Valve AA has flow rate=0; tunnels lead to valves DD, II, BB"
    "Valve BB has flow rate=13; tunnels lead to valves CC, AA"
    "Valve CC has flow rate=2; tunnels lead to valves DD, BB"
    "Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE"
    "Valve EE has flow rate=3; tunnels lead to valves FF, DD"
    "Valve FF has flow rate=0; tunnels lead to valves EE, GG"
    "Valve GG has flow rate=0; tunnels lead to valves FF, HH"
    "Valve HH has flow rate=22; tunnel leads to valve GG"
    "Valve II has flow rate=0; tunnels lead to valves AA, JJ"
    "Valve JJ has flow rate=21; tunnel leads to valve II"]))

(def day16-input (parse (sort (u/puzzle-input "2022/day16-input.txt"))))