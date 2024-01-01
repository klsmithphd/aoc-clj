(ns aoc-clj.2021.day12
  "Solution to https://adventofcode.com/2021/day/12"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(defn parse-line
  [line]
  (let [[a b] (str/split line #"-")]
    [[a b] [b a]]))

(defn parse
  [lines]
  (u/fmap (partial map second) (group-by first (mapcat parse-line lines))))

(defn big-cave?
  [cave]
  (Character/isUpperCase (first cave)))

(defn allowed?
  [path candidate]
  (or (big-cave? candidate)
      ((complement (set path)) candidate)))

(defn small-cave-quota-used?
  [path]
  (->> (filter (complement big-cave?) path)
       frequencies
       vals
       (some #(= 2 %))))

(defn allowed-part2?
  [path candidate]
  (and (not= "start" candidate)
       (or (big-cave? candidate)
           (if (small-cave-quota-used? path)
             (<  (count (filter #{candidate} path)) 1)
             (<= (count (filter #{candidate} path)) 1)))))

(defn end?
  [path]
  (= "end" (last path)))

(defn map-cave-path
  ([graph path]
   (map-cave-path graph path allowed?))
  ([graph path rule]
   (let [next-nodes  (if (end? path)
                       []
                       (->> (get graph (last path))
                            (filter #(rule path %))))]
     (if (empty? next-nodes)
       (if (end? path)
         path
         nil)
       (mapcat #(map-cave-path graph (conj path %) rule) next-nodes)))))

(defn split-by
  "From https://clojuredocs.org/clojure.core/split-with"
  [pred coll]
  (lazy-seq
   (when-let [s (seq coll)]
     (let [[xs ys] (split-with pred s)]
       (if (seq xs)
         (cons xs (split-by pred ys))
         (let [!pred (complement pred)
               skip (take-while !pred s)
               others (drop-while !pred s)
               [xs ys] (split-with pred others)]
           (cons (concat skip xs)
                 (split-by pred ys))))))))

(defn map-cave
  ([graph]
   (map-cave graph allowed?))
  ([graph rule]
   (split-by (complement #{"start"}) (map-cave-path graph ["start"] rule))))

(defn day12-part1-soln
  [input]
  (count (map-cave input)))

(defn day12-part2-soln
  [input]
  (count (map-cave input allowed-part2?)))