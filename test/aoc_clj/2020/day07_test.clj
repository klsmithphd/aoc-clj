(ns aoc-clj.2020.day07-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2020.day07 :as t]))

(def d07-s00
  (t/parse
   ["light red bags contain 1 bright white bag, 2 muted yellow bags."
    "dark orange bags contain 3 bright white bags, 4 muted yellow bags."
    "bright white bags contain 1 shiny gold bag."
    "muted yellow bags contain 2 shiny gold bags, 9 faded blue bags."
    "shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags."
    "dark olive bags contain 3 faded blue bags, 4 dotted black bags."
    "vibrant plum bags contain 5 faded blue bags, 6 dotted black bags."
    "faded blue bags contain no other bags."
    "dotted black bags contain no other bags."]))

(def d07-s01
  (t/parse
   ["shiny gold bags contain 2 dark red bags."
    "dark red bags contain 2 dark orange bags."
    "dark orange bags contain 2 dark yellow bags."
    "dark yellow bags contain 2 dark green bags."
    "dark green bags contain 2 dark blue bags."
    "dark blue bags contain 2 dark violet bags."
    "dark violet bags contain no other bags."]))

(deftest part1-sample
  (testing "Identifies the possible outer bags given the rules"
    (is (= #{:bright-white :muted-yellow :dark-orange :light-red}
           (t/all-outer-bags d07-s00 :shiny-gold)))))

(deftest part2-sample
  (testing "Counts the number of inner bags required to satisfy the rules"
    (is (= 32  (t/count-inner-bags d07-s00  :shiny-gold)))
    (is (= 126 (t/count-inner-bags d07-s01 :shiny-gold)))))

(def day07-input (u/parse-puzzle-input t/parse 2020 7))

(deftest day07-part1-soln
  (testing "Reproduces the answer for day07, part1"
    (is (= 372 (t/day07-part1-soln day07-input)))))

(deftest day07-part2-soln
  (testing "Reproduces the answer for day07, part2"
    (is (= 8015 (t/day07-part2-soln day07-input)))))