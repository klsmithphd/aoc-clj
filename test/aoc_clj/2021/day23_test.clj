(ns aoc-clj.2021.day23-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2021.day23 :as t]))

(def d23-s00-raw
  ["#############"
   "#...........#"
   "###B#C#B#D###"
   "  #A#D#C#A#  "
   "  #########  "])

(def d23-s00
  {:a0 {:type :b}
   :a1 {:type :a}
   :b0 {:type :c}
   :b1 {:type :d}
   :c0 {:type :b}
   :c1 {:type :c}
   :d0 {:type :d}
   :d1 {:type :a}})

(def d23-s00-soln
  [[:c0 :h2]
   [:b0 :c0]
   [:b1 :h3]
   [:h2 :b1]
   [:a0 :b0]
   [:d0 :h4]
   [:d1 :h5]
   [:h4 :d1]
   [:h3 :d0]
   [:h5 :a0]])

(def d23-s00-part2-soln
  [[:d0 :h6]
   [:d1 :h0]
   [:c0 :h5]
   [:c1 :h4]
   [:c2 :h1]
   [:b0 :c2]
   [:b1 :c1]
   [:b2 :h3]
   [:b3 :h2]
   [:h3 :b3]
   [:h4 :b2]
   [:h5 :b1]
   [:d2 :c0]
   [:d3 :h5]
   [:h2 :d3]
   [:a0 :b0]
   [:a1 :d2]
   [:a2 :h2]
   [:h1 :a2]
   [:h0 :a1]
   [:h2 :d1]
   [:h5 :a0]
   [:h6 :d0]])

(deftest parse-test
  (testing "Correctly parses the input"
    (is (= d23-s00 (t/parse d23-s00-raw)))))

(deftest cost-of-moves
  (testing "Computes the costs of the moves identified in sample solution"
    (is (= 12521 (t/cost-of-moves d23-s00 d23-s00-soln)))))

(deftest cost-of-moves-part2
  (testing "Computes the costs of the moves identified in sample solution in part2 "
    (is (= 44169 (t/cost-of-moves (t/unfold-input d23-s00) d23-s00-part2-soln)))))

(def day23-input (u/parse-puzzle-input t/parse 2021 23))

(deftest part1-test
  (testing "Reproduces the answer for day23, part1"
    (is (= 16489 (t/part1 day23-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day23, part2"
    (is (= 43413 (t/part2 day23-input)))))
