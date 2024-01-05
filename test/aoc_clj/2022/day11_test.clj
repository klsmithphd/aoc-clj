(ns aoc-clj.2022.day11-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2022.day11 :as t]))

(def d11-s00
  (t/parse
   ["Monkey 0:"
    "  Starting items: 79, 98"
    "  Operation: new = old * 19"
    "  Test: divisible by 23"
    "    If true: throw to monkey 2"
    "    If false: throw to monkey 3"
    ""
    "Monkey 1:"
    "  Starting items: 54, 65, 75, 74"
    "  Operation: new = old + 6"
    "  Test: divisible by 19"
    "    If true: throw to monkey 2"
    "    If false: throw to monkey 0"
    ""
    "Monkey 2:"
    "  Starting items: 79, 60, 97"
    "  Operation: new = old * old"
    "  Test: divisible by 13"
    "    If true: throw to monkey 1"
    "    If false: throw to monkey 3"
    ""
    "Monkey 3:"
    "  Starting items: 74"
    "  Operation: new = old + 3"
    "  Test: divisible by 17"
    "    If true: throw to monkey 0"
    "    If false: throw to monkey 1"]))

(deftest parse-test
  (testing "Correctly parses the input sample"
    (is (= d11-s00
           [{:counts    0
             :items     [79 98]
             :operation ["*" 19]
             :test      23
             :true-op   2
             :false-op  3}
            {:counts    0
             :items     [54 65 75 74]
             :operation ["+" 6]
             :test      19
             :true-op   2
             :false-op  0}
            {:counts    0
             :items     [79 60 97]
             :operation ["*" "old"]
             :test      13
             :true-op   1
             :false-op  3}
            {:counts    0
             :items     [74]
             :operation ["+" 3]
             :test      17
             :true-op   0
             :false-op  1}]))))

(deftest round-test
  (testing "Monkeys end up with the right items after each round"
    (is (= [[20 23 27 26]
            [2080 25 167 207 401 1046]
            []
            []]
           (t/items (t/round-1 d11-s00))))
    (is (= [[695 10 71 135 350]
            [43 49 58 55 362]
            []
            []]
           (t/items (t/round-1 (t/round-1 d11-s00)))))
    (is (= [[83, 44, 8, 184, 9, 20, 26, 102]
            [110, 36]
            []
            []]
           (t/items (nth (iterate t/round-1 d11-s00) 15))))
    (is (= [[10, 12, 14, 26, 34]
            [245, 93, 53, 199, 115]
            []
            []]
           (t/items (nth (iterate t/round-1 d11-s00) 20))))))

(deftest counts-test
  (testing "Counts the number of items inspected by each monkey"
    (is (= [101 95 7 105]
           (t/counts (nth (iterate t/round-1 d11-s00) 20))))
    ;; part 2
    (is (= [2 4 3 6]
           (t/counts (nth (iterate t/round-2 (t/part2-augment d11-s00)) 1))))
    (is (= [99 97 8 103]
           (t/counts (nth (iterate t/round-2 (t/part2-augment d11-s00)) 20))))
    (is (= [5204 4792 199 5192]
           (t/counts (nth (iterate t/round-2 (t/part2-augment d11-s00)) 1000))))
    (is (= [26075 23921 974 26000]
           (t/counts (nth (iterate t/round-2 (t/part2-augment d11-s00)) 5000))))
    (is (= [52166 47830 1938 52013]
           (t/counts (nth (iterate t/round-2 (t/part2-augment d11-s00)) 10000))))))

(deftest monkey-business-test
  (testing "Computes the monkey business score (product of top two 
            inspection counts)"
    (is (= 10605 (t/monkey-business-1 d11-s00 20)))
    (is (= 2713310158 (t/monkey-business-2 (t/part2-augment d11-s00) 10000)))))

(def day11-input (u/parse-puzzle-input t/parse 2022 11))

(deftest part1-test
  (testing "Reproduces the answer for day11, part1"
    (is (= 112221 (t/part1 day11-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day11, part2"
    (is (= 25272176808 (t/part2 day11-input)))))