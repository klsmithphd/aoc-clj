(ns aoc-clj.2017.day24
  "Solution to https://adventofcode.com/2017/day/24"
  (:require
   [aoc-clj.utils.core :as u]))

;; Input parsing
(defn parse-line
  [line]
  (mapv read-string (re-seq #"\d+" line)))

(defn parse
  [input]
  (map parse-line input))

;; Puzzle logic
(defn starters
  "Find the components that can be used to start (has a zero pin side)"
  [components]
  (filter #(some zero? %) components))

(defn other-end
  "Given a component and a pin value, return the other end's value"
  [[a b] val]
  (if (= val a) b a))

(defn compatible?
  "Returns true if a component is compatible (has a end that matches
   the desired number of pins)"
  [pins component]
  (some #(= pins %) component))

(defn edges
  "Returns all the other available components that can work with 
   the current pin count"
  [pins components]
  (filter #(compatible? pins %) components))

(defn- bridges
  "Returns a list of all the longest-possible bridge paths, recursively"
  [components path]
  (let [[pin component] (peek path)
        therest (remove (u/equals? component) components)
        end     (other-end component pin)
        edges   (map #(vector end %) (edges end therest))]
    (if (empty? edges)
      [path]
      (->> edges
           (mapcat #(bridges therest (conj path %)))))))

(defn longest-bridges
  "Returns a coll of all the longest bridge spans possible"
  [components]
  (->> (starters components)
       (map #(vector (vector 0 %)))
       (mapcat #(bridges components %))
       (map #(map second %))))

(defn bridge-strength
  "Returns the strength of a bridge (the sum of the pin counts)"
  [bridge]
  (reduce + (flatten bridge)))

(defn max-bridge-strength
  "Returns the strongest possible bridge strength"
  [components]
  (->> components
       longest-bridges
       (map bridge-strength)
       (apply max)))

(defn max-longest-bridge-strength
  "Returns the strongest possible bridge strength among the longest
   of the bridges"
  [components]
  (let [bridges   (longest-bridges components)
        max-len   (apply max (map count bridges))]
    (->> bridges
         (filter #(= max-len (count %)))
         (map bridge-strength)
         (apply max))))

;; Puzzle solutions
(defn part1
  "What is the strength of the strongest bridge you can make with the 
   components you have available?"
  [input]
  (max-bridge-strength input))

(defn part2
  "What is the strength of the longest bridge you can make? 
   If you can make multiple bridges of the longest length, 
   pick the strongest one."
  [input]
  (max-longest-bridge-strength input))