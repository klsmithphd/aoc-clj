(ns aoc-clj.2019.day12-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2019.day12 :as t]))

(def d12-s00
  [[-1 0 2]
   [2 -10 -7]
   [4 -8 8]
   [3 5 -1]])

(def d12-s01
  [[-8 -10 0]
   [5 5 10]
   [2 -7 3]
   [9 -8 -3]])

(deftest total-energy-test
  (testing "Can find the total energy after a number of time steps"
    (is (= 179 (t/total-energy (nth (t/simulate d12-s00) 10))))
    (is (= 1940 (t/total-energy (nth (t/simulate d12-s01) 100))))))


(deftest recurrence-period-test
  (testing "Can find the number of time steps until the orbital state recurs"
    (is (= 2772 (t/recurrence-period d12-s00)))
    (is (= 4686774924 (t/recurrence-period d12-s01)))))

(def day12-input (u/parse-puzzle-input t/parse 2019 12))

(deftest day12-part1-soln-test
  (testing "Can reproduce the answer for part1"
    (is (= 8310 (t/day12-part1-soln day12-input)))))

;; FIXME: Test is too slow
;; https://github.com/Ken-2scientists/aoc-clj/issues/15
(deftest ^:slow day12-part2-soln-test
  (testing "Can reproduce the answer for part2"
    (is (= 319290382980408 (t/day12-part2-soln day12-input)))))