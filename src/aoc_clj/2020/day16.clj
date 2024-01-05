(ns aoc-clj.2020.day16
  "Solution to https://adventofcode.com/2020/day/16"
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(defn parse-range
  [range-str]
  (map read-string (str/split range-str #"-")))

(defn parse-rule
  [rule-str]
  (let [[desc-str range-str] (str/split rule-str #": ")
        desc (keyword (str/replace desc-str " " "-"))
        ranges (map parse-range (str/split range-str #" or "))]
    {:desc desc :ranges ranges}))

(defn parse-ticket
  [ticket-str]
  (map read-string (str/split ticket-str #",")))

(defn intermediate-parse
  [input]
  (let [[rules tickets] (str/split input #"\n\nyour ticket:\n")
        [yours nearby]  (str/split tickets #"\n\nnearby tickets:\n")]
    {:rules (map parse-rule (str/split rules #"\n"))
     :yours (parse-ticket yours)
     :nearby (map parse-ticket (str/split nearby #"\n"))}))

(defn parse
  [input]
  (->> input (str/join "\n") intermediate-parse))

(defn valid-values
  [rules]
  (->> (mapcat :ranges rules)
       (mapcat #(range (first %) (inc (second %))))
       (into #{})))

(defn invalid-nearby
  [{:keys [rules nearby]}]
  (let [valid? (valid-values rules)
        invalid? (complement valid?)]
    (filter invalid? (flatten nearby))))

(defn valid-tickets
  [{:keys [rules nearby]}]
  (let [valid? (valid-values rules)]
    (filter (partial every? valid?) nearby)))

(defn rule-matchers
  [{:keys [desc ranges]}]
  (fn [coll]
    (let [check (->> ranges
                     (mapcat #(range (first %) (inc (second %))))
                     (into #{}))]
      (when (every? check coll) desc))))

(defn identify-slots
  [{:keys [rules] :as input}]
  (let [matchers (map rule-matchers rules)
        test (fn [col] (set (filter some? (map #(% col) matchers))))
        cols (apply mapv vector (valid-tickets input))
        matches (map test cols)
        candidates (zipmap (range (count matches)) matches)]
    (loop [known #{} mapping {} possible candidates n 0]
      (if (empty? possible)
        mapping
        (let [[pos fieldset] (first (filter #(= 1 (count (val %))) possible))
              field (first fieldset)]
          (recur (conj known field)
                 (assoc mapping pos field)
                 (u/fmap #(disj % field) (dissoc possible pos))
                 (inc n)))))))

(defn resolved-ticket
  [{:keys [yours] :as input}]
  (let [mapping (identify-slots input)]
    (into {} (map-indexed (fn [idx val] [(get mapping idx) val]) yours))))

(defn part1
  [input]
  (reduce + (invalid-nearby input)))

(defn part2
  [input]
  (->> (resolved-ticket input)
       (filter #(str/starts-with? (str (symbol (key %))) "departure"))
       (map second)
       (reduce *)))