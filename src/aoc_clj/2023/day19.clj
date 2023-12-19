(ns aoc-clj.2023.day19
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(defn parse-cond
  [cond-str]
  (if (nil? (str/index-of cond-str ":"))
    {:outcome cond-str}
    (let [value (subs cond-str 0 1)
          oper  (subs cond-str 1 2)
          [num outcome] (str/split (subs cond-str 2) #":")]
      {:value value
       :oper oper
       :num (read-string num)
       :outcome outcome})))

(defn parse-rule
  [rule-str]
  (let [[id rules]    (str/split rule-str #" ")
        trimmed-rules (subs rules 1 (dec (count rules)))]
    [id (map parse-cond (str/split trimmed-rules #","))]))

(defn parse-rating
  [rating-str]
  (mapv read-string (re-seq #"\d+" rating-str)))

(defn parse
  [input]
  (let [[rules ratings] (u/split-at-blankline input)]
    {:rules   (into {} (map parse-rule rules))

     :ratings (map parse-rating ratings)}))