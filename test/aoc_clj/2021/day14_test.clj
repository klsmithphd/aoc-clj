(ns aoc-clj.2021.day14-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2021.day14 :as t]))

(def d14-s00
  (t/parse
   ["NNCB"
    ""
    "CH -> B"
    "HH -> N"
    "CB -> H"
    "NH -> C"
    "HB -> C"
    "HC -> B"
    "HN -> C"
    "NN -> C"
    "BH -> H"
    "NC -> B"
    "NB -> B"
    "BN -> B"
    "BB -> N"
    "BC -> B"
    "CC -> N"
    "CN -> C"]))

(deftest substitution-rule
  (testing "The pair-insert substitution rule works on sample"
    (is (= "NCNBCHB"
           (:template (t/step d14-s00))))
    (is (= "NBCCNBBBCBHCB"
           (:template (t/step (t/step d14-s00)))))
    (is (= "NBBBCNCCNBBNBNBBCHBHHBCHB"
           (:template (t/step (t/step (t/step d14-s00))))))
    (is (= "NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB"
           (:template (t/step (t/step (t/step (t/step d14-s00)))))))))

(deftest direct-most-minus-least-common-at-ten
  (testing "Most frequent element minus least common after 10 steps using direct impl"
    (is (= 1588 (t/direct-most-minus-least-common-at-n d14-s00 10)))))

(deftest most-minus-least-common-at-ten
  (testing "Most frequent element minus least common after 10 steps using efficient impl"
    (is (= 1588 (t/most-minus-least-common-at-n d14-s00 10)))
    (is (= 2188189693529 (t/most-minus-least-common-at-n d14-s00 40)))))

(def day14-input (u/parse-puzzle-input t/parse 2021 14))

(deftest part1-test
  (testing "Reproduces the answer for day14, part1"
    (is (= 2712 (t/part1 day14-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day14, part2"
    (is (= 8336623059567 (t/part2 day14-input)))))
