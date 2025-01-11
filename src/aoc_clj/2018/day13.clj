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
  [part {:keys [carts removed] :as state} cart]
  (if (and (= :part2 part) (removed cart))
    state
    (let [{:keys [pos] :as fwd-cart} (grid/forward cart 1)
          track  (grid/value state pos)
          new-cart (case track
                     :intersection (turn-cart fwd-cart)
                     :curve-45     (on-curve track fwd-cart)
                     :curve-135    (on-curve track fwd-cart)
                     fwd-cart)
          final-cart (if ((set (map :pos carts)) pos)
                       (assoc new-cart :collided true)
                       new-cart)
          other-cart (first (filter #(= pos (:pos %)) carts))]
      (if (and (= :part2 part) (some? other-cart))
        (-> state
            (update :removed conj cart other-cart)
            (update :carts disj cart other-cart))
        (-> state
            (update :carts disj cart)
            (update :carts conj final-cart))))))

(def tick-cart-p1 (partial tick-cart :part1))
(def tick-cart-p2 (partial tick-cart :part2))

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
  [part {:keys [carts] :as state}]
  (let [ticker (case part
                 :part1 tick-cart-p1
                 :part2 tick-cart-p2)]
    (-> (reduce ticker state (cart-order carts))
        (assoc :removed #{}))))

(def tick-part1 (partial tick :part1))
(def tick-part2 (partial tick :part2))

(defn correct-coordinates
  [height [x y]]
  [x (- height y 1)])

(defn not-crashed?
  "Returns true if no crash has yet occurred"
  [{:keys [carts]}]
  (not-any? :collided carts))

(defn first-crash
  "Returns the location of the first cart crash"
  [{:keys [height] :as state}]
  (->> (merge state {:carts (carts state) :removed #{}})
       (iterate tick-part1)
       (drop-while not-crashed?)
       first
       :carts
       (filter :collided)
       first
       :pos
       (correct-coordinates height)
       (str/join ",")))

(defn last-cart
  "Returns the location of the only remaining cart after crashed carts are removed"
  [{:keys [height] :as state}]
  (->> (merge state {:carts (carts state) :removed #{}})
       (iterate tick-part2)
       (drop-while #(> (count (:carts %)) 1))
       first
       :carts
       first
       :pos
       (correct-coordinates height)
       (str/join ",")))

;; Puzzle solutions
(defn part1
  "To help prevent crashes, you'd like to know the location of the first crash."
  [input]
  (first-crash input))

(defn part2
  "What is the location of the last cart at the end of the first tick where it
   is the only cart left?"
  [input]
  (last-cart input))