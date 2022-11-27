(ns aoc-clj.2016.day09-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2016.day09 :as t]))

(deftest decompress
  (testing "Decompresses the example data correctly"
    (is (= "ADVENT" (t/decompress "ADVENT")))
    (is (= "ABBBBBC" (t/decompress "A(1x5)BC")))
    (is (= "XYZXYZXYZ" (t/decompress "(3x3)XYZ")))
    (is (= "ABCBCDEFEFG" (t/decompress "A(2x2)BCD(2x2)EFG")))
    (is (= "(1x3)A" (t/decompress "(6x1)(1x3)A")))
    (is (= "X(3x3)ABC(3x3)ABCY" (t/decompress "X(8x2)(3x3)ABCY")))))

(deftest decompress-count
  (testing "Can count the decompressed size for example data"
    (is (= 9 (t/decompress-count "(3x3)XYZ")))
    (is (= 20 (t/decompress-count "X(8x2)(3x3)ABCY")))
    (is (= 241920 (t/decompress-count "(27x12)(20x12)(13x14)(7x10)(1x12)A")))
    (is (= 445 (t/decompress-count "(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN")))))

(deftest day09-part1-soln
  (testing "Reproduces the answer for day09, part1"
    (is (= 112830 (t/day09-part1-soln)))))

(deftest day09-part2-soln
  (testing "Reproduces the answer for day09, part2"
    (is (= 10931789799 (t/day09-part2-soln)))))