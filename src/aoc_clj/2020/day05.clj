(ns aoc-clj.2020.day05
  "Solution to https://adventofcode.com/2020/day/5"
  (:require [clojure.string :as str]))

(def parse identity)

(defn seat-id
  [seat]
  (let [bit-string (str/replace seat
                                #"F|B|L|R"
                                {"F" "0"
                                 "B" "1"
                                 "L" "0"
                                 "R" "1"})]
    (read-string (str "2r" bit-string))))

(defn part1
  [input]
  (apply max (map seat-id input)))

(defn part2
  [input]
  (let [seat-ids (sort (map seat-id input))]
    (->> (map vector seat-ids (rest seat-ids))
         (filter #(= 2 (- (second %) (first %))))
         ffirst
         inc)))