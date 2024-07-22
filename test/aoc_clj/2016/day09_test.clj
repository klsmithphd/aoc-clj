(ns aoc-clj.2016.day09-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day09 :as d09]))

(def d09-s00 "ADVENT")
(def d09-s01 "A(1x5)BC")
(def d09-s02 "(3x3)XYZ")
(def d09-s03 "A(2x2)BCD(2x2)EFG")
(def d09-s04 "(6x1)(1x3)A")
(def d09-s05 "X(8x2)(3x3)ABCY")

(def d09-s06 "(27x12)(20x12)(13x14)(7x10)(1x12)A")
(def d09-s07 "(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN")

(deftest parse-marker-test
  (testing "Parses a marker string as two ints"
    (is (= [1 5] (d09/parse-marker "(1x5)")))
    (is (= [190 3] (d09/parse-marker "(190x3)")))))

(deftest decompressed-count-test
  (testing "Counts the number of characters in a decompressed string"
    (is (= 6  (d09/decompressed-count d09-s00))) ;; "ADVENT"
    (is (= 7  (d09/decompressed-count d09-s01))) ;; "ABBBBBC"
    (is (= 9  (d09/decompressed-count d09-s02))) ;; "XYZXYZXYZ"
    (is (= 11 (d09/decompressed-count d09-s03))) ;; "ABCBCDEFEFG"
    (is (= 6  (d09/decompressed-count d09-s04))) ;; "(1x3)A"
    (is (= 18 (d09/decompressed-count d09-s05))) ;; "X(3x3)ABC(3x3)ABCY"

    (is (= 9   (d09/decompressed-count d09-s02 true))) ;; "XYZXYZXYZ"
    (is (= 20  (d09/decompressed-count d09-s05 true))) ;; "XABCABCABCABCABCABCY"
    (is (= 241920 (d09/decompressed-count d09-s06 true)))
    (is (= 445 (d09/decompressed-count d09-s07 true)))))


(def day09-input (u/parse-puzzle-input d09/parse 2016 9))

(deftest part1-test
  (testing "Reproduces the answer for day09, part1"
    (is (= 112830 (d09/part1 day09-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day09, part2"
    (is (= 10931789799 (d09/part2 day09-input)))))