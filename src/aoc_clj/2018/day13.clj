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
  (let [{:keys [grid width height]} (mg/ascii->MapGrid2D charmap input)]
    (mg/->MapGrid2D width height (into {} (remove #(nil? (val %)) grid)))))

;; Puzzle logic
(defn cart-map
  "Constructs a state map for each of the carts"
  [[pos cart]]
  {:pos pos
   :heading (case cart
              :cart-u :n
              :cart-d :s
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
        0 (grid/turn cart :left)
        1 (grid/turn cart :forward)
        2 (grid/turn cart :right))
      (update :int-cnt inc)))

(defn on-curve
  [curve {:keys [heading] :as cart}]
  (assoc cart :heading
         (case curve
           :curve-45 (case heading
                       :n :e
                       :s :w
                       :e :n
                       :w :s)
           :curve-135 (case heading
                        :s :e
                        :n :w
                        :e :s
                        :w :n))))

(defn tick-cart
  "Update one cart forward in time."
  [tracks cart]
  (let [{:keys [pos] :as new-cart} (grid/forward cart 1)
        track (grid/value tracks pos)]
    (case track
      :intersection (turn-cart new-cart)
      :curve-45     (on-curve track new-cart)
      :curve-135    (on-curve track new-cart)
      new-cart)))

(defn tick
  "Evolve every cart forward by one unit of time."
  [tracks carts]
  (map #(tick-cart tracks %) carts))



(defn not-crashed?
  "Returns true if no crash has yet occurred"
  [carts]
  (->> carts
       (map :pos)
       frequencies
       vals
       (not-any? #(> % 1))))

(defn correct-coordinates
  [height [x y]]
  [x (- height y 1)])

;; The issue with this implementation is that I'm allowing every cart to
;; move --- I need to check the crash condition after each cart has
;; been allowed to go.
;;
;; I'm not taking into account the ordering of the carts properly.
(defn first-crash
  "Returns the location of the first cart crash"
  [{:keys [height] :as tracks}]
  (->> (carts tracks)
       (iterate #(tick tracks %))
       (drop-while not-crashed?)
       first
       (map :pos)
       frequencies
       (filter #(> (val %) 1))
       first
       key
       (correct-coordinates height)
       (str/join ",")))

;; Puzzle solutions
(defn part1
  "To help prevent crashes, you'd like to know the location of the first crash."
  [input]
  (first-crash input))