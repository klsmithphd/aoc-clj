(ns aoc-clj.2017.day24
  "Solution to https://adventofcode.com/2017/day/23"
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
  [components]
  (filter #(some zero? %) components))

(defn other-end
  [[a b] val]
  (if (= val a) b a))

(defn compatible?
  [pins component]
  (some #(= pins %) component))

(defn edges
  [pins components]
  (filter #(compatible? pins %) components))

(defn bridges
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
  [components]
  (->> (starters components)
       (map #(vector (vector 0 %)))
       (mapcat #(bridges components %))
       (map #(map second %))))

(defn bridge-strength
  [bridge]
  (reduce + (flatten bridge)))

(defn max-bridge-strength
  [components]
  (->> components
       longest-bridges
       (map bridge-strength)
       (apply max)))

(defn max-longest-bridge-strength
  [components]
  (let [bridges   (longest-bridges components)
        max-len   (apply max (map count bridges))]
    (->> bridges
         (filter #(= max-len (count %)))
         (map bridge-strength)
         (apply max))))

;; Puzzle solutions
(defn part1
  [input]
  (max-bridge-strength input))

(defn part2
  [input]
  (max-longest-bridge-strength input))