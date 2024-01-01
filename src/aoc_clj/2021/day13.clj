(ns aoc-clj.2021.day13
  "Solution to https://adventofcode.com/2021/day/13"
  (:require [clojure.string :as str]))

(defn parse-coord
  [line]
  (mapv read-string (str/split line #",")))

(defn parse-fold
  [line]
  (let [[l r] (str/split line #"=")]
    {:axis (keyword (str (last l)))
     :loc  (read-string r)}))

(defn parse
  [input]
  (let [[coords folds] (-> (str/join "\n" input)
                           (str/split #"\n\n"))]
    {:coords (map parse-coord (str/split coords #"\n"))
     :folds  (map parse-fold  (str/split folds #"\n"))}))

(defn fold-y
  [loc [x y]]
  (if (<= y loc)
    [x y]
    [x (- (* 2 loc) y)]))

(defn fold-x
  [loc [x y]]
  (if (<= x loc)
    [x y]
    [(- (* 2 loc) x) y]))

(defn fold
  [coords {:keys [axis loc]}]
  (case axis
    :y (set (map (partial fold-y loc) coords))
    :x (set (map (partial fold-x loc) coords))))

(defn dots-after-first-fold
  [{:keys [coords folds]}]
  (count (fold coords (first folds))))

(defn complete-folds
  [{:keys [coords folds]}]
  (reduce fold coords folds))

(defn pprint-paper
  [coords]
  (let [xmax (apply max (map first coords))
        ymax (apply max (map second coords))
        chars (for [y (range (inc ymax))
                    x (range (inc xmax))]
                (if (coords [x y]) \# \ ))]
    (str/join "\n"
              (map #(apply str %)
                   (partition (inc xmax) chars)))))

(defn day13-part1-soln
  [input]
  (dots-after-first-fold input))

(defn day13-part2-soln
  [input]
  (comment
    (->> (complete-folds input)
         pprint-paper
         println))
  "HKUJGAJZ")