(ns aoc-clj.2015.day11
  (:require [clojure.string :as str]))

(def day11-input "hxbxwxba")

(defn char->int
  [c]
  (- (int  c) 97)) ; 97 = \a

(defn str->nums
  [s]
  (mapv char->int s))

(defn int->char
  [i]
  (char (+ i 97))) ; 97 = \a

(defn nums->str
  [nums]
  (str/join (map int->char nums)))

(defn increasing-triplet?
  [[a b c]]
  (and (= (- b a) 1)
       (= (- c b) 1)))

(defn increasing-straight?
  [nums]
  (let [triplets (partition 3 1 nums)]
    (some increasing-triplet? triplets)))

(defn matching-pair?
  [[a b]]
  (= a b))

(defn two-distinct-pairs?
  [nums]
  (let [pairs (partition 2 1 nums)
        matches (filter matching-pair? pairs)]
    (>= (count (distinct matches)) 2)))

(def i (char->int \i))
(def l (char->int \l))
(def o (char->int \o))
(def disallowed #{i l o})
(defn no-disallowed?
  [nums]
  (not (some disallowed nums)))

(def valid-password?
  (every-pred
   increasing-straight?
   no-disallowed?
   two-distinct-pairs?))

(defn increment
  [pw]
  (loop [idx 7 nums pw]
    (if (< (inc (nth nums idx)) 26)
      (update nums idx inc)
      (recur (dec idx)
             (assoc nums idx 0)))))

(defn next-password
  [pw]
  (-> pw str->nums increment nums->str))

(defn next-valid-password
  [pw]
  (let [nums (str->nums pw)]
    (->> (iterate increment nums)
         (drop-while (complement valid-password?))
         first
         nums->str)))

(defn day11-part1-soln
  []
  (next-valid-password day11-input))

(defn day11-part2-soln
  []
  (next-valid-password (next-password (day11-part1-soln))))