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

(defn disintegratable-count
  [bricks]
  (let [placed-bricks (place-bricks (sort-by lowest-z (map brick-z-rep bricks)))]
    (->> (:bricks placed-bricks)
         (filter #(disintegratable? placed-bricks %))
         count)))

(defn disintegration-chain-step
  [{:keys [bricks]} id brick]
  (let [z-above-brick    (inc (highest-z brick))
        touching-bricks  (->> (map-indexed vector bricks)
                              (filter #(= z-above-brick (lowest-z (second %))))
                              (filter #(set/intersection
                                        (first (vals brick))
                                        (first (vals (second %))))))]
    [id (mapv first touching-bricks)]))

(defn unroll
  [[a b]]
  (if (empty? b)
    [[a]]
    (map #(vector a %) b)))


(defn disintegratable-chain
  "Adjacency list"
  [bricks]
  (let [placed-bricks (->> (map brick-z-rep bricks)
                           (sort-by lowest-z)
                           place-bricks)]
    (->> (map-indexed
          (partial disintegration-chain-step placed-bricks)
          (:bricks placed-bricks))
         (mapcat unroll))))

(def foo [[0 1] [0 2] [1 3] [1 4] [2 3] [2 4] [3 5] [4 5] [5 6] [6]])

(defn indegree
  [adjaceny brick]
  (count (filter #(= brick (second %)) adjaceny)))

(defn children
  [adjacency brick]
  (->>  (filter #(= brick (first %)) adjacency)
        (map second)))

(indegree foo 6)

(defn adj-disintegratable?
  [adjacency brick]
  (let [kids (children adjacency brick)]
    (if (= [nil] kids)
      true
      (every? #(> % 1) (map #(indegree adjacency %) kids)))))

(adj-disintegratable? foo 6)

(defn desc-depth
  [adjacency brick]
  (if (= [nil] (children adjacency brick))
    1
    (reduce + (map #(desc-depth adjacency %) (children adjacency brick)))))

(children foo 5)
(desc-depth foo 5)


(defn day22-part1-soln
  [input]
  (disintegratable-count input))