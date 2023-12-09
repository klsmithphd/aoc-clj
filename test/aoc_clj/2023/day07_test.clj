(ns aoc-clj.2023.day07-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2023.day07 :as t]))

(def d07-s01-raw ["32T3K 765"
                  "T55J5 684"
                  "KK677 28"
                  "KTJJT 220"
                  "QQQJA 483"])

(def d07-s01 [{:hand [3 2 10 3 13] :bid 765}
              {:hand [10 5 5 11 5] :bid 684}
              {:hand [13 13 6 7 7] :bid 28}
              {:hand [13 10 11 11 10] :bid 220}
              {:hand [12 12 12 11 14] :bid 483}])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d07-s01 (t/parse d07-s01-raw)))))


(def day07-input (u/parse-puzzle-input t/parse 2023 7))