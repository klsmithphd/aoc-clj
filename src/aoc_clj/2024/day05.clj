(ns aoc-clj.2024.day05
  "Solution to https://adventofcode.com/2024/day/5"
  (:require [aoc-clj.utils.core :as u]))

;; Input parsing
(defn numbers
  [line]
  (map read-string (re-seq #"\d+" line)))

(defn parse-updates
  [updates]
  (map numbers updates))

(defn parse-ordering
  [ordering]
  (->> (map numbers ordering)
       (group-by first)
       (u/fmap #(set (map second %)))))

(defn parse
  [input]
  (let [[ordering updates] (u/split-at-blankline input)]
    {:ordering (parse-ordering ordering)
     :updates  (parse-updates updates)}))

;; Puzzle logic
(defn update-in-order?
  [ordering [el & others]]
  (every? (complement (get ordering el #{})) others))

(defn chunks
  [coll]
  (take-while some? (iterate next coll)))

(defn in-order?
  [ordering upd]
  (every? #(update-in-order? ordering %) (chunks (reverse upd))))

(defn middle-page
  [coll]
  (let [n (count coll)]
    (nth coll (quot n 2))))

(defn ordered-updates
  [{:keys [ordering updates]}]
  (filter #(in-order? ordering %) updates))

(defn unordered-updates
  [{:keys [ordering updates]}]
  (remove #(in-order? ordering %) updates))

(defn reordered-update
  [ordering [fst & others]]
  (loop [nxt fst ordered [] rst others]
    (if (empty? others)
      ordered
      (let [afters (remove (get ordering nxt #{}) rst)]
        (case (count afters)
          0 (recur (first others) (conj ordered nxt) (rest rst))
          1 (recur (first afters) () ())
          (recur () () ()))))))

(defn ordered-update-middle-pages
  [page-data]
  (->> (ordered-updates page-data)
       (map middle-page)))

;; Puzzle solutions
(defn part1
  [input]
  (reduce + (ordered-update-middle-pages input)))