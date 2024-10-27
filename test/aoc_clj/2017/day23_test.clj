(ns aoc-clj.2017.day23-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2017.day23 :as d23]))

(def d23-s00-raw
  ["set b 84"
   "set c b"
   "jnz a 2"
   "jnz 1 5"
   "mul b 100"
   "sub b -100000"])

(def d23-s00
  [["set" ["b" 84]]
   ["set" ["c" "b"]]
   ["jnz" ["a" 2]]
   ["jnz" [1 5]]
   ["mul" ["b" 100]]
   ["sub" ["b" -100000]]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d23-s00 (d23/parse d23-s00-raw)))))

(deftest step-test
  (testing "Updates the system state by one step"
    (is (= {:insts d23-s00 :pos 1 :mul-count 0 "b" 84}
           (d23/step (d23/init-state d23-s00))))

    (is (= {:insts d23-s00 :pos 2 :mul-count 0 "b" 84 "c" 84}
           (d23/step
            {:insts d23-s00 :pos 1 :mul-count 0 "b" 84})))

    (is (= {:insts d23-s00 :pos 3 :mul-count 0 "b" 84 "c" 84}
           (d23/step
            {:insts d23-s00 :pos 2 :mul-count 0 "b" 84 "c" 84})))

    (is (= {:insts d23-s00 :pos 8 :mul-count 0 "b" 84 "c" 84}
           (d23/step
            {:insts d23-s00 :pos 3 :mul-count 0 "b" 84 "c" 84})))

    (is (= {:insts d23-s00 :pos 5 :mul-count 1 "b" 8400 "c" 84}
           (d23/step
            {:insts d23-s00 :pos 4 :mul-count 0 "b" 84 "c" 84})))

    (is (= {:insts d23-s00 :pos 6 :mul-count 0 "b" 100084 "c" 84}
           (d23/step
            {:insts d23-s00 :pos 5 :mul-count 0 "b" 84 "c" 84})))))

(def day23-input (u/parse-puzzle-input d23/parse 2017 23))

(deftest part1-test
  (testing "Reproduces the answer for day23, part1"
    (is (= 6724 (d23/part1 day23-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day23, part1"
    (is (= 903 (d23/part2 day23-input)))))