(ns aoc-clj.2016.day12-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2016.day12 :as d12]))

(def d12-s00-raw
  ["cpy 41 a"
   "inc a"
   "inc a"
   "dec a"
   "jnz a 2"
   "dec a"])

(def d12-s00
  [{:cmd "cpy" :x 41 :y :a}
   {:cmd "inc" :x :a}
   {:cmd "inc" :x :a}
   {:cmd "dec" :x :a}
   {:cmd "jnz" :x :a :y 2}
   {:cmd "dec" :x :a}])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d12-s00 (d12/parse d12-s00-raw)))))

(deftest execute
  (testing "Executes sample instructions"
    (is (= {:a 42, :b 0, :c 0, :d 0, :inst 6}
           (d12/execute d12/init-state d12-s00)))))

(def day12-input (u/parse-puzzle-input d12/parse 2016 12))

(deftest part1-test
  (testing "Reproduces the answer for day12, part1"
    (is (= 318020 (d12/part1 day12-input)))))

;; FIXME: https://github.com/klsmithphd/aoc-clj/issues/75
;; Test is too slow to run all the time
(deftest ^:slow part2
  (testing "Reproduces the answer for day12, part2"
    (is (= 9227674 (d12/part2 day12-input)))))