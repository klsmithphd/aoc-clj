(ns aoc-clj.2018.day02-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2018.day02 :as d02]))

(def d02-s00
  ["abcdef"
   "bababc"
   "abbcde"
   "abcccd"
   "aabcdd"
   "abcdee"
   "ababab"])

(def d02-s01
  ["abcde"
   "fghij"
   "klmno"
   "pqrst"
   "fguij"
   "axcye"
   "wvxyz"])

(deftest checksum-test
  (testing "Computes the checksum (the product of the number of ids that
            contain a letter exactly twice and the number that 
            contain a letter exactly three times"
    (is (= 12 (d02/checksum d02-s00)))))

(deftest closest-boxids-common-chars-test
  (testing "Finds the letters in common between the two closest box ids"
    (is (= "fgij" (d02/closest-boxids-common-chars d02-s01)))))

(def day02-input (u/parse-puzzle-input d02/parse 2018 2))

(deftest part1-test
  (testing "Reproduces the answer for day02, part1"
    (is (= 5478 (d02/part1 day02-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day02, part2"
    (is (= "qyzphxoiseldjrntfygvdmanu" (d02/part2 day02-input)))))