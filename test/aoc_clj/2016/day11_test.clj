(ns aoc-clj.2016.day11-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day11 :as d11]))

(def d11-s00-raw
  ["The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip."
   "The second floor contains a hydrogen generator."
   "The third floor contains a lithium generator."
   "The fourth floor contains nothing relevant."])

(def d11-s00
  {:e 1
   1 #{[:m "hydrogen"] [:m "lithium"]}
   2 #{[:g "hydrogen"]}
   3 #{[:g "lithium"]}
   4 #{}})

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d11-s00 (d11/parse d11-s00-raw)))))

(deftest endstate-test
  (testing "Defines the desired endstate (with all objects on the fourth floor), given the initial state"
    (is (= {:e 4
            1 #{}
            2 #{}
            3 #{}
            4 #{[:m "hydrogen"] [:m "lithium"] [:g "hydrogen"] [:g "lithium"]}}
           (d11/endstate d11-s00)))))

(deftest move-count
  (testing "Counts the minimum number of moves "
    (is (= 11 (d11/move-count d11-s00)))))


(def day11-input (u/parse-puzzle-input d11/parse 2016 11))

;; FIXME: Implementation is too slow
;; https://github.com/klsmithphd/aoc-clj/issues/31
(deftest ^:slow part1
  (testing "Reproduces the answer for day11, part1"
    (is (= 31 (d11/part1 day11-input)))))

(deftest ^:slow part2
  (testing "Reproduces the answer for day11, part2"
    (is (= 55 (d11/part2 day11-input)))))