(ns aoc-clj.2016.day21-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2016.day21 :as d21]))

(def d21-s00-raw
  ["swap position 4 with position 0"
   "swap letter d with letter b"
   "reverse positions 0 through 4"
   "rotate left 1 step"
   "move position 1 to position 4"
   "move position 3 to position 0"
   "rotate based on position of letter b"
   "rotate based on position of letter d"])

(def d21-s00
  [{:cmd "swap-pos" :p1 4 :p2 0}
   {:cmd "swap-let" :l1 "d" :l2 "b"}
   {:cmd "reverse" :p1 0 :p2 4}
   {:cmd "rotate-l" :amt 1}
   {:cmd "move" :p1 1 :p2 4}
   {:cmd "move" :p1 3 :p2 0}
   {:cmd "rotate" :let "b"}
   {:cmd "rotate" :let "d"}])

(def d21-s00-seq
  ["abcde"
   "ebcda"
   "abcde"
   "bcdea"
   "bdeac"
   "abdec"
   "ecabd"
   "decab"])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d21-s00 (d21/parse d21-s00-raw)))))