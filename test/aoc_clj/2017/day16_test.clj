(ns aoc-clj.2017.day16-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day16 :as d16]))

(def d16-s00-raw ["s1,x3/4,pe/b"])
(def d16-s00
  [[:spin 1]
   [:exchange [3 4]]
   [:partner ["e" "b"]]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d16-s00 (d16/parse d16-s00-raw)))))

(deftest move-test
  (testing "Correctly moves the programs for each instruction"
    (is (= ["e" "a" "b" "c" "d"]
           (d16/move ["a" "b" "c" "d" "e"] (nth d16-s00 0))))
    (is (= ["e" "a" "b" "d" "c"]
           (d16/move ["e" "a" "b" "c" "d"] (nth d16-s00 1))))
    (is (= ["b" "a" "e" "d" "c"]
           (d16/move ["e" "a" "b" "d" "c"] (nth d16-s00 2))))))

(deftest dance-test
  (testing "Performs all the dance steps and returns the final state"
    (is (= "baedc" (apply str (d16/dance d16-s00 ["a" "b" "c" "d" "e"]))))))

(deftest dance-at-large-n-test
  (testing "Determines the state of the programs after n dance rounds"
    (is (= "baedc" (apply str (d16/n-dances d16-s00 ["a" "b" "c" "d" "e"] 1))))
    (is (= "ceadb" (apply str (d16/n-dances d16-s00 ["a" "b" "c" "d" "e"] 2))))
    (is (= "ecbda" (apply str (d16/n-dances d16-s00 ["a" "b" "c" "d" "e"] 3))))
    (is (= "abcde" (apply str (d16/n-dances d16-s00 ["a" "b" "c" "d" "e"] 4))))
    (is (= "abcde" (apply str (d16/n-dances d16-s00 ["a" "b" "c" "d" "e"] 100))))))

(def day16-input (u/parse-puzzle-input d16/parse 2017 16))

(deftest part1-test
  (testing "Reproduces the answer for day15, part1"
    (is (= "olgejankfhbmpidc" (d16/part1 day16-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day15, part2"
    (is (= "gfabehpdojkcimnl" (d16/part2 day16-input)))))
