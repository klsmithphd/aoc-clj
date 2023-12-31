(ns aoc-clj.2015.day07-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2015.day07 :as t]))

(def day07-sample ["123 -> x"
                   "456 -> y"
                   "x AND y -> d"
                   "x OR y -> e"
                   "x LSHIFT 2 -> f"
                   "y RSHIFT 2 -> g"
                   "NOT x -> h"
                   "NOT y -> i"
                   "f -> j"])

(deftest parse-test
  (testing "Parser logic works correctly"
    (is (= (t/parse-line (nth day07-sample 0))  ["x" {:op :assign :args 123}]))
    (is (= (t/parse-line (nth day07-sample 1))  ["y" {:op :assign :args 456}]))
    (is (= (t/parse-line (nth day07-sample 2))  ["d" {:op :and :args ["x" "y"]}]))
    (is (= (t/parse-line (nth day07-sample 3))  ["e" {:op :or :args ["x" "y"]}]))
    (is (= (t/parse-line (nth day07-sample 4))  ["f" {:op :lshift :args ["x" 2]}]))
    (is (= (t/parse-line (nth day07-sample 5))  ["g" {:op :rshift :args ["y" 2]}]))
    (is (= (t/parse-line (nth day07-sample 6))  ["h" {:op :not :args ["x"]}]))
    (is (= (t/parse-line (nth day07-sample 7))  ["i" {:op :not :args ["y"]}]))
    (is (= (t/parse-line (nth day07-sample 8))  ["j" {:op :assign :args "f"}]))))

(deftest wire-val-test
  (testing "Computes the wire values correctly"
    (let [circuit (into {} (map t/parse-line day07-sample))]
      (is (= (t/wire-val circuit "d") 72))
      (is (= (t/wire-val circuit "e") 507))
      (is (= (t/wire-val circuit "f") 492))
      (is (= (t/wire-val circuit "g") 114))
      (is (= (t/wire-val circuit "h") 65412))
      (is (= (t/wire-val circuit "i") 65079))
      (is (= (t/wire-val circuit "x") 123))
      (is (= (t/wire-val circuit "y") 456))
      (is (= (t/wire-val circuit "j") 492)))))

(def day07-input (u/parse-puzzle-input t/parse 2015 7))

(deftest day07-part1-soln
  (testing "Reproduces the answer for day07, part1"
    (is (= 3176 (t/day07-part1-soln day07-input)))))

(deftest day07-part2-soln
  (testing "Reproduces the answer for day07, part2"
    (is (= 14710 (t/day07-part2-soln day07-input)))))