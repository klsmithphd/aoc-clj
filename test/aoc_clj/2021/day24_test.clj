(ns aoc-clj.2021.day24-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2021.day24 :as t]))

;; (def day24-sample1
;;   (t/parse
;;    ["inp x"
;;     "mul x -1"]))

;; (def day24-sample2
;;   (t/parse
;;    ["inp z"
;;     "inp x"
;;     "mul z 3"
;;     "eql z x"]))

;; (def day24-sample3
;;   (t/parse
;;    ["inp w"
;;     "add z w"
;;     "mod z 2"
;;     "div w 2"
;;     "add y w"
;;     "mod y 2"
;;     "div w 2"
;;     "add x w"
;;     "mod x 2"
;;     "div w 2"
;;     "mod w 2"]))

(def day24-input (u/parse-puzzle-input t/parse 2021 24))

(deftest day24-part1-soln
  (testing "Reproduces the answer for day24, part1"
    (is (= 98998519596997 (t/day24-part1-soln day24-input)))))

;; FIXME: 2021.day24 part 2 solution is too slow
;; https://github.com/Ken-2scientists/aoc-clj/issues/11
(deftest ^:slow day24-part2-soln
  (testing "Reproduces the answer for day24, part2"
    (is (= 31521119151421 (t/day24-part2-soln day24-input)))))
