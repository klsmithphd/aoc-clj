(ns aoc-clj.2023.day15-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2023.day15 :as t]))

(deftest hash-char-test
  (testing "Steps of the HASH algorithm, character-by-character"
    (is (= 200 (t/hash-char 0 \H)))
    (is (= 153 (t/hash-char 200 \A)))
    (is (= 172 (t/hash-char 153 \S)))
    (is (= 52 (t/hash-char 172 \H)))))

(deftest hash-alg-test
  (testing "Correct HASH algorithm output"
    (is (= 52 (t/hash-alg "HASH")))))

