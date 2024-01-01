(ns aoc-clj.2019.day08
  "Solution to https://adventofcode.com/2019/day/8"
  (:require [clojure.string :as str]))

(defn parse
  [input]
  (->> input
       first
       (map (comp read-string str))))

(defn final-pixel
  [pixels]
  (first (filter (partial not= 2) pixels)))

(defn decode-image
  [space-image width height]
  (->> space-image
       (partition (* width height))
       (apply (partial map list))
       (map final-pixel)))

(defn pprint-image
  [image width]
  (let [legible-pixels (replace {0 " " 1 "*"} image)]
    (doseq [line (partition width legible-pixels)]
      (println (str/join line)))))

(defn day08-part1-soln
  [input]
  (let [layers (partition (* 25 6) input)
        layer-freqs (map frequencies layers)
        min-zero-layer (apply min-key #(get % 0) layer-freqs)]
    (* (get min-zero-layer 1) (get min-zero-layer 2))))

(defn day08-part2-soln
  [input]
  (comment
    (pprint-image (decode-image input 25 6) 25)
  ;; Displays:
  ;; *  * ****  **  **** *  *
  ;; *  *    * *  *    * *  *
  ;; ****   *  *      *  *  *
  ;; *  *  *   *     *   *  *
  ;; *  * *    *  * *    *  *
  ;; *  * ****  **  ****  **
    )
  "HZCZU")