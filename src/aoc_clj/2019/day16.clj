(ns aoc-clj.2019.day16
  "Solution to https://adventofcode.com/2019/day/16"
  (:require [clojure.string :as str]))

(defn nums->str
  [nums]
  (str/join nums))

(defn str->nums
  [s]
  (mapv (comp read-string str) s))

(def parse (comp str->nums first))

(defn selector-lookup
  [size idx]
  (let [adds (for [x (range idx size (* (inc idx) 4))]
               [x (min size (+ x idx 1))])
        subs (for [x (range (dec (* 3 (inc idx))) size (* (inc idx) 4))]
               [x (min size (+ x idx 1))])]
    {:add-indices adds
     :sub-indices subs}))

;; (defn selector-lookup-shift
;;   [size shift idx]
;;   (let [adds (for [x (range idx size (* (inc idx) 4))]
;;                [(- x shift) (- (min size (+ x idx 1)) shift)])
;;         subs (for [x (range (dec (* 3 (inc idx))) size (* (inc idx) 4))]
;;                [(- x shift) (- (min size (+ x idx 1)) shift)])]
;;     {:add-indices adds
;;      :sub-indices subs}))

(defn selector
  [size idx coll]
  (let [{:keys [add-indices sub-indices]} (selector-lookup size idx)]
    {:adds (vec (mapcat #(apply subvec coll %) add-indices))
     :subs (vec (mapcat #(apply subvec coll %) sub-indices))}))

;; (defn selector-shift
;;   [size shift idx coll]
;;   (let [{:keys [add-indices sub-indices]} (selector-lookup-shift size shift idx)]
;;     {:adds (vec (mapcat #(apply subvec coll %) add-indices))
;;      :subs (vec (mapcat #(apply subvec coll %) sub-indices))}))

(defn do-calc
  [{:keys [adds subs]}]
  (mod (Math/abs (- (reduce + adds)
                    (reduce + subs))) 10))

(defn digit-calc
  [size num]
  (let [selections (pmap #(selector size % num) (range size))]
    (mapv do-calc selections)))

;; (defn digit-calc-shift
;;   [size shift num]
;;   (let [selections (pmap #(selector-shift size shift % num) (range shift size))]
;;     (mapv do-calc selections)))

(defn phase
  [num]
  (let [size (count num)]
    (digit-calc size num)))

(defn run-phases
  [nums phases]
  (let [size (count nums)
        stepper (partial digit-calc size)]
    (first (drop phases (iterate stepper nums)))))

(defn add-mod-10
  [a b]
  (mod (+ a b) 10))

(defn real-signal
  [digits phases]
  (let [size (count digits)
        signal (take (* size 10000) (cycle digits))
        skip (read-string (str/replace (nums->str (take 7 digits)) #"^0+" ""))
        input (reverse (drop skip signal))]
    (->> (iterate (partial reductions add-mod-10) input)
         (drop phases)
         first
         (take-last 8)
         reverse)))

(defn part1
  [input]
  (nums->str (take 8 (run-phases input 100))))

(defn part2
  [input]
  (nums->str (real-signal input 100)))

