(ns aoc-clj.2020.day17-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2020.day17 :as t]))

;; (def d17-s00
;;   (t/parse
;;    [".#."
;;     "..#"
;;     "###"]))

(def day17-input (u/parse-puzzle-input t/parse 2020 17))

(deftest part1-test
  (testing "Reproduces the answer for day17, part1"
    (is (= 267 (t/part1 day17-input)))))

(deftest ^:slow part2
  (testing "Reproduces the answer for day17, part2"
    (is (= 1812 (t/part2 day17-input)))))