(ns aoc-clj.2022.day05
  "Solution to https://adventofcode.com/2022/day/5"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(defn parse-stack
  [stack]
  [(read-string (str (first stack)))
   (mapv str (rest stack))])

(defn parse-stacks
  [stacks]
  (->> stacks
       ;; transposes columns to rows
       (apply map vector)
       ;; reassembles as strings
       (map str/join)
       ;; Skip the first row (containing "[")
       (drop 1)
       ;; Actual stacks are now every 4th row
       (take-nth 4)
       ;; Reverse each stack NZ1 -> 1ZN
       (map str/reverse)
       ;; Remove whitespace at ends
       (map str/trim)
       ;; Parse into number and letters
       (map parse-stack)
       ;; Pack into a map
       (into {})))

(defn parse-move
  [line]
  (mapv read-string (re-seq #"\d+" line)))

(defn parse-moves
  [moves]
  (map parse-move moves))

(defn parse
  [input]
  (let [[stacks moves] (u/split-at-blankline input)]
    {:stacks (parse-stacks stacks)
     :moves  (parse-moves moves)}))

(def day05-input (parse (u/puzzle-input "2022/day05-input.txt")))

(defn take-crates-2 [num stacks src]
  (take-last num (stacks src)))

(defn take-crates-1 [num stacks src]
  (reverse (take-crates-2 num stacks src)))

(defn apply-move
  [take-fn stacks [num src dst]]
  (-> stacks
      (update src #(vec (drop-last num %)))
      (update dst #(apply conj % (take-fn num stacks src)))))

(def apply-move-1 (partial apply-move take-crates-1))
(def apply-move-2 (partial apply-move take-crates-2))

(defn final-arrangement-1
  [{:keys [stacks moves]}]
  (reduce apply-move-1 stacks moves))

(defn final-arrangement-2
  [{:keys [stacks moves]}]
  (reduce apply-move-2 stacks moves))

(defn stack-tops
  [stacks]
  (let [n (count stacks)]
    (->> (map stacks (range 1 (inc n)))
         (map peek)
         (str/join))))

(defn day05-part1-soln
  "After the rearrangement procedure completes, 
   what crate ends up on top of each stack?"
  []
  (-> day05-input final-arrangement-1 stack-tops))

(defn day05-part2-soln
  "The CrateMover 9001 is notable for many new and exciting features: 
   air conditioning, leather seats, an extra cup holder, and 
   the ability to pick up and move multiple crates at once.
   
   After the rearrangement procedure completes, 
   what crate ends up on top of each stack?"
  []
  (-> day05-input final-arrangement-2 stack-tops))