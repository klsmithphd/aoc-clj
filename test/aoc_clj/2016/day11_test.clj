(ns aoc-clj.2016.day11-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2016.day11 :as t]))

"The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip."
"The second floor contains a hydrogen generator."
"The third floor contains a lithium generator."
"The fourth floor contains nothing relevant."
(def day11-sample
  {:e 1
   1 #{[:m "H"] [:m "Li"]}
   2 #{[:g "H"]}
   3 #{[:g "Li"]}
   4 #{}})

(deftest move-count
  (testing "Counts the minimum number of moves "
    (is (= 11 (t/move-count day11-sample)))))

;; FIXME: Implementation is too slow
;; https://github.com/Ken-2scientists/aoc-clj/issues/31
(deftest ^:slow day11-part1-soln
  (testing "Reproduces the answer for day11, part1"
    (is (= 31 (t/day11-part1-soln)))))

(deftest ^:slow day11-part2-soln
  (testing "Reproduces the answer for day11, part2"
    (is (= 55 (t/day11-part2-soln)))))