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
  "Returns true if the page (sub-)updates are in-order"
  [ordering [el & others]]
  (every? (complement (get ordering el #{})) others))

(defn chunks
  "Returns a seq of each successive *chunk* of the collection.
   
   Ex:
   (chunks [1 2 3 4])
   Returns:
   [[1 2 3 4]
    [2 3 4]
    [3 4]
    [4]]"
  [coll]
  (take-while some? (iterate next coll)))

(defn in-order?
  "Returns true if the entire page update is in order"
  [ordering upd]
  (every? #(update-in-order? ordering %) (chunks (reverse upd))))

(defn middle-page
  "Returns the middle page number out of the odd-numbered page updates"
  [coll]
  (let [n (count coll)]
    (nth coll (quot n 2))))

(defn ordered-updates
  "Returns all of the page updates that are in order"
  [{:keys [ordering updates]}]
  (filter #(in-order? ordering %) updates))

(defn unordered-updates
  "Returns all of the page updates that are out of order"
  [{:keys [ordering updates]}]
  (remove #(in-order? ordering %) updates))

(defn reordered-update
  "Returns a page update in correct order."
  [ordering upd]
  (loop [ordered [] [nxt & rst] upd]
    (if (empty? rst)
      (conj ordered nxt)
      (let [need-to-be-before
            (filter #((get ordering % #{}) nxt) rst)]
        (if (empty? need-to-be-before)
          (recur (conj ordered nxt) rst)
          (recur ordered (concat need-to-be-before
                                 [nxt]
                                 (remove (set need-to-be-before) rst))))))))

(defn ordered-update-middle-pages
  "Returns the middle pages of all the in-order page updates"
  [page-data]
  (->> (ordered-updates page-data)
       (map middle-page)))

(defn reordered-update-middle-pages
  "Returns the middle pages of all the out-of-order page updates
   after they've been put in the correct order"
  [{:keys [ordering] :as page-data}]
  (->> (unordered-updates page-data)
       (map #(reordered-update ordering %))
       (map middle-page)))

;; Puzzle solutions
(defn part1
  "What do you get if you add up the middle page number from those
   correctly-ordered updates?"
  [input]
  (reduce + (ordered-update-middle-pages input)))

(defn part2
  "Find the updates which are not in the correct order. 
   What do you get if you add up the middle page numbers after 
   correctly ordering just those updates?"
  [input]
  (reduce + (reordered-update-middle-pages input)))