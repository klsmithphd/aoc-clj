(ns aoc-clj.2016.day14-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.utils.digest :as d]
            [aoc-clj.2016.day14 :as d14]))

(def d14-s00 "abc")

(deftest triple-char-key-candidates-test
  (testing "Reproduces the first few indices that result in hashes
            containing a triple-char sequence"
    (is (= [18 39 45 64 77 79 88 91 92]
           (map first (take 9 (d14/triple-char-key-candidates d/md5-str d14-s00)))))
    
    (is (= [5 10]
           (map first (take 2 (d14/triple-char-key-candidates d14/stretched-md5-str d14-s00))))) ))

(deftest first-few-keys
  (testing "Finds the indices of the first few keys with the sample salt"
    (is (= [39 92] (take 2 (d14/pad-keys d/md5-str d14-s00))))))

(deftest last-pad-key-test
  (testing "Finds the index that produces the 64 one-time pad key"
    (is (= 22728 (d14/last-pad-key d/md5-str d14-s00)))))

(deftest ^:slow last-stretched-pad-key-test
  (testing "Finds the index that produces the 64 one-time pad key with key stretching"
    (is (= 22551 (d14/last-pad-key d14/stretched-md5-str d14-s00)))))

(def day14-input (u/parse-puzzle-input d14/parse 2016 14))

(deftest part1-test
  (testing "Reproduces the answer for day13, part1"
    (is (= 15168 (d14/part1 day14-input)))))

(deftest ^:slow part2-test
  (testing "Reproduces the answer for day14, part2"
    (is (= 12345 (d14/part2 day14-input)))))