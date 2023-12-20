(ns aoc-clj.2023.day20
  (:require [clojure.string :as str]))

(defn parse-line
  [line]
  (let [[l r] (str/split line #" -> ")]
    {:type (cond (= "broadcaster" l) :broadcast
                 (str/starts-with? l "%") :flip-flop
                 (str/starts-with? l "&") :conjunction)
     :id (if (= "broadcaster" l) l (subs l 1))
     :dest (str/split r #", ")}))

(defn parse
  [input]
  (map parse-line input))