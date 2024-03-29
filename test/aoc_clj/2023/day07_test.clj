(ns aoc-clj.2023.day07-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2023.day07 :as t]))

(def d07-s00-raw ["32T3K 765"
                  "T55J5 684"
                  "KK677 28"
                  "KTJJT 220"
                  "QQQJA 483"])

(def d07-s00 [{:hand [3 2 10 3 13] :bid 765}
              {:hand [10 5 5 11 5] :bid 684}
              {:hand [13 13 6 7 7] :bid 28}
              {:hand [13 10 11 11 10] :bid 220}
              {:hand [12 12 12 11 14] :bid 483}])

(def d07-s00-jokers [{:hand [3 2 10 3 13] :bid 765}
                     {:hand [10 5 5 0 5] :bid 684}
                     {:hand [13 13 6 7 7] :bid 28}
                     {:hand [13 10 0 0 10] :bid 220}
                     {:hand [12 12 12 0 14] :bid 483}])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d07-s00 (t/parse d07-s00-raw)))))

(deftest hand-type-test
  (testing "Determines the type of hand"
    (is (= :five-of-a-kind  (t/hand-type "AAAAA")))
    (is (= :four-of-a-kind  (t/hand-type "AA8AA")))
    (is (= :full-house      (t/hand-type "23332")))
    (is (= :three-of-a-kind (t/hand-type "TTT98")))
    (is (= :two-pair        (t/hand-type "23432")))
    (is (= :one-pair        (t/hand-type "A23A4")))
    (is (= :high-card       (t/hand-type "23456")))))

(deftest sort-test
  (testing "Applies the correct sort logic"
    (is (= [{:hand [3 2 10 3 13] :bid 765}
            {:hand [13 10 11 11 10] :bid 220}
            {:hand [13 13 6 7 7] :bid 28}
            {:hand [10 5 5 11 5] :bid 684}
            {:hand [12 12 12 11 14] :bid 483}]
           (sort t/compare-hands d07-s00)))))

(deftest winnings-test
  (testing "Computes the overall winnings"
    (is (= 6440 (t/winnings d07-s00)))))

(deftest jokerize-hand-test
  (testing "Reinterprets the hand with J as Joker"
    (is (= d07-s00-jokers (map t/jokerize-hand d07-s00)))))

(deftest effective-hand-test
  (testing "Returns the effective hand with the joker acting as any card"
    (is (= [3 2 10 3 13]    (t/effective-hand (:hand (nth d07-s00-jokers 0)))))
    (is (= [10 5 5 5 5]     (t/effective-hand (:hand (nth d07-s00-jokers 1)))))
    (is (= [13 13 6 7 7]    (t/effective-hand (:hand (nth d07-s00-jokers 2)))))
    (is (= [13 10 10 10 10] (t/effective-hand (:hand (nth d07-s00-jokers 3)))))
    (is (= [12 12 12 12 14] (t/effective-hand (:hand (nth d07-s00-jokers 4)))))
    ;; Five Jokers is still Five Jokers
    (is (= [0 0 0 0 0]      (t/effective-hand [0 0 0 0 0])))))

(deftest joker-winnings-test
  (testing "Computes the winnings correctly with joker rules"
    (is (= 5905 (t/joker-winnings d07-s00)))))

(def day07-input (u/parse-puzzle-input t/parse 2023 7))

(deftest part1-test
  (testing "Reproduces the answer for day07, part1"
    (is (= 248396258 (t/part1 day07-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day07, part2"
    (is (= 246436046 (t/part2 day07-input)))))