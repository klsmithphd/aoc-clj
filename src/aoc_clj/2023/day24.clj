(ns aoc-clj.2023.day24
  (:require [clojure.math :as math]
            [clojure.math.combinatorics :as combo]
            [clojure.core.matrix :as matrix]
            [net.mikera.vectorz-clj]))

(matrix/set-current-implementation :vectorz)

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

(defn parallel-3d?
  [[[_ v1] [_ v2]]]
  (apply = (map / v2 v1)))

(defn rock-parameters
  [h0 h1 h2]
  (let [[[p0x p0y p0z] [v0x v0y v0z]] h0
        [[p1x p1y p1z] [v1x v1y v1z]] h1
        [[p2x p2y p2z] [v2x v2y v2z]] h2
        crs0xy (- (* p0x v0y) (* p0y v0x))
        crs0yz (- (* p0y v0z) (* p0z v0y))
        crs0zx (- (* p0z v0x) (* p0x v0z))
        crs1xy (- (* p1x v1y) (* p1y v1x))
        crs1yz (- (* p1y v1z) (* p1z v1y))
        crs1zx (- (* p1z v1x) (* p1x v1z))
        crs2xy (- (* p2x v2y) (* p2y v2x))
        crs2yz (- (* p2y v2z) (* p2z v2y))
        crs2zx (- (* p2z v2x) (* p2x v2z))
        mat (matrix/array
             [[(- v1y v0y) (- v0x v1x) 0 (- p0y p1y) (- p1x p0x) 0]
              [(- v2y v0y) (- v0x v2x) 0 (- p0y p2y) (- p2x p0x) 0]
              [0 (- v1z v0z) (- v0y v1y) 0 (- p0z p1z) (- p1y p0y)]
              [0 (- v2z v0z) (- v0y v2y) 0 (- p0z p2z) (- p2y p0y)]
              [(- v0z v1z) 0 (- v1x v0x) (- p1z p0z) 0 (- p0x p1x)]
              [(- v0z v2z) 0 (- v2x v0x) (- p2z p0z) 0 (- p0x p2x)]])
        constants (matrix/array
                   [[(- crs1xy crs0xy)]
                    [(- crs2xy crs0xy)]
                    [(- crs1yz crs0yz)]
                    [(- crs2yz crs0yz)]
                    [(- crs1zx crs0zx)]
                    [(- crs2zx crs0zx)]])]
    (take 6 (matrix/to-vector (matrix/mmul (matrix/inverse mat) constants)))))

(defn rock-position-sum
  [input]
  (->> (apply rock-parameters (take 3 input))
       (take 3)
       (reduce +)
       math/round))

(def part1-limits [200000000000000 400000000000000])

(defn day24-part1-soln
  [input]
  (intersections-within-area part1-limits input))

(defn day24-part2-soln
  [input]
  (rock-position-sum input))



