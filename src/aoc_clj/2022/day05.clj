(ns aoc-clj.2022.day05
  "Solution to https://adventofcode.com/2022/day/5"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

;;;; Input parsing

(defn parse-stack
  "Convert a text string representation given by `text` with the first
   character being the stack id and the remaining characters
   representing the crates in the stack in order from bottom to top"
  [stack]
  [(read-string (str (first stack)))
   (mapv str (rest stack))])

(defn parse-stacks
  "Convert ASCII art text representation of `stacks` into a map
   representing the ordered stack contents, keyed by id"
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
  "Extract the number of crates to move, the source stack and destination
   stack ids"
  [line]
  (mapv read-string (re-seq #"\d+" line)))

(defn parse-moves
  "Convert the text representations into move data"
  [moves]
  (map parse-move moves))

(defn parse
  "Parse the text-based representation of the inputs"
  [input]
  (let [[stacks moves] (u/split-at-blankline input)]
    {:stacks (parse-stacks stacks)
     :moves  (parse-moves moves)}))

;;;; Puzzle logic

(defn take-crates-2
  "The crates taken off of a stack, maintaining order"
  [num stacks src]
  (take-last num (stacks src)))

(defn take-crates-1
  "The crates taken off of a stack as if sequentially popped"
  [num stacks src]
  (reverse (take-crates-2 num stacks src)))

(defn apply-move
  "Execute the move order against the stack. Returns a new state of the stacks.
   `take-fn` is a function which indicates how items are removed from the
   source stack.
   `stacks` is the initial state of the stacks
   `moves` is a vector of 3 integers: number of crates to move, the 
   source stack id, and the destination stack id"
  [take-fn stacks [num src dst]]
  (-> stacks
      (update src #(vec (drop-last num %)))
      (update dst #(apply conj % (take-fn num stacks src)))))

(def apply-move-1 (partial apply-move take-crates-1))
(def apply-move-2 (partial apply-move take-crates-2))

(defn final-arrangement-1
  "The final arrangement of the stacks using the move rules of part 1"
  [{:keys [stacks moves]}]
  (reduce apply-move-1 stacks moves))

(defn final-arrangement-2
  "The final arrangement of the stacks using the move rules of part 2"
  [{:keys [stacks moves]}]
  (reduce apply-move-2 stacks moves))

(defn stack-tops
  "Returns a concatenated string of the letters representing the crates
   at the top of all the stacks in order by their id"
  [stacks]
  (let [n (count stacks)]
    (->> (map stacks (range 1 (inc n)))
         (map peek)
         (str/join))))

;;;; Puzzle solutions

(defn day05-part1-soln
  "After the rearrangement procedure completes, 
   what crate ends up on top of each stack?"
  [input]
  (-> input final-arrangement-1 stack-tops))

(defn day05-part2-soln
  "The CrateMover 9001 is notable for many new and exciting features: 
   air conditioning, leather seats, an extra cup holder, and 
   the ability to pick up and move multiple crates at once.
   
   After the rearrangement procedure completes, 
   what crate ends up on top of each stack?"
  [input]
  (-> input final-arrangement-2 stack-tops))