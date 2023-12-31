(ns aoc-clj.2016.day04
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(def alphabet "abcdefghijklmnopqrstuvwxyz")

(defn parse-line
  [line]
  (let [[l r] (str/split line #"\[")
        name (str/split l #"-")]
    {:encrypted-name (butlast name)
     :sector-id (read-string (last name))
     :checksum (butlast r)}))

(def day04-input (map parse-line (u/puzzle-input "inputs/2016/day04-input.txt")))

(defn freq-letter-compare
  [x y]
  (let [freq-compare (compare (val x) (val y))]
    (if (zero? freq-compare)
      (compare (key x) (key y))
      (- freq-compare))))

(defn real-room?
  [{:keys [:encrypted-name checksum]}]
  (let [freqs (->> (apply str encrypted-name)
                   frequencies
                   (sort freq-letter-compare)
                   (map first)
                   (take 5))]
    (= freqs checksum)))

(defn real-room-sector-id-sum
  [input]
  (->> (filter real-room? input)
       (map :sector-id)
       (reduce +)))

(defn day04-part1-soln
  []
  (real-room-sector-id-sum day04-input))

(defn decipher
  [{:keys [encrypted-name sector-id] :as room}]
  (let [mapping (zipmap alphabet (u/rotate (mod sector-id 26) alphabet))]
    (assoc room :decrypted-name (map #(apply str (map mapping %)) encrypted-name))))

(defn north-pole-objects-room
  [input]
  (->> (filter real-room? input)
       (map decipher)
       (filter #(= ["northpole" "object" "storage"] (:decrypted-name %)))
       first
       :sector-id))

(defn day04-part2-soln
  []
  (north-pole-objects-room day04-input))
