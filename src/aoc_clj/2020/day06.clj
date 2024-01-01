(ns aoc-clj.2020.day06
  "Solution to https://adventofcode.com/2020/day/6"
  (:require [clojure.set :as set]
            [aoc-clj.utils.core :as u]))

(defn parse
  [input]
  (->> (u/split-at-blankline input)))

(defn unique-answers-in-group
  [group]
  (->> group
       (mapcat #(map identity %))
       set))

(defn common-answers-in-group
  [group]
  (->> group
       (map (comp set #(map identity %)))
       (apply set/intersection)))

(defn sum-counts
  [input count-fn]
  (->> input
       (map (comp count count-fn))
       (reduce +)))

(defn day06-part1-soln
  [input]
  (sum-counts input unique-answers-in-group))

(defn day06-part2-soln
  [input]
  (sum-counts input common-answers-in-group))