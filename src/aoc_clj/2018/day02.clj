(ns aoc-clj.2018.day02
  "Solution to https://adventofcode.com/2018/day/2"
  (:require [clojure.string :as str]))

(def parse identity)

(defn repeat-count
  [repeat-num coll]
  (if (some #(= repeat-num %) coll) 1 0))

(defn repeat-counter
  [repeat-num box-id]
  (->> box-id
       frequencies
       (map second)
       (repeat-count repeat-num)))

(defn compute-checksum
  "Computes the 'checksum' which is defined as the product
   between the number of box-ids that contain any pair of matching
   characters and the number of box-ids that contain a triple of matching
   characters"
  [box-ids]
  (let [doubles (reduce + (map (partial repeat-counter 2) box-ids))
        triples (reduce + (map (partial repeat-counter 3) box-ids))]
    (* doubles triples)))

(defn char-cmp
  [c1 c2]
  (if (= c1 c2) 0 1))

(defn edit-distance
  [str1 str2]
  (reduce + (map char-cmp str1 str2)))

(defn chars-in-common
  [str1 str2]
  (->> (map list str1 str2)
       (filter #(= (first %) (second %)))
       (map first)
       str/join))

(defn find-closest-boxids
  "Given a list of box-ids, find the pair that is only one off in edit distance"
  [box-ids]
  (let [box-id-count (count box-ids)
        distances (mapcat #(map (partial edit-distance %) box-ids) box-ids)
        closest (ffirst (filter #(= 1 (second %)) (map-indexed #(list %1 %2) distances)))
        index1 (mod closest box-id-count)
        index2 (quot closest box-id-count)]
    (chars-in-common (nth box-ids index1) (nth box-ids index2))))

(defn part1
  [input]
  (compute-checksum input))

(defn part2
  [input]
  (find-closest-boxids input))
