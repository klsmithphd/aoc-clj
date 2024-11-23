(ns aoc-clj.2018.day18-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.grid.vecgrid :as vg]
            [aoc-clj.2018.day18 :as d18]))

(def d18-s00-raw
  [".#.#...|#."
   ".....#|##|"
   ".|..|...#."
   "..|#.....#"
   "#.#|||#|#|"
   "...#.||..."
   ".|....|..."
   "||...#|.#|"
   "|.||||..|."
   "...#.|..|."])

(def d18-s00
  (vg/->VecGrid2D
   [[:o :l :o :l :o :o :o :t :l :o]
    [:o :o :o :o :o :l :t :l :l :t]
    [:o :t :o :o :t :o :o :o :l :o]
    [:o :o :t :l :o :o :o :o :o :l]
    [:l :o :l :t :t :t :l :t :l :t]
    [:o :o :o :l :o :t :t :o :o :o]
    [:o :t :o :o :o :o :t :o :o :o]
    [:t :t :o :o :o :l :t :o :l :t]
    [:t :o :t :t :t :t :o :o :t :o]
    [:o :o :o :l :o :t :o :o :t :o]]))

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d18-s00 (d18/parse d18-s00-raw)))))