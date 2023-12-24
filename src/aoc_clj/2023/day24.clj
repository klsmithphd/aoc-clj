(ns aoc-clj.2023.day24
  (:require [clojure.math.combinatorics :as combo]))

(defn parse-line
  [line]
  (let [[a b c d e f] (map read-string (re-seq #"\-?\d+" line))]
    [[a b c] [d e f]]))

(defn parse
  [input]
  (map parse-line input))

(defn slope-and-intercept-xy
  [[[px py _] [vx vy _]]]
  (let [slope (/ vy vx)]
    [slope (- py (* slope px))]))

(defn in-past?
  [x [[px _ _] [vx _ _]]]
  (neg? (/ (- x px) vx)))

(defn hailstone-intersect-xy
  [[h1 h2]]
  (let [[m1 b1] (slope-and-intercept-xy h1)
        [m2 b2] (slope-and-intercept-xy h2)]
    (if (= m1 m2)
      :parallel
      (let [x (/ (- b2 b1) (- m1 m2))]
        (if (or (in-past? x h1) (in-past? x h2))
          :past
          [x (+ (* m1 x) b1)])))))

(defn within-area?
  [[mn mx] [x y]]
  (and (<= mn x mx) (<= mn y mx)))

(defn intersections-within-area
  [limits hailstones]
  (->> (combo/combinations hailstones 2)
       (map hailstone-intersect-xy)
       (remove keyword?)
       (filter (partial within-area? limits))
       count))

(def part1-limits [200000000000000 400000000000000])

(defn day24-part1-soln
  [input]
  (intersections-within-area part1-limits input))



