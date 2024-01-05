(ns aoc-clj.2021.day08
  "Solution to https://adventofcode.com/2021/day/8"
  (:require [clojure.string :as str]
            [clojure.set :as set]
            [aoc-clj.utils.core :as u]))

(def digit-mapping
  {"abcefg"  0 ; 6 chars
   "cf"      1 ; 2 chars
   "acdeg"   2 ; 5 chars
   "acdfg"   3 ; 5 chars
   "bcdf"    4 ; 4 chars
   "abdfg"   5 ; 5 chars
   "abdefg"  6 ; 6 chars
   "acf"     7 ; 3 chars
   "abcdefg" 8 ; 7 chars
   "abcdfg"  9 ; 6 chars
   })

(defn parse-line
  [line]
  (let [[l r] (str/split line #" \| ")]
    {:patterns (str/split l #" ")
     :output (str/split r #" ")}))

(defn parse
  [input]
  (map parse-line input))


(defn easy-digits-count
  [note]
  (->> (:output note)
       (map count)
       (filter #{2 3 4 7})
       count))

(defn total-easy-digits-count
  [notes]
  (reduce + (map easy-digits-count notes)))

(def freq-map
  {4 #{\e}
   6 #{\b}
   7 #{\d \g}
   8 #{\a \c}
   9 #{\f}})

(defn freq-rule
  [patterns]
  (let [freqs (->> patterns
                   (apply str)
                   (frequencies))]
    (u/fmap freq-map freqs)))

(defn easy-digit-rule
  [patterns]
  (let [one   (first (filter #(= 2 (count %)) patterns))
        seven (first (filter #(= 3 (count %)) patterns))
        four  (first (filter #(= 4 (count %)) patterns))
        [u v] one
        [x y] (filter (complement #{u v}) four)
        z     (first (filter (complement #{u v}) seven))]
    {u #{\c \f} v #{\c \f} x #{\b \d} y #{\b \d} z #{\a}}))

(defn decode-mapping
  [patterns]
  (let [rule-a (freq-rule patterns)
        rule-b (easy-digit-rule patterns)
        combined (into rule-a (zipmap (keys rule-b)
                                      (map #(set/intersection (rule-b %) (rule-a %)) (keys rule-b))))
        known  (apply set/union (vals (filter #(= 1 (count (val %)))  combined)))
        unknown (into {} (filter #(> (count (val %)) 1) combined))]
    (->> (into combined (u/fmap #(set/difference % known) unknown))
         (u/fmap first))))

(defn decode-digit
  [mapping digit]
  (->> (map mapping digit)
       sort
       str/join
       digit-mapping))

(defn decode-notes
  [{:keys [output patterns]}]
  (let [mapping (decode-mapping patterns)
        digit-str (->> (map (partial decode-digit mapping) output)
                       (apply str))]
    (->> (str/replace digit-str #"^0+" "")
         read-string)))

(defn sum-of-decoded-digits
  [input]
  (reduce + (map decode-notes input)))

(defn part1
  [input]
  (total-easy-digits-count input))


(defn part2
  [input]
  (sum-of-decoded-digits input))
