(ns aoc-clj.2023.day07
  (:require [clojure.string :as str]))

(def hand-type-ranks
  {:five-of-a-kind  6
   :four-of-a-kind  5
   :full-house      4
   :three-of-a-kind 3
   :two-pair        2
   :one-pair        1
   :high-card       0})

(defn card-map
  [ch]
  (case ch
    \A 14
    \K 13
    \Q 12
    \J 11
    \T 10
    (read-string (str ch))))

(defn parse-hand
  [s]
  (mapv card-map s))

(defn parse-row
  [row]
  (let [[hand-str bid-str] (str/split row #" ")]
    {:hand (parse-hand hand-str)
     :bid  (read-string bid-str)}))

(defn parse
  [input]
  (map parse-row input))

(defn hand-type
  "Returns the hand *type*"
  [hand]
  (let [freqs (vec (sort (vals (frequencies hand))))]
    (case freqs
      [5]         :five-of-a-kind
      [1 4]       :four-of-a-kind
      [2 3]       :full-house
      [1 1 3]     :three-of-a-kind
      [1 2 2]     :two-pair
      [1 1 1 2]   :one-pair
      [1 1 1 1 1] :high-card)))

(defn jack->joker
  "Change the numerical value of Jacks (11) to Jokers (0) while
   leaving all other numbers untouched"
  [num]
  (case num
    11 0
    num))

(defn jokerize-hand
  "Replace Jacks with Jokers in the hand"
  [hand]
  (update hand :hand #(mapv jack->joker %)))

(defn max-occurring
  "Returns the most frequently occurring item in the collection"
  [cards]
  (->> (frequencies cards)
       (sort-by val >)
       first
       key))

(defn effective-hand
  "Returns the effective hand, where jokers are replaced with cards that
   maximize the rank of the hand."
  [hand]
  (let [joker-count  (count (filter zero? hand))]
    (if (or (zero? joker-count) (= 5 joker-count))
      hand
      (let [normal-cards (filter pos? hand)
            max-card (max-occurring normal-cards)
            mapping (fn [card]
                      (case card
                        0 max-card
                        card))]
        (mapv mapping hand)))))

(defn compare-hands
  "Compare two hands, returning a negative number if a < b, 0 if a = b, or
   a positive number if a > b"
  ([a b]
   (compare-hands false a b))
  ([jokers? a b]
   (let [hand-a (:hand a)
         hand-b (:hand b)
         rank-a (hand-type-ranks (if jokers?
                                   (hand-type (effective-hand hand-a))
                                   (hand-type hand-a)))
         rank-b (hand-type-ranks (if jokers?
                                   (hand-type (effective-hand hand-b))
                                   (hand-type hand-b)))]
     (if (= rank-a rank-b)
       (compare hand-a hand-b)
       (compare rank-a rank-b)))))

(defn winnings
  "You can determine the total winnings of this set of hands by adding up
   the result of multiplying each hand's bid with its rank "
  ([hands]
   (winnings hands false))
  ([hands jokers?]
   (->> (sort (partial compare-hands jokers?) hands)
        (map-indexed #(* (inc %1) (:bid %2)))
        (reduce +))))

(defn joker-winnings
  "Computes the winnings from the hands, accounting for joker rules"
  [hands]
  (winnings (map jokerize-hand hands) true))

(defn day07-part1-soln
  "Find the rank of every hand in your set. What are the total winnings?"
  [input]
  (winnings input))

(defn day07-part2-soln
  "Using the new joker rule, find the rank of every hand in your set. 
   What are the new total winnings?"
  [input]
  (joker-winnings input))
