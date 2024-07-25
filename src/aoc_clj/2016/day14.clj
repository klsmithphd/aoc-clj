(ns aoc-clj.2016.day14
  "Solution to https://adventofcode.com/2016/day/14"
  (:require [clojure.string :as str]
            [aoc-clj.utils.digest :as d]))

;; Input parsing
(def parse first)

;; Puzzle logic
(defn stretched-md5-str
  "Computed the stretched MD5 hash of the supplied string by iteratively
   computing the hash of hashes for a total of 2017 total hashings"
  [s]
  (first (drop 2017 (iterate d/md5-str s))))

(def md5 d/md5-str)
(def smd5 stretched-md5-str)

(defn has-triple-chars?
  "Returns true if the string has a sequence of a character being
   repeated three times consecutively"
  [s]
  (some? (re-find #"(.)\1{2}" s)))

(defn triple-char-key-candidates
  "An infinite sequence of index, hash tuples for which the hashes
   contain a character repeated three times consecutively"
  [hash-fn salt]
  (->> (range)
       (map-indexed (fn [idx itm] [idx (hash-fn (str salt itm))]))
       (filter #(has-triple-chars? (second %)))))

(defn fivepeat-in-thousand
  "Given a window of a current index-hash pair as well as subsequent
   index-hash pairs, return true if there's a hash in the next
   1000 candidates that has a sequence of five consecutive characters
   matching the current triplet"
  [[[idx hashstr] & other-candidates]]
  (let [trip-ch (second (re-find #"(.)\1{2}" hashstr))
        patt    (re-pattern (str/join (repeat 5 trip-ch)))]
    (->> other-candidates
         (filter #(<= (first %) (+ 1000 idx)))
         (some #(re-find patt (second %))))))

(defn pad-keys
  "Returns an infinite sequence of all the indices that produce valid
   one-time pad keys for the given salt"
  [hash-fn salt]
  (->> (triple-char-key-candidates hash-fn salt)
       (partition 1001 1)
       (filter fivepeat-in-thousand)
       (map ffirst)))

(defn last-pad-key
  "Returns the last (64th) one-time pad key for the given salt"
  [hash-fn salt]
  (->> (pad-keys hash-fn salt)
       (take 64)
       last))

;; Puzzle solutions
(defn part1
  "What index produces the 64th one-time pad key"
  [input]
  (last-pad-key md5 input))

(defn part2
  "What index produces the 64th one-time pad key with key stretching implemented"
  [input]
  (last-pad-key smd5 input))
