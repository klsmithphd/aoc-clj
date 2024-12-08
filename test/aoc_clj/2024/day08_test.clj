(ns aoc-clj.2024.day08-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2024.day08 :as d08]))

(def d08-s00-raw
  ["............"
   "........0..."
   ".....0......"
   ".......0...."
   "....0......."
   "......A....."
   "............"
   "............"
   "........A..."
   ".........A.."
   "............"
   "............"])

(def d08-s00
  {:width    12
   :height   12
   :antennae
   {\0 #{[8 10] [5 9] [7 8] [4 7]}
    \A #{[9 2] [8 3] [6 6]}}})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d08-s00 (d08/parse d08-s00-raw)))))
