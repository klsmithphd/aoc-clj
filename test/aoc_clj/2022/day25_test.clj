(ns aoc-clj.2022.day25-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2022.day25 :as t]))

(def d25-s01-decimal
  [1
   2
   3
   4
   5
   6
   7
   8
   9
   10
   15
   20
   2022
   12345
   314159265])

(def d25-s01-snafu
  ["1"
   "2"
   "1="
   "1-"
   "10"
   "11"
   "12"
   "2="
   "2-"
   "20"
   "1=0"
   "1-0"
   "1=11-2"
   "1-0---0"
   "1121-1110-1=0"])

(def d25-s02-decimal
  [1747
   906
   198
   11
   201
   31
   1257
   32
   353
   107
   7
   3
   37])

(def d25-s02-snafu
  ["1=-0-2"
   "12111"
   "2=0="
   "21"
   "2=01"
   "111"
   "20012"
   "112"
   "1=-1="
   "1-12"
   "12"
   "1="
   "122"])

(deftest decimal->snafu-test
  (testing "Can convert decimal to SNAFU numerals"
    (is (= d25-s01-snafu (map t/decimal->snafu d25-s01-decimal)))
    (is (= d25-s02-snafu (map t/decimal->snafu d25-s02-decimal)))))

(deftest snafu->decimal-test
  (testing "Can convert SNAFU to decimal numerals"
    (is (= d25-s01-decimal (map t/snafu->decimal d25-s01-snafu)))
    (is (= d25-s02-decimal (map t/snafu->decimal d25-s02-snafu)))))

;; (deftest day25-part1-soln
;;   (testing "Reproduces the answer for day25, part1"
;;     (is (= 0 (t/day25-part1-soln)))))

;; (deftest day25-part2-soln
;;   (testing "Reproduces the answer for day25, part2"
;;     (is (= 0 (t/day25-part2-soln)))))