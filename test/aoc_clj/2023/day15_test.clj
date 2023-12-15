(ns aoc-clj.2023.day15-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2023.day15 :as t]))

(def d15-s01-raw ["rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7"])
(def d15-s01 ["rn=1" "cm-" "qp=3" "cm=2" "qp-" "pc=4" "ot=9" "ab=5" "pc-" "pc=6" "ot=7"])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d15-s01 (t/parse d15-s01-raw)))))


(deftest hash-char-test
  (testing "Steps of the HASH algorithm, character-by-character"
    (is (= 200 (t/hash-char 0 \H)))
    (is (= 153 (t/hash-char 200 \A)))
    (is (= 172 (t/hash-char 153 \S)))
    (is (= 52 (t/hash-char 172 \H)))))

(deftest hash-alg-test
  (testing "Correct HASH algorithm output"
    (is (= 52 (t/hash-alg "HASH")))
    (is (= 30 (t/hash-alg (nth d15-s01 0))))
    (is (= 253 (t/hash-alg (nth d15-s01 1))))))

(deftest hash-sum-test
  (testing "Adds the HASH values of the input strings"
    (is (= 1320 (t/hash-sum d15-s01)))))

(def day15-input (u/parse-puzzle-input t/parse 2023 15))

(deftest day15-part1-soln
  (testing "Reproduces the answer for day15, part1"
    (is (= 511215 (t/day15-part1-soln day15-input)))))