(ns aoc-clj.2016.day09-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day09 :as d09]))

(deftest decompress-test
  (testing "Decompresses the example data correctly"
    (is (= "ADVENT" (d09/decompress "ADVENT")))
    (is (= "ABBBBBC" (d09/decompress "A(1x5)BC")))
    (is (= "XYZXYZXYZ" (d09/decompress "(3x3)XYZ")))
    (is (= "ABCBCDEFEFG" (d09/decompress "A(2x2)BCD(2x2)EFG")))
    (is (= "(1x3)A" (d09/decompress "(6x1)(1x3)A")))
    (is (= "X(3x3)ABC(3x3)ABCY" (d09/decompress "X(8x2)(3x3)ABCY")))))

(deftest decompress-count-test
  (testing "Can count the decompressed size for example data"
    (is (= 9 (d09/decompress-count "(3x3)XYZ")))
    (is (= 20 (d09/decompress-count "X(8x2)(3x3)ABCY")))
    (is (= 241920 (d09/decompress-count "(27x12)(20x12)(13x14)(7x10)(1x12)A")))
    (is (= 445 (d09/decompress-count "(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN")))))

(def day09-input (u/parse-puzzle-input d09/parse 2016 9))

(deftest part1-test
  (testing "Reproduces the answer for day09, part1"
    (is (= 112830 (d09/part1 day09-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day09, part2"
    (is (= 10931789799 (d09/part2 day09-input)))))