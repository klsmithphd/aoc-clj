(ns aoc-clj.2020.day19
  "Solution to https://adventofcode.com/2020/day/19"
  (:require [clojure.string :as str]))

(defn to-list
  [x]
  (read-string (str "(" x ")")))

(defn parse-rule
  [rule-str]
  (let [[idx rule-txt] (str/split rule-str #": ")
        rule (if (.contains rule-txt "|")
               (map to-list (str/split rule-txt #" \| "))
               (if (str/starts-with? rule-txt "\"")
                 (read-string rule-txt)
                 (to-list rule-txt)))]
    [(read-string idx) rule]))

(defn intermediate-parse
  [input]
  (let [[rules messages] (str/split input #"\n\n")]
    {:rules (into {} (map parse-rule (str/split rules #"\n")))
     :messages (str/split messages #"\n")}))

(defn parse
  [input]
  (intermediate-parse (str/join "\n" input)))

(defn handle-special-eleven
  "I'm not proud of this, but it works"
  [[left right]]
  (str "("
       left right
       "|"
       left left right right
       "|"
       left left left right right right
       "|"
       left left left left right right right right
       "|"
       left left left left left right right right right right
       ")"))

(defn resolve-rule
  ([rules rule]
   (resolve-rule false rules rule))
  ([special? rules rule]
   (let [r (rules rule)]
     (if (string? r)
       r
       (if (coll? (first r))
         (str "("
              (str/join (map (partial resolve-rule special? rules) (first r)))
              "|"
              (str/join (map (partial resolve-rule special? rules) (second r)))
              ")")
         (let [pieces (map (partial resolve-rule special? rules) r)]
           (if (not special?)
             (str/join pieces)
             (case rule
               8 (str (str/join pieces) "+")
               11 (handle-special-eleven pieces)
               (str/join pieces)))))))))

(defn count-matches
  ([input]
   (count-matches input false))
  ([{:keys [rules messages]} special?]
   (let [pattern (re-pattern (str "^" (resolve-rule special? rules 0) "$"))]
     (->> (map (partial re-find pattern) messages)
          (map first)
          (filter some?)
          count))))

(defn part1
  [input]
  (count-matches input))

(defn part2
  [input]
  (count-matches input true))