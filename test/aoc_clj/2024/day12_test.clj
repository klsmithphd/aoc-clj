(ns aoc-clj.2024.day12-test
  (:require [clojure.test :refer [deftest testing is]]
            [clojure.set :as set]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2024.day12 :as d12]))

(def d12-s00-raw
  ["AAAA"
   "BBCD"
   "BBCC"
   "EEEC"])

(def d12-s00
  {\A #{#{[0 0] [1 0] [2 0] [3 0]}}
   \B #{#{[0 1] [1 1] [0 2] [1 2]}}
   \C #{#{[2 1] [2 2] [3 2] [3 3]}}
   \D #{#{[3 1]}}
   \E #{#{[0 3] [1 3] [2 3]}}})

(def d12-s01-raw
  ["OOOOO"
   "OXOXO"
   "OOOOO"
   "OXOXO"
   "OOOOO"])

(def d12-s01
  {\X #{#{[1 3]} #{[1 1]} #{[3 3]} #{[3 1]}}
   \O #{#{[0 0] [1 0] [2 0] [3 0] [4 0]
          [0 1]       [2 1]       [4 1]
          [0 2] [1 2] [2 2] [3 2] [4 2]
          [0 3]       [2 3]       [4 3]
          [0 4] [1 4] [2 4] [3 4] [4 4]}}})

(def d12-s02
  (d12/parse
   ["RRRRIICCFF"
    "RRRRIICCCF"
    "VVRRRCCFFF"
    "VVRCCCJFFF"
    "VVVVCJJCFE"
    "VVIVCCJJEE"
    "VVIIICJJEE"
    "MIIIIIJJEE"
    "MIIISIJEEE"
    "MMMISSJEEE"]))

(deftest parse-test
  (testing "Returns the plot sets for a given field"
    (is (= d12-s00 (d12/parse d12-s00-raw)))
    (is (= d12-s01 (d12/parse d12-s01-raw)))))

(deftest area-test
  (testing "Computes the area of a given plot"
    (is (= 4  (d12/area (first (get d12-s00 \A)))))
    (is (= 21 (d12/area (first (get d12-s01 \O)))))
    (is (= 1  (d12/area (first (get d12-s01 \X)))))))

(deftest perimeter-test
  (testing "Computes the perimeter of a given plot"
    (is (= 10 (d12/perimeter (first (get d12-s00 \A)))))
    (is (= 8  (d12/perimeter (first (get d12-s00 \B)))))
    (is (= 10 (d12/perimeter (first (get d12-s00 \C)))))
    (is (= 4  (d12/perimeter (first (get d12-s00 \D)))))
    (is (= 8  (d12/perimeter (first (get d12-s00 \E)))))

    (is (= 4  (d12/perimeter (first (get d12-s01 \X)))))
    (is (= 36 (d12/perimeter (first (get d12-s01 \O)))))))

(deftest sides-test
  (testing "Computes the number of sides a given plot has"
    (is (= 4 (d12/sides (first (get d12-s00 \A)))))
    (is (= 4 (d12/sides (first (get d12-s00 \B)))))
    ;; (is (= 8 (d12/sides (first (get d12-s00 \C)))))
    ;; (is (= 4 (d12/sides (first (get d12-s00 \D)))))
    ;; (is (= 4 (d12/sides (first (get d12-s00 \E)))))
    ))

(deftest region-price-test
  (testing "Computes the price of a given plot"
    (is (= 40 (d12/region-price (first (get d12-s00 \A)))))
    (is (= 32 (d12/region-price (first (get d12-s00 \B)))))
    (is (= 40 (d12/region-price (first (get d12-s00 \C)))))
    (is (= 4  (d12/region-price (first (get d12-s00 \D)))))
    (is (= 24 (d12/region-price (first (get d12-s00 \E)))))

    (is (= 756 (d12/region-price (first (get d12-s01 \O)))))
    (is (= 4   (d12/region-price (first (get d12-s01 \X)))))))

(deftest total-price-test
  (testing "Computes the total price for the entire field"
    (is (= 140  (d12/total-price d12-s00)))
    (is (= 772  (d12/total-price d12-s01)))
    (is (= 1930 (d12/total-price d12-s02)))))

(def day12-input (u/parse-puzzle-input d12/parse 2024 12))

(deftest part1-test
  (testing "Reproduces the answer for day12, part1"
    (is (= 1396298 (d12/part1 day12-input)))))