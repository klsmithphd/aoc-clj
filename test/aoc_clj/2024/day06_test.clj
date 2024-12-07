(ns aoc-clj.2024.day06-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2024.day06 :as d06]))

(def d06-s00
  (d06/parse
   ["....#....."
    ".........#"
    ".........."
    "..#......."
    ".......#.."
    ".........."
    ".#..^....."
    "........#."
    "#........."
    "......#..."]))

(deftest distinct-guard-positions-test
  (testing "Counts the number of unique positions visited by the guard"
    (is (= 41 (d06/distinct-guard-positions d06-s00)))))



(def day06-input (u/parse-puzzle-input d06/parse 2024 6))

(deftest part1-test
  (testing "Reproduces the answer for day06, part1"
    (is (= 4656 (d06/part1 day06-input)))))



;; (->> (d06/guard-start d06-s00)
;;      (d06/next-move d06-s00)
;;      (d06/next-move d06-s00)
;;      (d06/next-move d06-s00)
;;      (d06/next-move d06-s00)
;;      (d06/next-move d06-s00)
;;      (d06/next-move d06-s00)
;;     ;;  (d06/next-move d06-s00)
;;      )

;; (:loop? (last (d06/guard-path
;;                     (assoc-in d06-s00 [:grid [3 3]] :wall))))

;; (d06/looping-guard-paths d06-s00)

;; (rest (:path (d06/guard-path d06-s00)))