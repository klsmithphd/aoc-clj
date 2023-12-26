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

(defn highest-z
  [brick]
  (apply max (keys brick)))

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
  (let [z-above-brick    (inc (highest-z brick))
        above-bricks     (filter #(= z-above-brick (lowest-z %)) bricks)
        z-index-wo-brick (merge-with set/difference z-index brick)]
    (or (empty? above-bricks)
        (every? #(at-rest? z-index-wo-brick %) above-bricks))))

(defn disintegratable-bricks
  [bricks]
  (let [placed-bricks (place-bricks (sort-by lowest-z (map brick-z-rep bricks)))]
    (->> (:bricks placed-bricks)
         (filter #(disintegratable? placed-bricks %)))))

(defn disintegratable-count
  [bricks]
  (count (disintegratable-bricks bricks)))

(defn disintegration-chain-step
  [{:keys [bricks]} id brick]
  (let [z-above-brick    (inc (highest-z brick))
        touching-bricks  (->> (map-indexed vector bricks)
                              (filter #(= z-above-brick (lowest-z (second %))))
                              (filter #(seq
                                        (set/intersection
                                         (first (vals brick))
                                         (first (vals (second %)))))))]
    [id (mapv first touching-bricks)]))

(defn supports-graph
  [bricks]
  (let [placed-bricks (->> (map brick-z-rep bricks)
                           (sort-by lowest-z)
                           place-bricks)]
    (into {} (map-indexed #(disintegration-chain-step placed-bricks %1 %2) (:bricks placed-bricks)))))

(defn unroll
  [[a b]]
  (map #(vector a %) b))

(defn adj-list
  [graph]
  (mapcat unroll graph))

(defn supported-by-graph
  [supports-graph]
  (->> (adj-list supports-graph)
       (group-by second)
       (u/fmap #(mapv first %))))

(defn adj-disintegratable?
  [graph indegrees brick]
  (let [kids (graph brick)]
    (if (empty? kids)
      true
      (every? #(> % 1) (map indegrees kids)))))

(declare dependents)
(defn dependents-uncached
  [graph brick]
  (into (set (graph brick)) (mapcat #(dependents graph %) (graph brick))))
(def dependents (memoize dependents-uncached))

(defn dependents-count
  [graph brick]
  (count (dependents graph brick)))

(defn will-fall?
  [supported-by removed brick]
  (empty? (set/difference (set (supported-by brick)) removed)))

(defn fall-number
  [supports supported-by brick]
  (loop [queue (into clojure.lang.PersistentQueue/EMPTY (supports brick))
         removed #{brick}
         number 0]
    (if (not (seq queue))
      number
      (if (will-fall? supported-by removed (peek queue))
        (recur (into (pop queue) (remove (set queue) (supports (peek queue))))
               (conj removed (peek queue))
               (inc number))
        (recur (pop queue) removed number)))))

(defn bricks-to-fall
  [bricks]
  (let [supports     (supports-graph bricks)
        supported-by (supported-by-graph supports)
        brick-ids (keys supports)]
    (->> (map #(fall-number supports supported-by %) brick-ids)
         (reduce +))))

(defn day22-part1-soln
  [input]
  (disintegratable-count input))

(defn day22-part2-soln
  [input]
  (bricks-to-fall input))