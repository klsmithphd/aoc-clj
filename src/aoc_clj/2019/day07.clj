(ns aoc-clj.2019.day07
  "Solution to https://adventofcode.com/2019/day/7"
  (:require [clojure.math.combinatorics :as combo]
            [manifold.stream :as s]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.intcode :as intcode]))

(def parse u/firstv)

(defn amplifier
  "Creates an 'amplifier' `intcode` program to run on another thread
   using input stream `in` and output stream `out`"
  [intcode [in out]]
  (future (intcode/intcode-exec intcode in out)))

(defn inout-streams
  "Construct the correct number of input/output streams for the amplifer config"
  [config]
  (case config
    :chain (repeatedly 6 s/stream)
    :loop  (repeatedly 5 s/stream)))

(defn inout-pairs
  "Construct the correct input/output stream pairs for the amplifer config"
  [config streams]
  (case config
    ;; '((0 1) (1 2) (2 3) (3 4) (4 5))
    :chain (partition 2 1 streams)
    ;; '((0 1) (1 2) (2 3) (3 4) (4 0))
    :loop  (partition 2 1 streams streams)))

(defn amplifier-config
  "For a given `intcode` program and initial phase values given by `phases`
   for each of the five amplifiers in the config (either `:chain` or `:loop`),
   return the final amplifier output value"
  [intcode phases config]
  (let [streams (inout-streams config)
        amps    (map #(amplifier intcode %) (inout-pairs config streams))
        _       (doall (map #(s/put! %1 %2) streams phases))
        _       (s/put! (first streams) 0)]
    (intcode/last-out @(last amps))))

(defn amplifier-chain
  [intcode phases]
  (amplifier-config intcode phases :chain))

(defn amplifier-loop
  [intcode phases]
  (amplifier-config intcode phases :loop))

(defn max-output
  "Return the highest possible output value from running the amplifier config
   on all possible valid phase permutations"
  [intcode amp-config phase-options]
  (apply max (map #(amp-config intcode %) (combo/permutations phase-options))))

(defn max-amplifier-chain-output
  [intcode]
  (max-output intcode amplifier-chain [0 1 2 3 4]))

(defn max-amplifier-loop-output
  [intcode]
  (max-output intcode amplifier-loop [5 6 7 8 9]))

(defn part1
  [input]
  (max-amplifier-chain-output input))

(defn part2
  [input]
  (max-amplifier-loop-output input))