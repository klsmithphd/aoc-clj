(ns aoc-clj.2020.day07
  "Solution to https://adventofcode.com/2020/day/7"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(defn keywordize
  [adj color]
  (keyword (str/join "-" [adj color])))

(defn bag-description
  [desc]
  (let [[num c1 c2] (str/split (str/trim desc) #"\ ")]
    [(Integer/parseInt num) (keywordize c1 c2)]))

(defn parse-rule
  [rule-str]
  (let [[outer inner] (str/split rule-str #" bags contain ")]
    [(apply keywordize (str/split outer #"\ "))
     (if (str/starts-with? inner "no")
       []
       (map bag-description (str/split inner #",")))]))

(defn parse
  [input]
  (map parse-rule input))

(defn contained-by
  [[outer inner]]
  (map (fn [itm] [(second itm) outer]) inner))

(defn nesting
  [rules]
  (u/fmap (partial map second) (group-by first (mapcat contained-by rules))))

(defn all-outer-bags
  [rules bag]
  (let [nestings (nesting rules)]
    (loop [next-bags [bag] outer-bags #{}]
      (if (empty? next-bags)
        outer-bags
        (let [outers (mapcat (partial get nestings) next-bags)]
          (recur outers (into outer-bags outers)))))))

(defn satisfy-rule
  [rules cnt bag]
  (let [needed-bags (get rules bag)]
    (if (empty? needed-bags)
      cnt
      (* cnt
         (reduce + 1 (map #(satisfy-rule rules (first %) (second %)) needed-bags))))))

(defn count-inner-bags
  [rules bag]
  (dec (satisfy-rule (into {} rules) 1 bag)))

(defn part1
  [input]
  (count (all-outer-bags input :shiny-gold)))

(defn part2
  [input]
  (count-inner-bags input :shiny-gold))
