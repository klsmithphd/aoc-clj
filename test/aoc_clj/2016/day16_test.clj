(ns aoc-clj.2016.day16-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2016.day16 :as d16]))

(deftest fill-test
  (testing "Implements the fill logic correctly"
    (is (= [\1 \0 \0] (d16/fill "1")))
    (is (= [\0 \0 \1] (d16/fill "0")))
    (is (= [\1 \1 \1 \1 \1 \0 \0 \0 \0 \0 \0]
           (d16/fill "11111")))
    (is (= [\1 \1 \1 \1 \0 \0 \0 \0 \1 \0 \1 \0 \0 \1 \0 \1 \0 \1 \1 \1 \1 \0 \0 \0 \0]
           (d16/fill "111100001010")))))

(deftest checksum-test
  (testing "Computes the checksum for a bit string"
    (is (= "100"   (d16/checksum "110010110100")))
    (is (= "01100" (d16/checksum "10000011110010000111")))))
