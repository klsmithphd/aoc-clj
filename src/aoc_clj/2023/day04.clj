(ns aoc-clj.2023.day04
  (:require [clojure.set :as set]
            [clojure.string :as str]))

(defn parse-line
  [line]
  (let [[_ nums]       (str/split line #":\s+")
        [winning card] (str/split nums #"\s+\|\s+")]
    {:winning (into #{} (map read-string (str/split winning #"\s+")))
     :card    (into #{} (map read-string (str/split card #"\s+")))}))

(defn parse
  [input]
  (mapv parse-line input))

(defn winning-matches
  "Computes how many values overlap between the winning numbers and the card"
  [{:keys [winning card]}]
  (count (set/intersection winning card)))

(defn points
  "The first match makes the card worth one point and each match after the 
   first doubles the point value of that card."
  [card]
  (let [matches (winning-matches card)]
    (if (zero? matches)
      0
      (int (Math/pow 2 (dec matches))))))

(defn points-sum
  "Returns the total of all the point scores for each of the cards"
  [cards]
  (reduce + (map points cards)))

(defn update-card-count
  "Applies the rules of part2 to update the number of remaining cards
   based on the number of winning number matches on the current card,
   i.e., the current card's `score`.
   
   You win copies of the scratchcards below the winning card equal 
   to the number of matches."
  [{:keys [card-cnt total]} score]
  (let [this-count (first card-cnt)]
    {:card-cnt (concat
                (map #(+ this-count %) (take score (rest card-cnt)))
                (drop score (rest card-cnt)))
     :total    (+ total this-count)}))

(defn total-cards
  "Determines the total number of scratchcards you have applying
   the logic of part2"
  [cards]
  (let [scores   (map winning-matches cards)
        card-cnt (repeat (count cards) 1)
        init     {:card-cnt card-cnt :total 0}]
    (:total (reduce update-card-count init scores))))

(defn part1
  "Take a seat in the large pile of colorful cards.
   How many points are they worth in total?"
  [input]
  (points-sum input))

(defn part2
  "Process all of the original and copied scratchcards until no more 
   scratchcards are won. Including the original set of scratchcards, 
   how many total scratchcards do you end up with?"
  [input]
  (total-cards input))
