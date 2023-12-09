(ns aoc-clj.2023.day05
  (:require [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(defn str->numbers
  [s]
  (map read-string (str/split s #"\s")))

(defn parse-map
  [chunk]
  (map str->numbers (rest chunk)))

(defn parse-seeds
  [seeds-str]
  (-> (str/split seeds-str #": ")
      second
      str->numbers))

(defn parse
  [input]
  (let [chunks (u/split-at-blankline input)]
    {:seeds (parse-seeds (ffirst chunks))
     :maps  (mapv parse-map (rest chunks))}))


