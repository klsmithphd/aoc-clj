(ns aoc-clj.2022.day11-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.2022.day11 :as t]))

(def d11-s01
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

(deftest round-test
  (testing "Monkeys end up with the right items after each round"
    (is (= [[20 23 27 26]
            [2080 25 167 207 401 1046]
            []
            []]
           (t/items (t/round d11-s01))))
    (is (= [[695 10 71 135 350]
            [43 49 58 55 362]
            []
            []]
           (t/items (t/round (t/round d11-s01)))))
    (is (= [[83, 44, 8, 184, 9, 20, 26, 102]
            [110, 36]
            []
            []]
           (t/items (nth (iterate t/round d11-s01) 15))))
    (is (= [[10, 12, 14, 26, 34]
            [245, 93, 53, 199, 115]
            []
            []]
           (t/items (nth (iterate t/round d11-s01) 20))))))

(deftest counts-test
  (testing "Counts the number of items inspected by each monkey"
    (is (= [101 95 7 105]
           (t/counts (nth (iterate t/round d11-s01) 20))))
    ;; part 2
    (is (= [2 4 3 6]
           (t/counts (nth (iterate t/round-part2 d11-s01) 1))))
    (is (= [99 97 8 103]
           (t/counts (nth (iterate t/round-part2 d11-s01) 20))))
    ;; Currently too prohibitively expensive to test
    ;; (is (= [5204 1792 199 5192]
    ;;        (t/counts (nth (iterate t/round-part2 d11-s01) 1000))))
    ;; (is (= [26075 23921 974 26000]
    ;;        (t/counts (nth (iterate t/round-part2 d11-s01) 5000))))
    ;; (is (= [52166 47830 1938 52013]
    ;;        (t/counts (nth (iterate t/round-part2 d11-s01) 10000))))
    ))

(deftest monkey-business-test
  (testing "Computes the monkey business score (product of top two 
            inspection counts)"
    (is (= 10605 (t/monkey-business-1 d11-s01 20)))
    ;; Currently too prohibitively expensive to test
    ;; (is (= 2713310158 (t/monkey-business-2 d11-s01 10000)))
    ))

(deftest day11-part1-soln
  (testing "Reproduces the answer for day11, part1"
    (is (= 112221 (t/day11-part1-soln)))))

;; (deftest day11-part2-soln
;;   (testing "Reproduces the answer for day11, part2"
;;     (is (= 0 (t/day11-part2-soln)))))