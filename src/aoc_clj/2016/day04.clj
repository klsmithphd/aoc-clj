(ns aoc-clj.2016.day04
  "Solution to https://adventofcode.com/2016/day/4"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

;; Constants
(def alphabet "abcdefghijklmnopqrstuvwxyz")

;; Input parsing
(defn parse-line
  [line]
  (let [[_ code checksum] (re-find #"([\w\-]*)\[(\w{5})\]" line)
        segments (str/split code #"-")]
    {:encrypted-name (butlast segments)
     :sector-id (read-string (last segments))
     :checksum checksum}))

(defn parse
  [input]
  (map parse-line input))

;; Puzzle logic
(defn freq-letter-compare
  "A compare function for sorting letter-frequency key-value pairs.
   Frequencies should be sorted in descending order first, and if
   there are ties, characters should be sorted alphabetically"
  [x y]
  (let [freq-compare (compare (val y) (val x))]
    (if (zero? freq-compare)
      (compare (key x) (key y))
      freq-compare)))

(defn real-room?
  "A room is real if the checksum is the five most common characters
   in the enrcrypted name, in order, with ties broken by alphabetization"
  [{:keys [encrypted-name checksum]}]
  (let [frequent-chars (->> (str/join encrypted-name)
                            frequencies
                            (sort freq-letter-compare)
                            (map key)
                            (take 5)
                            str/join)]
    (= frequent-chars checksum)))

(defn real-room-sector-id-sum
  "The sum of the sector ids of all the real rooms"
  [input]
  (->> (filter real-room? input)
       (map :sector-id)
       (reduce +)))

(defn decipher
  "Update the room to add a decrypted name to all room names"
  [{:keys [encrypted-name sector-id] :as room}]
  (let [charmap (zipmap alphabet (u/rotate (mod sector-id 26) alphabet))
        decode  (fn [s] (str/join (map charmap s)))]
    (assoc room :decrypted-name (map decode encrypted-name))))

(defn north-pole-objects-room
  "Find all the valid rooms, decrypt their names, and find the one
   that matches `northpole object storage`."
  [input]
  (->> (filter real-room? input)
       (map decipher)
       (filter #(= ["northpole" "object" "storage"] (:decrypted-name %)))
       first
       :sector-id))

;; Puzzle solutions
(defn part1
  "Sum of the sector ids of the real rooms"
  [input]
  (real-room-sector-id-sum input))

(defn part2
  "Sector id of the room where North Pole objects are stored"
  [input]
  (north-pole-objects-room input))
