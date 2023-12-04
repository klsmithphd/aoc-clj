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

(defn points
  [{:keys [winning card]}]
  (let [overlap (count (set/intersection winning card))]
    (if (zero? overlap)
      0
      (int (Math/pow 2 (dec overlap))))))

(defn points-sum
  [cards]
  (reduce + (map points cards)))

(defn day04-part1-soln
  [input]
  (points-sum input))