(ns aoc-clj.2016.day14
  "Solution to https://adventofcode.com/2016/day/14"
  (:require [clojure.string :as str]
            [aoc-clj.utils.digest :as d]))

;; Input parsing
(def parse first)

;; Puzzle logic
(defn has-triple-chars?
  [s]
  (some? (re-find #"(.)\1{2}" s)))

(defn triple-char-key-candidates
  [salt]
  (->> (range)
       (map-indexed (fn [idx itm] [idx (d/md5-str (str salt itm))]))
       (filter #(has-triple-chars? (second %)))))

(defn fivepeat-in-thousand
  [[[idx hashstr] & other-candidates]]
  (let [trip-ch (second (re-find #"(.)\1{2}" hashstr))
        patt    (re-pattern (str/join (repeat 5 trip-ch)))]
    (->> other-candidates
         (filter #(<= (first %) (+ 1000 idx)))
         (some #(re-find patt (second %))))))

(defn pad-keys
  [salt]
  (->> (triple-char-key-candidates salt)
       (partition 1001 1)
       (filter fivepeat-in-thousand)
       (map first)))

(defn last-pad-key
  [salt]
  (->> (pad-keys salt)
       (take 64)
       last
       first))

;; Puzzle solutions
(defn part1
  [input]
  (last-pad-key input))