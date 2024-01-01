(ns aoc-clj.2021.day18
  "Solution to https://adventofcode.com/2021/day/18"
  (:require [clojure.zip :as zip]
            [aoc-clj.utils.core :as u]))

(defn parse
  [input]
  (map read-string input))

(defn iter-zip [zipper]
  (->> zipper
       (iterate zip/next)
       (take-while (complement zip/end?))))

(defn depth
  [loc]
  (count (zip/path loc)))

(defn ready-to-explode?
  [loc]
  (if (and (zip/branch? loc)
           (not (zip/branch? (-> loc zip/down)))
           (not (zip/branch? (-> loc zip/down zip/right))))
    (>= (depth loc) 4)
    false))

(defn find-exploder
  [zipper]
  (->> zipper iter-zip (filter ready-to-explode?) first))

(defn ready-to-split?
  [loc]
  (let [val (zip/node loc)]
    (and (number? val)
         (>= val 10))))

(defn find-splitter
  [zipper]
  (->> zipper iter-zip (filter ready-to-split?) first))

(defn follow-left
  [loc]
  (if (not (zip/branch? loc))
    loc
    (follow-left (-> loc zip/down))))

(defn follow-right
  [loc]
  (if (not (zip/branch? loc))
    loc
    (follow-right (-> loc zip/down zip/right))))

(defn nearest-left
  [loc]
  (let [parent (zip/up loc)]
    (if (nil? parent)
      nil
      (let [left (zip/down parent)]
        (if (= loc left)
          (nearest-left parent)
          (follow-right left))))))

(defn nearest-right
  [loc]
  (let [parent (zip/up loc)]
    (if (nil? parent)
      nil
      (let [right (-> parent zip/down zip/right)]
        (if (= loc right)
          (nearest-right parent)
          (follow-left right))))))

(defn add-to-exploder
  [find-fn val loc]
  (let [edit-node (find-fn loc)]
    (if edit-node
      (find-exploder (zip/vector-zip (zip/root (zip/edit edit-node + val))))
      (find-exploder (zip/vector-zip (zip/root loc))))))

(defn replace-exploder
  [loc]
  (zip/vector-zip (zip/root (zip/replace loc 0))))

(defn reduce-explode
  [to-explode]
  (let [[lval rval] (zip/node to-explode)]
    (->> to-explode
         (add-to-exploder nearest-left lval)
         (add-to-exploder nearest-right rval)
         replace-exploder)))

(defn reduce-split
  [to-split]
  (let [val (zip/node to-split)
        left (quot val 2)
        right (- val left)]
    (zip/vector-zip (zip/root (zip/replace to-split [left right])))))

(defn number-reduce
  [n]
  (loop [z (zip/vector-zip n)]
    (let [exploder (find-exploder z)
          splitter (find-splitter z)]
      (if (and (nil? exploder) (nil? splitter))
        (zip/root z)
        (if exploder
          (recur (reduce-explode exploder))
          (recur (reduce-split splitter)))))))

(defn snailfish-add
  [a b]
  (number-reduce (vector a b)))

(defn magnitude
  [n]
  (if (number? n)
    n
    (let [left (first n)
          right (second n)]
      (+ (* 3 (magnitude left))
         (* 2 (magnitude right))))))

(defn shift-sum
  [row]
  (map #(magnitude (snailfish-add (first row) %)) (rest row)))

(defn largest-magnitude-sum
  [numbers]
  (let [shifts (range (count numbers))]
    (->> (map #(u/rotate % numbers) shifts)
         (map shift-sum)
         flatten
         (apply max))))

(defn day18-part1-soln
  [input]
  (magnitude (reduce snailfish-add input)))

(defn day18-part2-soln
  [input]
  (largest-magnitude-sum input))