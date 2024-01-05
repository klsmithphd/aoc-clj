(ns aoc-clj.2020.day04
  "Solution to https://adventofcode.com/2020/day/2"
  (:require [clojure.set :as set]
            [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(defn split-pair
  [pair]
  (let [[k v] (str/split pair #"\:")]
    [(keyword k) v]))

(defn parse
  [input]
  (->> (u/split-at-blankline input)
       (map #(str/join " " %))
       (map #(str/split % #"\ "))
       (map (fn [x]
              (->> x
                   (mapcat split-pair)
                   (apply hash-map))))))

(defn keys-valid?
  [passport]
  (let [needed #{:byr :iyr :eyr :hgt :hcl :ecl :pid}
        ks     (set (keys passport))]
    (set/subset? needed ks)))

(defn byr-valid?
  [{:keys [byr]}]
  (let [yr (read-string byr)]
    (and (= 4 (count byr))
         (number? yr)
         (>= yr 1920)
         (<= yr 2002))))

(defn iyr-valid?
  [{:keys [iyr]}]
  (let [yr (read-string iyr)]
    (and (= 4 (count iyr))
         (number? yr)
         (>= yr 2010)
         (<= yr 2020))))

(defn eyr-valid?
  [{:keys [eyr]}]
  (let [yr (read-string eyr)]
    (and (= 4 (count eyr))
         (number? yr)
         (>= yr 2020)
         (<= yr 2030))))

(defn hgt-valid?
  [{:keys [hgt]}]
  (and (or (str/ends-with? hgt "cm")
           (str/ends-with? hgt "in"))
       (let [len (count hgt)
             num (read-string (subs hgt 0 (- len 2)))
             unit (subs hgt (- len 2) len)]
         (case unit
           "in" (and (number? num)
                     (>= num 59)
                     (<= num 76))
           "cm" (and (number? num)
                     (>= num 150)
                     (<= num 193))))))

(defn hcl-valid?
  [{:keys [hcl]}]
  (some? (re-matches #"^\#[a-f0-9]{6}$" hcl)))

(defn ecl-valid?
  [{:keys [ecl]}]
  (let [valid-colors #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"}]
    (some? (valid-colors ecl))))

(defn pid-valid?
  [{:keys [pid]}]
  (some? (re-matches #"^[0-9]{9}$" pid)))

(def passport-valid?
  (every-pred keys-valid?
              byr-valid?
              iyr-valid?
              eyr-valid?
              hgt-valid?
              hcl-valid?
              ecl-valid?
              pid-valid?))

(defn part1
  [input]
  (count (filter keys-valid? input)))

(defn part2
  [input]
  (count (filter passport-valid? input)))