(ns aoc-clj.2018.day13
  "Solution to https://adventofcode.com/2018/day/13"
  (:require [clojure.string :as str]
            [aoc-clj.utils.grid :as grid]
            [aoc-clj.utils.grid.mapgrid :as mg]))

;; Constants
(def cart-keys #{:cart-l :cart-r :cart-d :cart-u})

;; Input parsing
(def charmap
  {\\ :curve-135
   \/ :curve-45
   \| :v
   \- :h
   \+ :intersection
   \< :cart-l
   \> :cart-r
   \v :cart-d
   \^ :cart-u
   \  nil})

(defn parse
  [input]
  (let [{:keys [grid width height]} (mg/ascii->MapGrid2D charmap input :down true)]
    (mg/->MapGrid2D width height (into {} (remove #(nil? (val %)) grid)))))

;; Puzzle logic
(defn cart-map
  "Constructs a state map for each of the carts"
  [[pos cart]]
  {:pos pos
   :heading (case cart
              :cart-u :s
              :cart-d :n
              :cart-l :w
              :cart-r :e)
   :int-cnt  0})

(defn carts
  "Returns the carts found within the map."
  [{:keys [grid]}]
  (->> grid
       (filter #(cart-keys (val %)))
       (map cart-map)))

(defn turn-cart
  [{:keys [int-cnt] :as cart}]
  (-> (case (mod int-cnt 3)
        0 (grid/turn cart :right)
        1 (grid/turn cart :forward)
        2 (grid/turn cart :left))
      (update :int-cnt inc)))

(defn on-curve
  [curve {:keys [heading] :as cart}]
  (assoc cart :heading
         (case curve
           :curve-45 (case heading
                       :n :w
                       :s :e
                       :e :s
                       :w :n)
           :curve-135 (case heading
                        :s :w
                        :n :e
                        :e :n
                        :w :s))))

(defn tick-cart
  [tracks cart]
  (let [{:keys [pos] :as new-cart} (grid/forward cart 1)
        track (grid/value tracks pos)]
    (case track
      :intersection (turn-cart new-cart)
      :curve-45     (on-curve track new-cart)
      :curve-135    (on-curve track new-cart)
      new-cart)))

(defn tick
  [tracks carts]
  (map #(tick-cart tracks %) carts))

(defn not-crashed?
  [carts]
  (->> carts
       (map :pos)
       frequencies
       vals
       (not-any? #(> % 1))))

(defn first-crash
  [tracks]
  (->> (carts tracks)
       (iterate #(tick tracks %))
       (drop-while not-crashed?)
       first
       (map :pos)
       frequencies
       (filter #(> (val %) 1))
       first
       key
       (str/join ",")))

;; Puzzle solutions
(defn part1
  [input]
  (first-crash input))