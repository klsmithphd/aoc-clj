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
  "Takes the brick specification format from the input and 
   returns a new representation as a map, where the keys are
   the z-values occupied by the brick and the values are a set
   of the [x y] positions occupied by the brick"
  [[[sx sy sz] [ex ey ez]]]
  (into {} (for [z (range sz (inc ez))]
             [z (set (for [y (range sy (inc ey))
                           x (range sx (inc ex))]
                       [x y]))])))

(defn at-rest?
  "Returns true if the brick is resting upon another brick in the
   z-index or on the ground (at z = 0)"
  [z-index brick]
  (let [brick-lowest-z (lowest-z brick)]
    (if (= 1 brick-lowest-z)
      true
      (seq (set/intersection (first (vals brick))
                             (z-index (dec brick-lowest-z)))))))

(defn lower-brick
  "Continuously decrement the z-values of the brick until it comes
   to rest"
  [z-index brick]
  (loop [next-brick brick]
    (if (at-rest? z-index next-brick)
      next-brick
      (recur (u/kmap dec next-brick)))))

(defn place-brick
  "Take a brick snapshot, lower it to its final resting position,
   and update-the z-index of occupied positions."
  [{:keys [z-index bricks]} brick]
  (let [updated-brick (lower-brick z-index brick)]
    {:z-index (merge-with into z-index updated-brick)
     :bricks  (conj bricks updated-brick)}))

(defn place-bricks
  "Take all brick snapshots and return a final at-rest configuration"
  [bricks]
  (reduce place-brick {:z-index {} :bricks []} bricks))

;; (defn disintegratable?
;;   [{:keys [z-index bricks]} brick]
;;   (let [z-above-brick    (inc (highest-z brick))
;;         above-bricks     (filter #(= z-above-brick (lowest-z %)) bricks)
;;         z-index-wo-brick (merge-with set/difference z-index brick)]
;;     (or (empty? above-bricks)
;;         (every? #(at-rest? z-index-wo-brick %) above-bricks))))

;; (defn disintegratable-bricks
;;   [bricks]
;;   (let [placed-bricks (place-bricks (sort-by lowest-z (map brick-z-rep bricks)))]
;;     (->> (:bricks placed-bricks)
;;          (filter #(disintegratable? placed-bricks %)))))

;; (defn disintegratable-count
;;   [bricks]
;;   (count (disintegratable-bricks bricks)))

(defn adjacent-bricks
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
    (into {} (map-indexed #(adjacent-bricks placed-bricks %1 %2) (:bricks placed-bricks)))))

;; (defn disintegratable?
;;   [{:keys [z-index bricks]} brick]
;;   (let [z-above-brick    (inc (highest-z brick))
;;         above-bricks     (filter #(= z-above-brick (lowest-z %)) bricks)
;;         z-index-wo-brick (merge-with set/difference z-index brick)]
;;     (or (empty? above-bricks)
;;         (every? #(at-rest? z-index-wo-brick %) above-bricks))))


;; TODO - Move to graph utils
(defn unroll
  [[a b]]
  (map #(vector a %) b))

;; TODO - Move to graph utils
(defn adj-list
  [graph]
  (mapcat unroll graph))

;; Consider also moving to graph utils
(defn supported-by-graph
  [supports-graph]
  (->> (adj-list supports-graph)
       (group-by second)
       (u/fmap #(mapv first %))))

(defn disintegratable?
  [supports supported-by brick]
  (if (empty? (supports brick))
    true
    (every? #(> % 1) (map #(count (supported-by %)) (supports brick)))))

(defn disintegratable-bricks
  [bricks]
  (let [supports (supports-graph bricks)
        supported-by (supported-by-graph supports)]
    (filter #(disintegratable? supports supported-by %) (keys supports))))


(defn disintegratable-count
  [bricks]
  (count (disintegratable-bricks bricks)))
;; (defn disintegratable?
;;   [supports supported-by brick]
;;   (if (empty? (supports brick))
;;     true
;;     (every? #(> % 1) (map #(count (supported-by %)) (supports brick)))))

;; (defn adj-disintegratable?
;;   [graph indegrees brick]
;;   (let [kids (graph brick)]
;;     (if (empty? kids)
;;       true
;;       (every? #(> % 1) (map indegrees kids)))))

;; (declare dependents)
;; (defn dependents-uncached
;;   [graph brick]
;;   (into (set (graph brick)) (mapcat #(dependents graph %) (graph brick))))
;; (def dependents (memoize dependents-uncached))

;; (defn dependents-count
;;   [graph brick]
;;   (count (dependents graph brick)))

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
  "Figure how the blocks will settle based on the snapshot. 
   Once they've settled, consider disintegrating a single brick; 
   how many bricks could be safely chosen as the one to get disintegrated?"
  [input]
  (disintegratable-count input))

(defn day22-part2-soln
  "For each brick, determine how many other bricks would fall if that brick 
   were disintegrated. 
   What is the sum of the number of other bricks that would fall?"
  [input]
  (bricks-to-fall input))