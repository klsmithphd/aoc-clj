(ns aoc-clj.2023.day12
  (:require [clojure.string :as str]))

(defn parse-line
  [line]
  (let [[left right] (str/split line #" ")]
    [left (read-string (str "[" right "]"))]))

(defn parse
  [input]
  (map parse-line input))

(declare num-arrangements)
(defn num-arrangements-uncached
  [[spring-str groups]]
  ;; Trim any leading "." indicating operating springs.
  (let [springs (str/replace spring-str #"^\.+" "")]
    (cond
    ;; If the string is empty and there are no more groups to be found, that's an option
      (empty? springs) (if (empty? groups) 1 0)
    ;; If there are no more groups to be found, the string cannot contain "#"
      (empty? groups) (if (str/includes? springs "#") 0 1)
      (str/starts-with? springs "#")
      (cond
        ;; The string is shorter than required
        (< (count springs) (first groups)) 0
        ;; The string contains a dot before reach the size 
        (str/includes? (subs springs 0 (first groups)) ".") 0
        ;; The string is as long as the group
        (= (count springs) (first groups)) (if (= (count groups) 1) 1 0)
        ;; The next character after the matching string cannot be a "#"
        (str/starts-with? (subs springs (first groups)) "#") 0
        ;; This spring was a match
        :else (num-arrangements [(subs springs (inc (first groups))) (rest groups)]))
      ;; The first character is a "?" 
      ;; So we try replacing it with a "#" and see how that goes or with a "."
      ;; (which will get stripped, so we just skip it)
      :else (+ (num-arrangements [(str "#" (subs springs 1)) groups])
               (num-arrangements [(subs springs 1) groups])))))

(def num-arrangements (memoize num-arrangements-uncached))

(defn num-arrangements-sum
  [input]
  (reduce + (map num-arrangements input)))

(defn unfold
  [[springs groups]]
  [(str/join "?" (repeat 5 springs))
   (apply concat (repeat 5 groups))])

(defn unfolded-arrangements-sum
  [input]
  (num-arrangements-sum (map unfold input)))

(defn day12-part1-soln
  [input]
  (num-arrangements-sum input))

(defn day12-part2-soln
  [input]
  (unfolded-arrangements-sum input))
