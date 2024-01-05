(ns aoc-clj.2021.day08-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc-clj.utils.core :as u]
            [aoc-clj.2021.day08 :as t]))

(def d08-s00
  (t/parse-line "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"))

(def d08-s01
  (t/parse
   ["be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe"
    "edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc"
    "fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg"
    "fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb"
    "aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea"
    "fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb"
    "dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe"
    "bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef"
    "egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb"
    "gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce"]))

(deftest total-easy-digits-count
  (testing "Counts all easy digit codes in sample data"
    (is (= 26 (t/total-easy-digits-count d08-s01)))))

(deftest decode-notes
  (testing "Can decode the digit codes in sample data"
    (is (= 5353 (t/decode-notes d08-s00)))
    (is (= [8394 9781 1197 9361 4873 8418 4548 1625 8717 4315]
           (map t/decode-notes d08-s01))))

  (deftest sum-of-decoded-digits
    (testing "Can add up all the decoded digits from the sample data"
      (is (= 61229 (t/sum-of-decoded-digits d08-s01))))))

(def day08-input (u/parse-puzzle-input t/parse 2021 8))

(deftest part1-test
  (testing "Reproduces the answer for day08, part1"
    (is (= 493 (t/part1 day08-input)))))

(deftest part2-test
  (testing "Reproduces the answer for day08, part2"
    (is (= 1010460 (t/part2 day08-input)))))