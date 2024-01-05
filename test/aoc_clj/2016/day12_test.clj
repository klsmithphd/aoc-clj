(ns aoc-clj.2016.day12-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day12 :as t]))

(def day12-sample
  (mapv t/parse-line
        ["cpy 41 a"
         "inc a"
         "inc a"
         "dec a"
         "jnz a 2"
         "dec a"]))

(deftest execute
  (testing "Executes sample instructions "
    (is (= {:a 42, :b 0, :c 0, :d 0, :inst 6}
           (t/execute t/init-state day12-sample)))))

(def day12-input (u/parse-puzzle-input t/parse 2016 12))

(deftest part1-test
  (testing "Reproduces the answer for day12, part1"
    (is (= 318020 (t/part1 day12-input)))))

(deftest ^:slow part2
  (testing "Reproduces the answer for day12, part2"
    (is (= 9227674 (t/part2 day12-input)))))