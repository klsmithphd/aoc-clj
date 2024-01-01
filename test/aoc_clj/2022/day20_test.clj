(ns aoc-clj.2022.day20-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2022.day20 :as t]))

(def d20-s00   [1 2 -3 3 -2 0 4])
(def d20-s00-1 [2 1 -3 3 -2 0 4])
(def d20-s00-2 [1 -3 2 3 -2 0 4])
(def d20-s00-3 [1 2 3 -2 -3 0 4])
(def d20-s00-4 [1 2 -2 -3 0 3 4])
(def d20-s00-5 [1 2 -3 0 3 4 -2])
(def d20-s00-6 [1 2 -3 0 3 4 -2])
(def d20-s00-7 [1 2 -3 4 0 3 -2])

(deftest mix-test
  (testing "Mixes the values correctly, one at a time"
    (is (= d20-s00-1
           (mapv d20-s00 (t/mix d20-s00 (vec (range 7)) 0))))
    (is (= d20-s00-2
           (mapv d20-s00-1 (t/mix d20-s00-1 (vec (range 7)) 0))))
    (is (= d20-s00-3
           (mapv d20-s00-2 (t/mix d20-s00-2 (vec (range 7)) 1))))
    (is (= d20-s00-4
           (mapv d20-s00-3 (t/mix d20-s00-3 (vec (range 7)) 2))))
    (is (= d20-s00-5
           (mapv d20-s00-4 (t/mix d20-s00-4 (vec (range 7)) 2))))
    (is (= d20-s00-6
           (mapv d20-s00-5 (t/mix d20-s00-5 (vec (range 7)) 3))))
    (is (= d20-s00-7
           (mapv d20-s00-6 (t/mix d20-s00-6 (vec (range 7)) 5))))))

(deftest mixed-test
  (testing "Completes all mixing correctly"
    (is (= d20-s00-7 (t/mixed d20-s00)))))

(deftest grove-coordinates-test
  (testing "Computes the `grove coordinates`"
    (is (= 3 (t/grove-coordinates d20-s00-7)))))

(deftest decrypt-and-mix-ten-test
  (testing "Applies decryption key and mixes 10 times"
    (is (= 1623178306 (t/decrypt-and-mix-ten d20-s00)))))

(def day20-input (u/parse-puzzle-input t/parse 2022 20))

;; FIXME: Implementation is far too slow
;; https://github.com/Ken-2scientists/aoc-clj/issues/29
(deftest ^:slow day20-part1-soln
  (testing "Reproduces the answer for day20, part1"
    (is (= 9866 (t/day20-part1-soln day20-input)))))

(deftest ^:slow day20-part2-soln
  (testing "Reproduces the answer for day20, part2"
    (is (= 12374299815791 (t/day20-part2-soln day20-input)))))