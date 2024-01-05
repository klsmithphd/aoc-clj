(ns aoc-clj.2023.day24
  (:require [clojure.math :as math]
            [clojure.math.combinatorics :as combo]
            [clojure.core.matrix :as matrix]))

(matrix/set-current-implementation :vectorz)

(defn parse-line
  [line]
  (let [[a b c d e f] (map read-string (re-seq #"\-?\d+" line))]
    [[a b c] [d e f]]))

(defn parse
  [input]
  (map parse-line input))

(defn slope-and-intercept-xy
  "Given the x, y initial position and x, y velocity components,
   compute the slope and intercept of the equation of the y-vs-x line"
  [[[px py _] [vx vy _]]]
  (let [slope (/ vy vx)]
    [slope (- py (* slope px))]))

(defn in-past?
  "Determines if the x position provided would have occurred before t = 0"
  [x [[px _ _] [vx _ _]]]
  (neg? (/ (- x px) vx)))

(defn hailstone-intersect-xy
  "Determine where two hailstones will intersect, considering only x and y
   values. 
   
   Will return:
     `:parallel` if the lines never intersect
     `:past`     if the intersection occurs in the past for either hailstone
     otherwise the [x y] position of the intersection"
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
  "Determines whehter the [x y] position fits within the min/max area
   (inclusive)"
  [[mn mx] [x y]]
  (and (<= mn x mx) (<= mn y mx)))

(defn intersections-within-area
  "Find all of the hailstones that intersect with each other in the
   x-y dimension only within the area limits prescribed"
  [limits hailstones]
  (->> (combo/combinations hailstones 2)
       (map hailstone-intersect-xy)
       (remove keyword?)
       (filter (partial within-area? limits))
       count))

;; TODO -- Document this
;; I worked out these formulas by hand with pen and paper and they're
;; essentially impenetrable without the derivation
;; It was almost readable when it was set up as a simple matrix expression to
;; solve, but the double-precision values for the position coordinates are
;; too large.
(defn rock-parameters
  "Given three unique hailstones, compute the parameters (initial position
   and velocity expressed as a six-element vector) of a rock that will
   collide with all hailstones"
  [h0 h1 h2]
  (let [[[p0x p0y p0z] [v0x v0y v0z]] h0
        [[p1x p1y p1z] [v1x v1y v1z]] h1
        [[p2x p2y p2z] [v2x v2y v2z]] h2
        A (- v1y v0y)
        B (- v0x v1x)
        C (- p0y p1y)
        D (- p1x p0x)
        E (- v1z v0z)
        F (- p0z p1z)
        G (- v2y v0y)
        H (- v0x v2x)
        I (- p0y p2y)
        J (- p2x p0x)
        K (- v2z v0z)
        L (- p0z p2z)
        crs0xy (- (* p0x v0y) (* p0y v0x))
        crs0yz (- (* p0y v0z) (* p0z v0y))
        crs0zx (- (* p0z v0x) (* p0x v0z))
        crs1xy (- (* p1x v1y) (* p1y v1x))
        crs1yz (- (* p1y v1z) (* p1z v1y))
        crs1zx (- (* p1z v1x) (* p1x v1z))
        crs2xy (- (* p2x v2y) (* p2y v2x))
        crs2yz (- (* p2y v2z) (* p2z v2y))
        crs2zx (- (* p2z v2x) (* p2x v2z))
        U (- crs1xy crs0xy)
        V (- crs2xy crs0xy)
        W (- crs1yz crs0yz)
        X (- crs2yz crs0yz)
        Y (- crs1zx crs0zx)
        Z (- crs2zx crs0zx)
        mat (matrix/array
             [[A     B  0      C   D   0]
              [G     H  0      I   J   0]
              [0     E (- A)   0   F (- C)]
              [0     K (- G)   0   L (- I)]
              [(- E) 0 (- B) (- F) 0 (- D)]
              [(- K) 0 (- H) (- L) 0 (- J)]])
        constants (matrix/array [[U] [V] [W] [X] [Y] [Z]])
        ;; The double-precision position values aren't accurate enough
        ;; so I throw them away and recompute them algebraically.
        [_ _ _ vrx vry vrz] (map math/round (take 6 (matrix/to-vector (matrix/mmul (matrix/inverse mat) constants))))
        prx (/ (-' (-' (*' H U) (* B V))
                   (*' (-' (*' H C) (*' B I)) vrx)
                   (*' (-' (*' H D) (*' B J)) vry))
               (- (*' H A) (*' B G)))
        pry (/ (-' (-' (*' G U) (*' A V))
                   (*' (-' (*' G C) (*' A I)) vrx)
                   (*' (-' (*' G D) (*' A J)) vry))
               (-' (*' G B) (*' A H)))
        prz (/ (-' (-' (*' K W) (*' E X))
                   (*' (-' (*' K F) (*' E L)) vry)
                   (*' (-' (*' E I) (*' K C)) vrz))
               (-' (*' E G) (*' K A)))]
    [prx pry prz vrx vry vrz]))

(defn rock-position-sum
  "Determine the parameters of the rock that will collide with all 
   hailstones and then add the x, y, z values of the initial position"
  [input]
  (->> (apply rock-parameters (take 3 input))
       (take 3)
       (reduce +)
       math/round))

(def part1-limits [200000000000000 400000000000000])

(defn part1
  "Considering only the X and Y axes, check all pairs of hailstones' 
   future paths for intersections. How many of these intersections occur 
   within the test area?"
  [input]
  (intersections-within-area part1-limits input))

(defn part2
  "Determine the exact position and velocity the rock needs to have at 
   time 0 so that it perfectly collides with every hailstone. 
   What do you get if you add up the X, Y, and Z coordinates of that 
   initial position?"
  [input]
  (rock-position-sum input))



