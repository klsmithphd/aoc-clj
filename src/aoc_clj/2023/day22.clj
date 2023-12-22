(ns aoc-clj.2023.day22
  (:require [clojure.set :as set]
            [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(defn parse-line
  [line]
  (mapv u/str->vec (str/split line #"~")))

(defn parse
  [input]
  (map parse-line input))

(defn lowest-z
  [brick]
  (apply min (keys brick)))

(defn brick-z-rep
  [[[sx sy sz] [ex ey ez]]]
  (into {} (for [z (range sz (inc ez))]
             [z (set (for [y (range sy (inc ey))
                           x (range sx (inc ex))]
                       [x y]))])))

(defn at-rest?
  [z-index brick]
  (let [brick-lowest-z (lowest-z brick)]
    (if (= 1 brick-lowest-z)
      true
      (seq (set/intersection (first (vals brick))
                             (z-index (dec brick-lowest-z)))))))

(defn lower-brick
  [z-index brick]
  (loop [next-brick brick]
    (if (at-rest? z-index next-brick)
      next-brick
      (recur (u/kmap dec next-brick)))))

(defn place-brick
  [{:keys [z-index bricks]} brick]
  (let [updated-brick (lower-brick z-index brick)]
    {:z-index (merge-with into z-index updated-brick)
     :bricks  (conj bricks updated-brick)}))

(defn place-bricks
  [bricks]
  (reduce place-brick {:z-index {} :bricks []} bricks))

(defn disintegratable?
  [{:keys [z-index bricks]} brick]
  (let [brick-z      (lowest-z brick)
        above-bricks (filter #(= (inc brick-z) (lowest-z %)) bricks)
        z-index-wo-brick (merge-with set/difference z-index brick)]
    (or (empty? above-bricks)
        (every? #(at-rest? z-index-wo-brick %) above-bricks))))

(defn disintegratable-count
  [bricks]
  (let [placed-bricks (place-bricks (sort-by lowest-z (map brick-z-rep bricks)))]
    (->> (:bricks placed-bricks)
         (filter #(disintegratable? placed-bricks %))
         count)))

(defn day22-part1-soln
  [input]
  (disintegratable-count input))