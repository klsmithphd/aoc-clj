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


(defn compare-hands
  [a b]
  (let [hand-a (:hand a)
        hand-b (:hand b)
        rank-a (hand-type-ranks (hand-type hand-a))
        rank-b (hand-type-ranks (hand-type hand-b))]
    (if (= rank-a rank-b)
      (compare hand-a hand-b)
      (compare rank-a rank-b))))

(defn winnings
  [hands]
  (->> (sort compare-hands hands)
       (map-indexed #(* (inc %1) (:bid %2)))
       (reduce +)))

(defn day07-part1-soln
  [input]
  (winnings input))

