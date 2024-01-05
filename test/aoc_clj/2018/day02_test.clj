(ns aoc-clj.2018.day02-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2018.day02 :as t]))

(def day02-input (u/parse-puzzle-input t/parse 2018 2))

(deftest part1-test
  (testing "Reproduces the answer for day02, part1"
    (is (= 5478 (t/part1 day02-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day02, part2"
    (is (= "qyzphxoiseldjrntfygvdmanu" (t/part2 day02-input)))))