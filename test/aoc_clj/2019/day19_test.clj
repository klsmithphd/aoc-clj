(ns aoc-clj.2019.day19-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2019.day19 :as t]))

(def day19-input (u/parse-puzzle-input t/parse 2019 19))

(deftest day19-part1-test
  (testing "Can reproduce the solution for part1"
    (is (= 150 (t/part1 day19-input)))))

(deftest day19-part2-test
  (testing "Can reproduce the solution for part2"
    (is (= 12201460 (t/part2 day19-input)))))