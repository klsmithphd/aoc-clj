(ns aoc-clj.2019.day01-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2019.day01 :as t]))

(deftest fuel-test
  (testing "Part 1 fuel calculation"
    (is (= 2 (t/fuel 12)))
    (is (= 2 (t/fuel 14)))
    (is (= 654 (t/fuel 1969)))
    (is (= 33583 (t/fuel 100756)))))

(deftest total-fuel-test
  (testing "Part 2 recursive fuel calculation"
    (is (= 2 (t/total-fuel 14)))
    (is (= 966 (t/total-fuel 1969)))
    (is (= 50346 (t/total-fuel 100756)))))

(def day01-input (u/parse-puzzle-input t/parse 2019 1))

(deftest part1-test
  (testing "Can reproduce the answer for part1"
    (is (= 3152038 (t/part1 day01-input)))))

(deftest part2-test
  (testing "Can reproduce the answer for part2"
    (is (= 4725210 (t/part2 day01-input)))))