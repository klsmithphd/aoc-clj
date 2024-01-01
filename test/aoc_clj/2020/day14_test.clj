(ns aoc-clj.2020.day14-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2020.day14 :as t]))

;; (def d14-s00
;;   (t/parse
;;    ["mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X"
;;     "mem[8] = 11"
;;     "mem[7] = 101"
;;     "mem[8] = 0"]))

;; (def d14-s01
;;   (t/parse
;;    ["mask = 000000000000000000000000000000X1001X"
;;     "mem[42] = 100"
;;     "mask = 00000000000000000000000000000000X0XX"
;;     "mem[26] = 1"]))

(def day14-input (u/parse-puzzle-input t/parse 2020 14))

(deftest day14-part1-soln
  (testing "Reproduces the answer for day14, part1"
    (is (= 6631883285184 (t/day14-part1-soln day14-input)))))

(deftest day14-part2-soln
  (testing "Reproduces the answer for day14, part2"
    (is (= 3161838538691 (t/day14-part2-soln day14-input)))))