(ns aoc-clj.2020.day14
  "Solution to https://adventofcode.com/2020/day/14"
  (:require [clojure.math.combinatorics :as combo]
            [clojure.string :as str]
            [aoc-clj.utils.core :as u]))

(defn parse-mask
  [mask-str]
  (let [m-str (second (str/split mask-str #" = "))
        override-bits (->> (map-indexed vector m-str)
                          ;;  (filter #(not= \X (second %)))
                           (map (fn
                                  [[idx bit]]
                                  [(read-string (str bit)) (- 35 idx)]))
                           (group-by first)
                           (u/fmap #(map second %)))
        and-mask (reduce bit-clear 68719476735 (override-bits 0))
        or-mask  (reduce bit-set 0 (override-bits 1))
        float-mask (reduce bit-clear
                           (reduce bit-set 0 (concat (override-bits 0) (override-bits 1)))
                           (override-bits 'X))]
    {:and-mask and-mask
     :or-mask or-mask
     :float-mask float-mask
     :exes (get override-bits 'X)}))

(defn parse-assign
  [assign-str]
  (let [[addr-str val] (str/split assign-str #" = ")
        len (count addr-str)
        addr (read-string (subs addr-str 4 (dec len)))]
    {:address addr :val (read-string val)}))

(defn parse-line
  [input-line]
  (if (str/starts-with? input-line "mask")
    (parse-mask input-line)
    (parse-assign input-line)))

(defn parse
  [input]
  (map parse-line input))

(defn execute
  [instructions]
  (loop [mask {} registers {} inst instructions]
    (if (empty? inst)
      registers
      (let [next-inst (first inst)]
        (if (:and-mask next-inst)
          (recur next-inst registers (rest inst))
          (let [{:keys [address val]} next-inst]
            (recur mask
                   (assoc registers address
                          (->> val
                               (bit-and (:and-mask mask))
                               (bit-or  (:or-mask mask))))
                   (rest inst))))))))

(defn floating-vals
  [exes]
  (->> exes
       (map (partial bit-shift-left 1))
       combo/subsets
       (map (partial apply +))))

(defn addresses
  [{:keys [float-mask or-mask exes]} address]
  (let [raw (->> address
                 (bit-and float-mask)
                 (bit-or  or-mask))]
    (map (partial + raw) (floating-vals exes))))

(defn execute2
  [instructions]
  (loop [mask {} registers {} inst instructions]
    (if (empty? inst)
      registers
      (let [next-inst (first inst)]
        (if (:and-mask next-inst)
          (recur next-inst registers (rest inst))
          (let [{:keys [address val]} next-inst]
            (recur mask
                   (reduce #(assoc %1 %2 val) registers (addresses mask address))
                   (rest inst))))))))

(defn register-sum
  [input decoder]
  (reduce + (vals (decoder input))))

(defn day14-part1-soln
  [input]
  (register-sum input execute))

(defn day14-part2-soln
  [input]
  (register-sum input execute2))