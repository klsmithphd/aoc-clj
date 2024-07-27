(ns aoc-clj.2016.day15
   "Solution to https://adventofcode.com/2016/day/15"
  (:require [aoc-clj.utils.math :as math]
            [aoc-clj.utils.vectors :as vec]
            [aoc-clj.utils.core :as u]))

;; Input parsing
(defn parse-line
  [line]
  (let [[disc size _ pos] (map read-string (re-seq #"\d+" line))]
    {:disc disc :slot-count size :init-pos pos}))

(defn parse
  [input]
  (map parse-line input))

;; Puzzle logic 
(defn offset-mod
  "Compute the time-position offset and the modulus for a given disc
   
   The offset is just the sum of the disc number and the initial
   (t = 0) position of that disc. The modulus is the size
   (number of positions) for that disc.
   
   The logic of this puzzle is that each disc will be aligned
   to let the capsule fall through when t satisifies the equation
   (t + offset) (mod m)"
  [{:keys [disc slot-count init-pos]}]
  [(+ disc init-pos) slot-count])

(defn disc-coefficients
  "Compute the product of the other discs' moduluses (moduli?)
   and return it, along with that product multiplied by this disc's
   offset.
   
   This uses a rule of modular arithmetic that
   (a + b) mod n = (ma + mb) mod (mn). Here, we're multiplying 
   by all the other disc's moduluses so that we'll have a collection
   of equations that are all mod(product of moduluses) that 
   can then be combined."
  [[[offset _] & others]]
  (let [mult (reduce * (map second others))]
    [mult (* mult offset)]))

(defn all-coefficients
  "For all the discs, compute the coefficients a, b to the equation
   (a * t + b) mod (total_mod) = 0"
  [disc-data]
  (->> (range (count disc-data))
       (map #(u/rotate % disc-data))
       (map disc-coefficients)))


(defn drop-time
  "Computes the time to drop the capsule so that it will reach
   each disc in turn when that disc is at position 0 so that
   the capsule falls through"
  [discs]
  (let [disc-data (map offset-mod discs)        
        ;; The total-mod is the product of all the disc's mods
        total-mod (reduce * (map second disc-data)) 
        ;; Computes (a * t + b) mod (total-mod) = 0 for all discs
        coeffs    (all-coefficients disc-data)
        ;; Sums up the a's and b's, to yield a single equation
        [a b]     (vec/vec-sum coeffs)]
    ;; Computes (a^-1 * (-b)) mod (total-mod)
    (math/mod-mul total-mod (- b) (math/mod-inverse total-mod a))))

(defn add-new-bottom-disc
  "Appends a new disc that is one second below the current lowest
   disc, with 11 positions, and starting at position 0 at t = 0"
  [discs]
  (let [last-disc (:disc (last discs))]
    (conj discs {:disc (inc last-disc) :slot-count 11 :init-pos 0})))

;; Puzzle solutions
(defn part1
  "When is the first time to press the button to get a capsule?"
  [input]
  (drop-time input))

(defn part2
  "When is the first time to press the button to get a capsule
   once the new disc is added?"
  [input]
  (drop-time (add-new-bottom-disc input)))