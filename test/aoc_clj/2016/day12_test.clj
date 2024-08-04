(ns aoc-clj.2016.day12-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day12 :as d12]))

(def day12-input (u/parse-puzzle-input d12/parse 2016 12))

(deftest part1-test
  (testing "Reproduces the answer for day12, part1"
    (is (= 318020 (d12/part1 day12-input)))))

;; FIXME: https://github.com/klsmithphd/aoc-clj/issues/75
;; Test is too slow to run all the time
(deftest ^:slow part2
  (testing "Reproduces the answer for day12, part2"
    (is (= 9227674 (d12/part2 day12-input)))))