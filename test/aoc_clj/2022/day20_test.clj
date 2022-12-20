(ns aoc-clj.2022.day20-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2022.day20 :as t]))

(def d20-s01       [1 2 -3 3 -2 0 4])
(def d20-s01-mixed [1 2 -3 4 0 3 -2])

(deftest grove-coordinates-test
  (testing "Computes the `grove coordinates`"
    (is (= 3 (t/grove-coordinates d20-s01-mixed)))))

;; (deftest day20-part1-soln
;;   (testing "Reproduces the answer for day20, part1"
;;     (is (= 0 (t/day20-part1-soln)))))

;; (deftest day20-part2-soln
;;   (testing "Reproduces the answer for day20, part2"
;;     (is (= 0 (t/day20-part2-soln)))))