(ns aoc-clj.2019.day08-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2019.day08 :as t]))

(def decoded-output
  '(1 0 0 1 0 1 1 1 1 0 0 1 1 0 0 1 1 1 1 0 1 0 0 1 0
      1 0 0 1 0 0 0 0 1 0 1 0 0 1 0 0 0 0 1 0 1 0 0 1 0
      1 1 1 1 0 0 0 1 0 0 1 0 0 0 0 0 0 1 0 0 1 0 0 1 0
      1 0 0 1 0 0 1 0 0 0 1 0 0 0 0 0 1 0 0 0 1 0 0 1 0
      1 0 0 1 0 1 0 0 0 0 1 0 0 1 0 1 0 0 0 0 1 0 0 1 0
      1 0 0 1 0 1 1 1 1 0 0 1 1 0 0 1 1 1 1 0 0 1 1 0 0))

(def day08-input (u/parse-puzzle-input t/parse 2019 8))

(deftest space-image-decoder-test
  (testing "Can decode an image in space image format"
    (is (= '(0 1 1 0) (t/decode-image [0 2 2 2 1 1 2 2 2 2 1 2 0 0 0 0] 2 2)))
    (is (= decoded-output (t/decode-image day08-input 25 6)))))

(deftest part1-test
  (testing "Can reproduce the answer for part1"
    (is (= 2016 (t/part1 day08-input)))))

(deftest part2-test
  (testing "Can reproduce the answer for part2"
    (is (= "HZCZU" (t/part2 day08-input)))))