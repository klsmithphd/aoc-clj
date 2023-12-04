(ns aoc-clj.2023.day04
  (:require [clojure.set :as set]
            [clojure.string :as str]))

(def day04_s01_raw
  ["Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53"
   "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19"
   "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1"
   "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83"
   "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36"
   "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"])

(def day04_s01
  [{:winning #{41 48 83 86 17} :card #{83 86  6 31 17  9 48 53}}
   {:winning #{13 32 20 16 61} :card #{61 30 68 82 17 32 24 19}}
   {:winning #{1 21 53 59 44}  :card #{69 82 63 72 16 21 14  1}}
   {:winning #{41 92 73 84 69} :card #{59 84 76 51 58  5 54 83}}
   {:winning #{87 83 26 28 32} :card #{88 30 70 12 93 22 82 36}}
   {:winning #{31 18 13 56 72} :card #{74 77 10 23 35 67 36 11}}])

(defn parse-line
  [line]
  (let [nums (second (str/split line #":\s+"))
        [winning card] (str/split nums #"\s+\|\s+")]
    {:winning (into #{} (map read-string (str/split winning #"\s+")))
     :card (into #{} (map read-string (str/split card #"\s+")))}))

(defn parse
  [input]
  (mapv parse-line input))

(defn card-score
  [{:keys [winning card]}]
  (count (set/intersection winning card)))

(defn points
  [card]
  (let [matches (card-score card)]
    (if (zero? matches)
      0
      (int (Math/pow 2 (dec matches))))))

(defn points-sum
  [cards]
  (reduce + (map points cards)))

(defn update-card-count
  [{:keys [card-cnt total]} score]
  (let [this-count (first card-cnt)
        new-counts (map #(+ this-count %) (take score (rest card-cnt)))]
    {:card-cnt (concat new-counts (drop score (rest card-cnt)))
     :total    (+ total this-count)}))

(update-card-count {:card-cnt [1 1 1 1 1 1] :total 0} 4)
(update-card-count {:card-cnt [2 2 2 2 1] :total 1} 2)
(update-card-count {:card-cnt [4 4 2 1] :total 3} 2)
(update-card-count {:card-cnt [8 6 1] :total 7} 1)
(update-card-count {:card-cnt [14 1] :total 15} 0)
(update-card-count {:card-cnt [1] :total 29} 0)

(reduce update-card-count {:card-cnt [1 1 1 1 1 1] :total 0} [4 2 2 1 0 0])

(defn total-cards
  [cards]
  (let [scores (map card-score cards)
        card-cnt (repeat (count cards) 1)]
    (:total (reduce update-card-count {:card-cnt card-cnt :total 0} scores))))


(defn day04-part1-soln
  [input]
  (points-sum input))

(defn day04-part2-soln
  [input]
  (total-cards input))
