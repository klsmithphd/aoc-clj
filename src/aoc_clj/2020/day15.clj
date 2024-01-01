(ns aoc-clj.2020.day15
  "Solution to https://adventofcode.com/2020/day/15"
  (:require [clojure.string :as str]))

(defn parse
  [input]
  (map read-string (str/split (first input) #",")))

(defn rules
  [nums lastnum]
  (let [positions (nums lastnum)
        size      (count positions)]
    (if (= 1 size)
      0
      (- (reduce - (subvec positions (- size 2) size))))))

(defn conjv
  [v x]
  (if (nil? v)
    [x]
    (conj v x)))

(defn memory-seq
  [starters limit]
  (let [start-cnt (count starters)]
    (loop [theseq (vec starters)
           lastnum (last starters)
           nums (zipmap starters (mapv vector (range start-cnt)))
           cnt  start-cnt]
      (if (<= limit cnt)
        (take limit theseq)
        (let [nextnum (rules nums lastnum)]
          (recur (conj theseq nextnum)
                 nextnum
                 (update nums nextnum #(conjv % cnt))
                 (inc cnt)))))))

(defn day15-part1-soln
  [input]
  (last (memory-seq input 2020)))

(defn day15-part2-soln
  [input]
  (last (memory-seq input 30000000)))
