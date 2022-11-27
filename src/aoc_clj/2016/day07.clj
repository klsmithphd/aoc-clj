(ns aoc-clj.2016.day07
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(defn parse-line
  [line]
  (let [chunks (str/split line #"\[")
        splits (map #(str/split % #"\]") (rest chunks))]
    ;; (println start others)
    {:supernets (into [(first chunks)] (map second splits))
     :hypernets (mapv first splits)}))

(def day07-input (map parse-line (u/puzzle-input "2016/day07-input.txt")))

(defn abba?
  [s]
  (re-seq #"(.)(?!\1)(.)\2\1" s))

(defn supports-tls?
  [{:keys [supernets hypernets]}]
  (if (some identity (filter abba? hypernets))
    false
    (boolean (some identity (filter abba? supernets)))))

(defn day07-part1-soln
  []
  (count (filter supports-tls? day07-input)))

(defn all-abas
  [s]
  (re-seq #"(?=((.)(?!\2)(.)\2))" s))

(defn bab
  [[_ _ a b]]
  (re-pattern (str b a b)))

(defn supports-ssl?
  [{:keys [supernets hypernets]}]
  (->> (mapcat all-abas supernets)
       (map bab)
       (filter (fn [re]
                 (some identity (mapcat (partial re-find re) hypernets))))
       (some identity)
       boolean))

(defn day07-part2-soln
  []
  (count (filter supports-ssl? day07-input)))

