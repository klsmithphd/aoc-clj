(ns aoc-clj.2023.day15
  (:require [clojure.string :as str]))

(defn parse
  [input]
  (str/split (first input) #","))

(defn hash-char
  [acc char]
  (-> (+ acc (int char))
      (* 17)
      (rem 256)))

(defn hash-alg
  [chars]
  (reduce hash-char 0 chars))

(defn hash-sum
  [input]
  (reduce + (map hash-alg input)))

;; TODO change the utility fn to take a predicate instead of a value
(defn index-of
  [pred coll]
  (ffirst (filter (comp pred second) (map-indexed vector coll))))

(defn match-lens
  [label [lens-label _]]
  (= label lens-label))

(defn seq-add
  [boxes step]
  (let [[label r] (str/split step #"=")
        box-id (hash-alg label)
        lens (read-string r)
        index (index-of (partial match-lens label) (boxes box-id))]
    (if index
      (update boxes box-id #(assoc % index [label lens]))
      (update boxes box-id #(if (seq %) (conj % [label lens]) [[label lens]])))))

(defn vec-without
  [v index]
  (let [[l r] (split-at index v)]
    (into [] (concat l (rest r)))))

(defn seq-remove
  [boxes step]
  (let [label (subs step 0 (dec (count step)))
        box-id (hash-alg label)
        index (index-of (partial match-lens label) (boxes box-id))]
    (if index
      (update boxes box-id #(vec-without % index))
      boxes)))

(defn sequence-step
  [boxes step]
  (if (some #{\=} step)
    (seq-add boxes step)
    (seq-remove boxes step)))

(defn lens-arrangement
  [steps]
  (reduce sequence-step {} steps))

(defn box-focusing-power
  [[box-id lenses]]
  (* (inc box-id)
     (reduce +
             (map-indexed (fn [idx [_ lens]] (* (inc idx) lens)) lenses))))

(defn focusing-power
  [steps]
  (->> (lens-arrangement steps)
       (map box-focusing-power)
       (reduce +)))

(defn day15-part1-soln
  [input]
  (hash-sum input))

(defn day15-part2-soln
  [input]
  (focusing-power input))