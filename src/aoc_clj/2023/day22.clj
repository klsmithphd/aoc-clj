(ns aoc-clj.2023.day22
  (:require [clojure.set :as set]
            [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(defn parse-line
  [line]
  (mapv u/str->vec (str/split line #"~")))

(defn parse
  [input]
  (sort-by (comp peek first) (map parse-line input)))

(defn expanded-brick-coords
  [[[sx sy sz] [ex ey ez]]]
  (for [z (range sz (inc ez))
        y (range sy (inc ey))
        x (range sx (inc ex))]
    [x y z]))

(defn xy-vals
  [brick]
  (set (map (comp vec #(take 2 %)) brick)))

(defn lowest-z
  [brick]
  (peek (first brick)))

(defn at-rest?
  [z-index brick]
  (if (= 1 (lowest-z brick))
    true
    (seq (set/intersection (xy-vals brick)
                           (z-index (dec (lowest-z brick)))))))

(defn lower-brick
  [z-index brick]
  (loop [next-brick brick]
    (if (at-rest? z-index next-brick)
      next-brick
      (recur (mapv #(update % 2 dec) next-brick)))))

(defn update-z-index
  [index brick]
  (merge-with into index (->> (group-by peek brick)
                              (u/fmap xy-vals))))

(defn place-brick
  [{:keys [z-index bricks]} brick]
  (let [updated-brick (lower-brick z-index brick)]
    {:z-index (update-z-index z-index updated-brick)
     :bricks  (conj bricks updated-brick)}))

(defn place-bricks
  [bricks]
  (reduce place-brick {:z-index {} :bricks []} bricks))




;; Imagine overhead view of grid
;; [x y]
;; and then keep track of each z-depth
;; There's what ever's at z= 1, z = 2, etc.
;; Then each brick can be lowered until it's resting on a brick cell below it
;; We need to retain the identity of bricks because we need to figure out which
;; ones support one another
