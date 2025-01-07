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
       (map cart-map)
       set))

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
  [{:keys [carts] :as state} cart]
  (let [{:keys [pos] :as fwd-cart} (grid/forward cart 1)
        track  (grid/value state pos)
        new-cart (case track
                   :intersection (turn-cart fwd-cart)
                   :curve-45     (on-curve track fwd-cart)
                   :curve-135    (on-curve track fwd-cart)
                   fwd-cart)
        final-cart (if ((set (map :pos carts)) pos)
                     (assoc new-cart :collided true)
                     new-cart)]
    (-> state
        (update :carts disj cart)
        (update :carts conj final-cart))))

(defn cart-compare
  "Compares the positions of two carts."
  [carta cartb]
  (let [[xa ya] (:pos carta)
        [xb yb] (:pos cartb)]
    (if (= ya yb)
      (compare xa xb)
      (compare yb ya))))

(defn cart-order
  "Sorts the carts into reading order (top-to-bottom, then left to right)"
  [carts]
  (sort cart-compare carts))

(defn tick
  "Evolve every cart forward by one unit of time."
  [{:keys [carts] :as state}]
  (reduce tick-cart state (cart-order carts)))

(defn correct-coordinates
  [height [x y]]
  [x (- height y 1)])

(defn not-crashed?
  "Returns true if no crash has yet occurred"
  [{:keys [carts]}]
  (->> carts
       (map :collided)
       (not-any? true?)))

(defn first-crash
  "Returns the location of the first cart crash"
  [{:keys [height] :as state}]
  (->> (assoc state :carts (carts state))
       (iterate tick)
       (drop-while not-crashed?)
       first
       :carts
       (filter :collided)
       first
       :pos
       (correct-coordinates height)
       (str/join ",")))

;; Puzzle solutions
(defn part1
  "To help prevent crashes, you'd like to know the location of the first crash."
  [input]
  (first-crash input))